package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.Optional;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfsStepKeyboards.*;

@Getter
@RequiredArgsConstructor
public enum CalculatePfcSteps implements Step {
    GET_GOAL_STEP("Какая у тебя цель?", GetGoalStep.NAME, GET_GOAL_STEP_KEYBOARD),
    GET_GENDER_STEP("Какой у тебя пол?", GetGenderStep.NAME, GET_GENDER_STEP_KEYBOARD),
    GET_AGE_STEP("Сколько тебе полных лет?", GetAgeStep.NAME, new ReplyKeyboardRemove(true)),
    GET_HEIGHT_STEP("Какой у тебя рост?", GetHeightStep.NAME, new ReplyKeyboardRemove(true)),
    GET_WEIGHT_STEP("Какой у тебя текущий вес?", GetWeightStep.NAME, new ReplyKeyboardRemove(true)),
    GET_WAIST_STEP("Размер твоей талии?", GetWaistStep.NAME, new ReplyKeyboardRemove(true)),
    GET_HIPS_STEP("Размер твоих бёдер?", GetHipsStep.NAME, new ReplyKeyboardRemove(true)),
    GET_UNDER_BUST_STEP("Размер грудной клетки?", GetUnderBustStep.NAME, new ReplyKeyboardRemove(true)),
    GET_NECK_STEP("Размер шеи?", GetNeckStep.NAME, new ReplyKeyboardRemove(true)),
    GET_ACTIVITY_FACTOR_STEP(getActivityFactorStepText(), GetActivityFactorStep.NAME, GET_ACTIVITY_FACTOR_STEP_KEYBOARD),
    GET_WORKOUT_MODE_STEP("Желаемый режим тренировок?", GetWorkoutModeStep.NAME, GET_WORKOUT_MODE_STEP_KEYBOARD);

    private final String message;
    private final String stepExecutorName;
    private final ReplyKeyboard replyKeyboard;

    public static Step getStart() {
        return CalculatePfcSteps.values()[0];
    }

    @Override
    public Optional<Step> getNext() {
        if (isTheLastStep()) {
            return Optional.empty();
        }
        return Optional.of(values()[ordinal() + 1]);
    }

    private boolean isTheLastStep() {
        return ordinal() == values().length - 1;
    }

    @SneakyThrows
    private static String getActivityFactorStepText() {
        return new String(new ClassPathResource("messages/get-activity-factor-step.txt").getContentAsByteArray());
    }
}
