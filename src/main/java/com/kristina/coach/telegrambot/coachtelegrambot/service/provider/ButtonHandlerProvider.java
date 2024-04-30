package com.kristina.coach.telegrambot.coachtelegrambot.service.provider;

import com.kristina.coach.telegrambot.coachtelegrambot.service.handlers.ButtonHandler;
import com.kristina.coach.telegrambot.coachtelegrambot.service.handlers.UnknownButtonHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ButtonHandlerProvider implements Provider<ButtonHandler> {

    private final Map<String, ButtonHandler> buttonHandlers;
    private final UnknownButtonHandler unknownButtonHandler;


    @Override
    public ButtonHandler get(String key) {
        return buttonHandlers.getOrDefault(key, unknownButtonHandler);
    }
}
