package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.model.Goal;
import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import lombok.Getter;
import org.springframework.stereotype.Component;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfcSteps.GET_GOAL_STEP;

@Getter
@Component(GetGoalStep.NAME)
public class GetGoalStep extends AbstractStepExecutor {

    public static final String NAME = "getGoalStep";
    private final Step currentStep = GET_GOAL_STEP;

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return Goal.getByMessage(inputMsg).map(goal -> {
            bot.getUserBodyData().setGoal(goal);
            return true;
        }).orElseGet(() -> {
            bot.sendTextMessage("Прости, не понимаю. Выбери из предложенных вариантов пожалуйста");
            return false;
        });
    }
}
