package com.kristina.coach.telegrambot.coachtelegrambot.util;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.Map;
import java.util.Set;

@UtilityClass
public class KeyboardCreator {

    public static ReplyKeyboardMarkup createReplyKeyboardMarkup(Set<String> buttons) {
        if (buttons == null || buttons.isEmpty()) {
            return null;
        }

        List<KeyboardButton> keyboardButtons = buttons.stream()
                .map(KeyboardCreator::createKeyboardButton)
                .toList();

        List<KeyboardRow> keyboardRows = ListUtil.splitList(keyboardButtons, 2)
                .stream()
                .map(KeyboardRow::new)
                .toList();

        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();
    }

    public static InlineKeyboardMarkup createInlineKeyboardMarkup(Map<String, String> buttons) {
        if (buttons == null || buttons.isEmpty()) {
            return null;
        }

        List<List<InlineKeyboardButton>> keyboard = buttons.entrySet().stream()
                .map(KeyboardCreator::createInlineKeyboardButton)
                .map(List::of)
                .toList();

        return new InlineKeyboardMarkup(keyboard);
    }

    public static InlineKeyboardButton createInlineKeyboardButton(Map.Entry<String, String> entry) {
        return InlineKeyboardButton.builder()
                .text(entry.getKey())
                .callbackData(entry.getValue())
                .build();
    }

    public static KeyboardButton createKeyboardButton(String text) {
        return new KeyboardButton(text);
    }

}
