package com.kristina.coach.telegrambot.coachtelegrambot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Range;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum BodyMassIndex {
    SIGNIFICANT_UNDER_WEIGHT("Значительный дефицит массы тела", Range.of(Double.MIN_VALUE, 16.0), 1.2),
    UNDER_WEIGHT("Дефицит массы тела", Range.of(16.0, 18.5), 1.2),
    NORMAL("Норма", Range.of(18.5, 25.0), 1.0),
    OVERWEIGHT("Лишний вес", Range.of(25.0, 30.0), 0.8),
    OBESITY_1("Ожирение 1-й степени", Range.of(30.0, 35.0), 0.75),
    OBESITY_2("Ожирение 2-й степени", Range.of(35.0, 40.0), 0.7),
    OBESITY_3("Ожирение 3-й степени", Range.of(40.0, Double.MAX_VALUE), 0.65);

    private final String description;
    private final Range<Double> value;
    private final double fatFactor;

    public static BodyMassIndex get(double bmi) {
        return Stream.of(values())
                .filter(it -> it.value.contains(bmi))
                .findFirst()
                .orElseThrow();
    }
}
