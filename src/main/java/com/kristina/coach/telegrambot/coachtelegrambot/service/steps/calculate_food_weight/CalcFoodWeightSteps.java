package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_food_weight;

import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum CalcFoodWeightSteps implements Step {
    GET_RAW_FOOD_WEIGHT("Введи вес еды в сыром виде (в граммах)", GetRawFoodWeightStep.NAME, new ReplyKeyboardRemove(true)),
    GET_COOKED_FOOD_WEIGHT("Введи вес еды после приготовления (в граммах)", GetCookedFoodWeightStep.NAME, new ReplyKeyboardRemove(true)),
    GET_WANTED_RAW_FOOD_WEIGHT("Напиши сколько ты хочешь съесть грамм в сыром виде", GetWantedRawFoodWeightStep.NAME, new ReplyKeyboardRemove(true));

    private final String message;
    private final String stepExecutorName;
    private final ReplyKeyboard replyKeyboard;

    public static Step getStart() {
        return CalcFoodWeightSteps.values()[0];
    }

    public Optional<Step> getNext() {
        if (isTheLastStep()) {
            return Optional.empty();
        }
        return Optional.of(values()[ordinal() + 1]);
    }

    private boolean isTheLastStep() {
        return ordinal() == values().length - 1;
    }
}
