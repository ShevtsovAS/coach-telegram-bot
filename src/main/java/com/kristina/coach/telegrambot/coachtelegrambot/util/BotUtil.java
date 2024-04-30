package com.kristina.coach.telegrambot.coachtelegrambot.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

@UtilityClass
public class BotUtil {

    public static Set<Character> specCharacters = Set.of('_', '*', '[', ']', '(', ')', '~', '`', '>', '#', '+', '=', '|', '{', '}', '.');

    public static InlineKeyboardButton createButton(Map.Entry<String, String> entry) {
        return InlineKeyboardButton.builder()
                .text(entry.getKey())
                .callbackData(entry.getValue())
                .build();
    }

    public static AnswerCallbackQuery createAnswer(String id, String text, boolean showAlert) {
        return AnswerCallbackQuery.builder()
                .callbackQueryId(id)
                .text(text)
                .showAlert(showAlert)
                .build();
    }

    public static InlineKeyboardMarkup createInlineKeyboardMarkup(Map<String, String> buttons) {
        if (buttons == null || buttons.isEmpty()) {
            return null;
        }

        List<List<InlineKeyboardButton>> keyboard = buttons.entrySet().stream()
                .map(BotUtil::createButton)
                .map(List::of)
                .toList();

        return new InlineKeyboardMarkup(keyboard);
    }

    @SneakyThrows
    public static SendPhoto createPhotoMessage(Long chatId, InputFile photo, String text, Map<String, String> buttons) {
        return SendPhoto.builder()
                .chatId(chatId)
                .photo(photo)
                .parseMode(MARKDOWN)
                .caption(text)
                .replyMarkup(createInlineKeyboardMarkup(buttons))
                .build();
    }

    public static SendMessage createMessage(Long chatId, String text, Map<String, String> buttons) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode(MARKDOWN)
                .replyMarkup(BotUtil.createInlineKeyboardMarkup(buttons))
                .build();
    }

    public static List<BotCommand> createBotCommands(Map<String, String> commands) {
        return commands.entrySet().stream()
                .map(entry -> new BotCommand(entry.getKey(), entry.getValue()))
                .toList();
    }

    public static String formatTelegramMessage(String text) {
        List<Character> characters = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (isSpecCharacter(c)) {
                characters.add('\\');
            }
            characters.add(c);
        }
        StringBuilder builder = new StringBuilder();
        characters.forEach(builder::append);
        return builder.toString();
    }

    private static boolean isSpecCharacter(char c) {
        return specCharacters.contains(c);
    }

}