package com.kristina.coach.telegrambot.coachtelegrambot.service.commands;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.GetRawFoodWeightStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component(CalculateFoodWeightCommand.NAME)
@RequiredArgsConstructor
public final class CalculateFoodWeightCommand implements CommandExecutor {

    public static final String NAME = "/foodweight";

    @Override
    public void execute(CoachTelegramBot bot) {
        bot.setCurrentStep(GetRawFoodWeightStep.NAME);
        bot.sendTextMessage("Введи вес еды в сыром виде (в граммах)");
    }
}
