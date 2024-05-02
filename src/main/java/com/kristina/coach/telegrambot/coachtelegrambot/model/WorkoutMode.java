package com.kristina.coach.telegrambot.coachtelegrambot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
public enum WorkoutMode {
    NONE("без тренировок", 1.4),
    HOME("тренировка дома", 1.6),
    GYM("тренировка в зале", 2.0);

    private final String key;

    @Getter
    private final double proteinFactor;


    private final static Map<String, WorkoutMode> VALUES_MAP = Arrays.stream(values())
            .collect(toMap(it -> it.key, it -> it));

    public static Optional<WorkoutMode> getByMessage(String key) {
        return Optional.ofNullable(VALUES_MAP.get(key.toLowerCase()));
    }

}
