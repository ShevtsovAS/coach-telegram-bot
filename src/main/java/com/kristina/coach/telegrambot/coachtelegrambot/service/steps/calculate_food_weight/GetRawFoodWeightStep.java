package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_food_weight;

import com.kristina.coach.telegrambot.coachtelegrambot.model.Food;
import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_food_weight.CalcFoodWeightSteps.GET_RAW_FOOD_WEIGHT;

@Getter
@Component(GetRawFoodWeightStep.NAME)
public class GetRawFoodWeightStep extends AbstractStepExecutor {

    public static final String NAME = "getRawFoodWeightStep";
    private final Step currentStep = GET_RAW_FOOD_WEIGHT;

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg)).map(rawFoodWeight -> {
            Food userFood = bot.getUserFood();
            userFood.setRawWeight(rawFoodWeight);
            return true;
        }).orElse(false);
    }

}
