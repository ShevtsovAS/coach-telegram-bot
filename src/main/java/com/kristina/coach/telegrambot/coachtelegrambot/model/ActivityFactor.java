package com.kristina.coach.telegrambot.coachtelegrambot.model;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
public enum ActivityFactor {
    VERY_LOW("очень низкий", gender -> 1.3),
    LOW("низкий", gender -> 1.4),
    MIDDLE("средний", gender -> Gender.FEMALE == gender ? 1.6 : 1.7),
    HIGH("высокий", gender -> Gender.FEMALE == gender ? 1.8 : 1.9);

    private final String key;
    private final Function<Gender, Double> getFactorFunction;

    private final static Map<String, ActivityFactor> VALUES_MAP = Arrays.stream(values())
            .collect(toMap(it -> it.key, it -> it));

    public static Optional<ActivityFactor> getByMessage(String key) {
        return Optional.ofNullable(VALUES_MAP.get(key.toLowerCase()));
    }

    public Double getFactorValue(Gender gender) {
        return getFactorFunction.apply(gender);
    }
}
