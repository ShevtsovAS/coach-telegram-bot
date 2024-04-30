package com.kristina.coach.telegrambot.coachtelegrambot.service.handlers;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;

@FunctionalInterface
public interface ButtonHandler {
    void execute(CoachTelegramBot bot);
}
