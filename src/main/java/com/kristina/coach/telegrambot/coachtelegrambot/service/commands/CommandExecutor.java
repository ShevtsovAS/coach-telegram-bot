package com.kristina.coach.telegrambot.coachtelegrambot.service.commands;


import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;

@FunctionalInterface
public interface CommandExecutor {
    void execute(CoachTelegramBot bot);
}
