package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.model.Gender;
import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import lombok.Getter;
import org.springframework.stereotype.Component;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfcSteps.GET_GENDER_STEP;

@Getter
@Component(GetGenderStep.NAME)
public class GetGenderStep extends AbstractStepExecutor {

    public static final String NAME = "getGenderStep";
    private final Step currentStep = GET_GENDER_STEP;

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return Gender.getByMessage(inputMsg).map(gender -> {
            bot.getUserBodyData().setGender(gender);
            return true;
        }).orElseGet(() -> {
            bot.sendTextMessage("Прости, не понимаю. Выбери из предложенных вариантов пожалуйста");
            return false;
        });
    }
}
