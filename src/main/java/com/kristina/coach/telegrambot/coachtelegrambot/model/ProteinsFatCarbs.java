package com.kristina.coach.telegrambot.coachtelegrambot.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProteinsFatCarbs {
    double fatPercent;
    double fatlessMass;
    double basalExchange;
    double calBase;
    double calories;
    double protein;
    double fat;
    double carbs;
    double bodyMassIndexValue;
}
