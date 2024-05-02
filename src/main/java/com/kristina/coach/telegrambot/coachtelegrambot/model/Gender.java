package com.kristina.coach.telegrambot.coachtelegrambot.model;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
public enum Gender {
    MALE("мужской"),
    FEMALE("женский");

    private final String key;

    private final static Map<String, Gender> VALUES_MAP = Arrays.stream(values())
            .collect(toMap(it -> it.key, it -> it));

    public static Optional<Gender> getByMessage(String key) {
        return Optional.ofNullable(VALUES_MAP.get(key.toLowerCase()));
    }
}
