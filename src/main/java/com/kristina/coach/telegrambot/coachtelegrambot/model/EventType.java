package com.kristina.coach.telegrambot.coachtelegrambot.model;

import com.kristina.coach.telegrambot.coachtelegrambot.service.MultiSessionTelegramBot;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public enum EventType {
    COMMAND_RECEIVED,
    BUTTON_PRESSED,
    STEP_EXECUTION;

    public static EventType of(MultiSessionTelegramBot bot) {
        String message = bot.getMessageText();
        if (message.startsWith("/")) {
            return COMMAND_RECEIVED;
        }

        String buttonKey = bot.getCallbackQueryButtonKey();
        if (StringUtils.isNotBlank(buttonKey)) {
            return BUTTON_PRESSED;
        }

        return STEP_EXECUTION;
    }
}
