package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_food_weight;

import com.kristina.coach.telegrambot.coachtelegrambot.model.Food;
import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_food_weight.CalcFoodWeightSteps.GET_COOKED_FOOD_WEIGHT;

@Component(GetCookedFoodWeightStep.NAME)
public class GetCookedFoodWeightStep extends AbstractStepExecutor {

    public static final String NAME = "getCookedFoodWeightStep";

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg)).map(cookedFoodWeight -> {
            Food userFood = bot.getUserFood();
            userFood.setCookedWeight(cookedFoodWeight);
            return true;
        }).orElse(false);
    }

    @Override
    public Step getCurrentStep() {
        return GET_COOKED_FOOD_WEIGHT;
    }
}
