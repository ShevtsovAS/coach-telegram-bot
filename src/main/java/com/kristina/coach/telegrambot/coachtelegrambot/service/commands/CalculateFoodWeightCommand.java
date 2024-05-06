package com.kristina.coach.telegrambot.coachtelegrambot.service.commands;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_food_weight.CalcFoodWeightSteps;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component(CalculateFoodWeightCommand.NAME)
@RequiredArgsConstructor
public final class CalculateFoodWeightCommand implements CommandExecutor {

    public static final String NAME = "/foodweight";

    @Override
    public void execute(CoachTelegramBot bot) {
        bot.sendTextMessage(BotUtil.getMessageFromResource("calculate-food-weight-intro"));
        var calcFoodWeightStep = CalcFoodWeightSteps.getStart();
        bot.setCurrentStep(calcFoodWeightStep.getStepExecutorName());
        bot.sendTextMessage(calcFoodWeightStep.getMessage(), calcFoodWeightStep.getReplyKeyboard());
    }
}
