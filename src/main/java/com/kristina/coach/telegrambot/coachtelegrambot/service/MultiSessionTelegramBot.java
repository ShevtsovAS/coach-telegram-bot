package com.kristina.coach.telegrambot.coachtelegrambot.service;

import com.kristina.coach.telegrambot.coachtelegrambot.method.GetBotDescription;
import com.kristina.coach.telegrambot.coachtelegrambot.model.MyBotDescription;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
@Log4j2
public abstract class MultiSessionTelegramBot extends TelegramLongPollingBot {

    @Getter
    private final String botUsername;
    @Getter
    private final String botToken;

    private final ThreadLocal<Update> updateEvent = new ThreadLocal<>();

    public MultiSessionTelegramBot(String botUsername, String botToken) {
        super(botToken);
        this.botUsername = botUsername;
        this.botToken = botToken;
    }


    @Override
    public final void onUpdateReceived(Update updateEvent) {
        this.updateEvent.set(updateEvent);
        onUpdateEventReceived();
        answerCallbackQuery();
    }

    public abstract void onUpdateEventReceived();

    public String getMessageText() {
        return Optional.ofNullable(updateEvent.get())
                .map(Update::getMessage)
                .map(Message::getText)
                .orElse("");
    }

    public String getCallbackQueryButtonKey() {
        return Optional.ofNullable(updateEvent.get())
                .map(Update::getCallbackQuery)
                .map(CallbackQuery::getData)
                .orElse("");
    }

    @SneakyThrows
    public void sendTextMessage(String text) {
        sendTextMessage(text, null);
    }

    @SneakyThrows
    public void sendTextMessage(String text, Map<String, String> buttons) {
        sendApiMethod(BotUtil.createMessage(getCurrentChatId(), text, buttons));
    }

    public void sendPhotoMessage(String photoKey) {
        sendPhotoMessage(photoKey, null, null);
    }

    public void sendPhotoMessage(String photoKey, String text) {
        sendPhotoMessage(photoKey, text, null);
    }

    public void sendPhotoMessage(String photoKey, Map<String, String> buttons) {
        sendPhotoMessage(photoKey, null, buttons);
    }

    @SneakyThrows
    public void sendPhotoMessage(String photoKey, String text, Map<String, String> buttons) {
        ClassPathResource resource = new ClassPathResource(String.format("images/%s.jpg", photoKey));
        InputFile photo = new InputFile(resource.getInputStream(), botUsername);
        SendPhoto photoMessage = BotUtil.createPhotoMessage(getCurrentChatId(), photo, text, buttons);
        execute(photoMessage);
    }

    @SneakyThrows
    public void sendBotDescription() {
        MyBotDescription result = sendApiMethod(new GetBotDescription());
        String formatedDescription = BotUtil.formatTelegramMessage(result.getDescription());
        sendTextMessage(formatedDescription);
    }

    public Long getCurrentChatId() {
        Optional<Long> chatIdFromMessage = Optional.ofNullable(updateEvent.get())
                .map(Update::getMessage)
                .map(Message::getFrom)
                .map(User::getId);
        Optional<Long> chatIdFromCallbackQuery = Optional.ofNullable(updateEvent.get())
                .map(Update::getCallbackQuery)
                .map(CallbackQuery::getFrom)
                .map(User::getId);
        return chatIdFromMessage.or(() -> chatIdFromCallbackQuery).orElse(null);
    }

    @SneakyThrows
    public void answerCallbackQuery() {
        answerCallbackQuery(null, false);
    }

    @SneakyThrows
    public void answerCallbackQuery(String text) {
        answerCallbackQuery(text, false);
    }

    @SneakyThrows
    public void answerCallbackQuery(String text, boolean showAlert) {
        AnswerCallbackQuery answer = Optional.ofNullable(updateEvent.get())
                .map(Update::getCallbackQuery)
                .map(CallbackQuery::getId)
                .map(id -> BotUtil.createAnswer(id, text, showAlert))
                .orElse(null);

        if (answer != null) {
            execute(answer);
        }
    }

    @SneakyThrows
    public void initMainMenu(List<BotCommand> commands) {
        execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
    }

}