package com.kristina.coach.telegrambot.coachtelegrambot.service.steps;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(GetCookedFoodWeightStep.NAME)
public class GetCookedFoodWeightStep implements StepExecutor {

    public static final String NAME = "getCookedFoodWeightStep";

    @Override
    public void execute(CoachTelegramBot bot, String inputMsg) {
        Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg)).ifPresent(cookedFoodWeight -> {
            bot.setCookedFoodWeight(cookedFoodWeight);
            bot.setCurrentStep(GetWantedRawFoodWeightStep.NAME);
            bot.sendTextMessage("Напиши сколько ты хочешь съесть грамм в сыром виде");
        });
    }
}
