package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.model.ActivityFactor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import lombok.Getter;
import org.springframework.stereotype.Component;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfcSteps.GET_ACTIVITY_FACTOR_STEP;

@Getter
@Component(GetActivityFactorStep.NAME)
public class GetActivityFactorStep extends AbstractStepExecutor {

    public static final String NAME = "getActivityFactorStep";
    private final Step currentStep = GET_ACTIVITY_FACTOR_STEP;

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return ActivityFactor.getByMessage(inputMsg).map(activityFactor -> {
            bot.getUserBodyData().setActivityFactor(activityFactor);
            return true;
        }).orElseGet(() -> {
            bot.sendTextMessage("Прости, не понимаю. Выбери из предложенных вариантов пожалуйста");
            return false;
        });
    }
}
