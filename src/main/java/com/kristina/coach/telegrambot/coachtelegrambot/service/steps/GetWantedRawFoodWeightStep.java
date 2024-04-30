package com.kristina.coach.telegrambot.coachtelegrambot.service.steps;

import com.kristina.coach.telegrambot.coachtelegrambot.model.Food;
import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.commands.CalculateFoodWeightCommand;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Optional;

import static java.math.RoundingMode.HALF_UP;

@Component(GetWantedRawFoodWeightStep.NAME)
public class GetWantedRawFoodWeightStep implements StepExecutor {

    public static final String NAME = "getWantedRawFoodWeightStep";
    public static final DecimalFormat DF = new DecimalFormat("#.##");

    static {
        DF.setRoundingMode(HALF_UP);
    }

    @SneakyThrows
    @Override
    public void execute(CoachTelegramBot bot, String inputMsg) {
        Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg))
                .ifPresent(wantedRawFoodWeight -> calculateResult(bot, wantedRawFoodWeight));
    }

    @SneakyThrows
    private static void calculateResult(CoachTelegramBot bot, double wantedRawFoodWeight) {
        Food userFood = bot.getUserFood();
        Double rawWeight = userFood.getRawWeight();
        Double cookedWeight = userFood.getCookedWeight();
        if (rawWeight == null || cookedWeight == null) {
            String text = new String(new ClassPathResource("messages/food-lost.txt").getContentAsByteArray());
            bot.sendTextMessage(text);
            bot.getCommandExecutorProvider().get(CalculateFoodWeightCommand.NAME).execute(bot);
            return;
        }

        try {
            Double result = wantedRawFoodWeight / (rawWeight / cookedWeight);
            String text = new String(new ClassPathResource("messages/food-weight-calculation-result.txt").getContentAsByteArray());
            bot.sendTextMessage(String.format(text, DF.format(wantedRawFoodWeight), DF.format(result)));
        } catch (Exception e) {
            bot.sendTextMessage("Что то пошло не так, произошла ошибка при вычислении");
        } finally {
            bot.setCurrentStep(DefaultStep.NAME);
        }
    }
}
