package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.util.KeyboardCreator;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.LinkedHashSet;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class CalculatePfsStepKeyboards {

    public static final ReplyKeyboard GET_GOAL_STEP_KEYBOARD = KeyboardCreator.createReplyKeyboardMarkup(Stream.of(
            "Похудеть", "Изменить качество тела", "Набрать мышечную массу").collect(toCollection(LinkedHashSet::new)));

    public static final ReplyKeyboard GET_GENDER_STEP_KEYBOARD = KeyboardCreator.createReplyKeyboardMarkup(Stream.of(
            "Женский", "Мужской").collect(toCollection(LinkedHashSet::new)));

    public static final ReplyKeyboard GET_ACTIVITY_FACTOR_STEP_KEYBOARD = KeyboardCreator.createReplyKeyboardMarkup(Stream.of(
            "Очень низкий", "Низкий", "Средний", "Высокий").collect(toCollection(LinkedHashSet::new)));

    public static final ReplyKeyboard GET_WORKOUT_MODE_STEP_KEYBOARD = KeyboardCreator.createReplyKeyboardMarkup(Stream.of(
            "Без тренировок", "Тренировка дома", "Тренировка в зале").collect(toCollection(LinkedHashSet::new)));

}
