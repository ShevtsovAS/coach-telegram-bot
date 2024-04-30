package com.kristina.coach.telegrambot.coachtelegrambot.service.steps;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(GetRawFoodWeightStep.NAME)
public class GetRawFoodWeightStep implements StepExecutor {

    public static final String NAME = "getRawFoodWeightStep";

    @Override
    public void execute(CoachTelegramBot bot, String inputMsg) {
        Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg)).ifPresent(rawFoodWeight -> {
            bot.setRawFoodWeight(rawFoodWeight);
            bot.setCurrentStep(GetCookedFoodWeightStep.NAME);
            bot.sendTextMessage("Введи вес еды после приготовления (в граммах)");
        });
    }
}
