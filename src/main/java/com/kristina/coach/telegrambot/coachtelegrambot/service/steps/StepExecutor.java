package com.kristina.coach.telegrambot.coachtelegrambot.service.steps;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;

public interface StepExecutor {
    void execute(CoachTelegramBot bot, String inputMsg);
}
