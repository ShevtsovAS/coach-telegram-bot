package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_food_weight;

import com.kristina.coach.telegrambot.coachtelegrambot.model.Food;
import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.commands.CalculateFoodWeightCommand;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Optional;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_food_weight.CalcFoodWeightSteps.GET_WANTED_RAW_FOOD_WEIGHT;
import static java.math.RoundingMode.HALF_UP;

@Getter
@Component(GetWantedRawFoodWeightStep.NAME)
public class GetWantedRawFoodWeightStep extends AbstractStepExecutor {

    public static final String NAME = "getWantedRawFoodWeightStep";
    private static final DecimalFormat DF = new DecimalFormat("#.##");
    private final Step currentStep = GET_WANTED_RAW_FOOD_WEIGHT;

    static {
        DF.setRoundingMode(HALF_UP);
    }

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg))
                .map(wantedRawFoodWeight -> calculateResult(bot, wantedRawFoodWeight))
                .orElse(false);
    }

    @SneakyThrows
    private static boolean calculateResult(CoachTelegramBot bot, double wantedRawFoodWeight) {
        Food userFood = bot.getUserFood();
        Double rawWeight = userFood.getRawWeight();
        Double cookedWeight = userFood.getCookedWeight();
        if (rawWeight == null || cookedWeight == null) {
            bot.sendTextMessage(BotUtil.getMessageFromResource("food-lost"));
            bot.getCommandExecutorProvider().get(CalculateFoodWeightCommand.NAME).execute(bot);
            return false;
        }

        try {
            Double result = wantedRawFoodWeight / (rawWeight / cookedWeight);
            String text = BotUtil.getMessageFromResource("food-weight-calculation-result");
            bot.sendTextMessage(String.format(text, DF.format(wantedRawFoodWeight), DF.format(result)));
        } catch (Exception e) {
            bot.sendTextMessage("Что то пошло не так, произошла ошибка при вычислении");
        }

        return true;
    }
}
