package com.kristina.coach.telegrambot.coachtelegrambot.service;

import com.kristina.coach.telegrambot.coachtelegrambot.method.GetBotDescription;
import com.kristina.coach.telegrambot.coachtelegrambot.model.EventType;
import com.kristina.coach.telegrambot.coachtelegrambot.model.MyBotDescription;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;
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
        onUpdateEventReceived(EventType.of(this));
        answerCallbackQuery();
    }

    public abstract void onUpdateEventReceived(EventType eventType);

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
    public void sendTextMessage(String text, ReplyKeyboard replyMarkup) {
        sendApiMethod(BotUtil.createMessage(getCurrentChatId(), text, replyMarkup));
    }

    public void sendPhotoMessage(InputFile photo) {
        sendPhotoMessage(photo, null, null);
    }

    public void sendPhotoMessage(InputFile photo, String text) {
        sendPhotoMessage(photo, text, null);
    }

    public void sendPhotoMessage(InputFile photo, ReplyKeyboard replyMarkup) {
        sendPhotoMessage(photo, null, replyMarkup);
    }

    @SneakyThrows
    public void sendPhotoMessage(InputFile photo, String text, ReplyKeyboard replyMarkup) {
        SendPhoto photoMessage = BotUtil.createPhotoMessage(getCurrentChatId(), photo, text, replyMarkup);
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

    public String getUserNameFrom() {
        Optional<String> userNameFromMessage = Optional.ofNullable(updateEvent.get())
                .map(Update::getMessage)
                .map(Message::getFrom)
                .map(User::getFirstName);
        Optional<String> userNameFromCallbackQuery = Optional.ofNullable(updateEvent.get())
                .map(Update::getCallbackQuery)
                .map(CallbackQuery::getFrom)
                .map(User::getFirstName);
        return userNameFromMessage.or(() -> userNameFromCallbackQuery).orElse("дорогой друг");
    }
}
