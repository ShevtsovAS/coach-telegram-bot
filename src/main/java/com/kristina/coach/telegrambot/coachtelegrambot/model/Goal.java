package com.kristina.coach.telegrambot.coachtelegrambot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.kristina.coach.telegrambot.coachtelegrambot.model.Gender.MALE;
import static java.util.stream.Collectors.toMap;

@Getter
@RequiredArgsConstructor
public enum Goal {
    MUSCLE_MASS_GAIN("набрать мышечную массу", gender -> 1.2),
    MAINTAINING("изменить качество тела", gender -> 1.0),
    LOSE_WEIGHT("похудеть", gender -> MALE == gender ? 0.7 : 0.8);

    private final String key;

    private final Function<Gender, Double> getFactorFunction;

    private final static Map<String, Goal> VALUES_MAP = Arrays.stream(values())
            .collect(toMap(it -> it.key, it -> it));

    public static Optional<Goal> getByMessage(String key) {
        return Optional.ofNullable(VALUES_MAP.get(key.toLowerCase()));
    }

    public double getFactor(Gender gender) {
        return getFactorFunction.apply(gender);
    }
}
