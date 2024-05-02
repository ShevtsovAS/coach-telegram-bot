package com.kristina.coach.telegrambot.coachtelegrambot.service.calculator;

import com.kristina.coach.telegrambot.coachtelegrambot.model.BodyMassIndex;
import com.kristina.coach.telegrambot.coachtelegrambot.model.ProteinsFatCarbs;
import com.kristina.coach.telegrambot.coachtelegrambot.model.UserBodyData;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class PfsCalculator {

    public ProteinsFatCarbs calculate(UserBodyData userBodyData) {
        val fatPercent = calculateFatPercent(userBodyData);
        val fatlessMass = userBodyData.getWeight() * (100 - fatPercent) / 100;
        val basalExchange = 370 + (21.6 * fatlessMass);
        val calBase = calculateCalBase(userBodyData, basalExchange);
        val bodyMassIndexValue = calculateBodyMassIndexValue(userBodyData);
        val calories = calculateCalories(userBodyData, calBase);
        val protein = calculateProtein(userBodyData);
        val fat = calculateFat(userBodyData, bodyMassIndexValue);
        return ProteinsFatCarbs.builder()
                .fatPercent(fatPercent)
                .fatlessMass(fatlessMass)
                .basalExchange(basalExchange)
                .calBase(calBase)
                .calories(calories)
                .protein(protein)
                .bodyMassIndexValue(bodyMassIndexValue)
                .fat(fat)
                .carbs(calculateCarbs(calories, protein, fat))
                .build();
    }

    private double calculateCarbs(double calories, double protein, double fat) {
        return (calories - (protein * 4 + fat * 9)) / 4;
    }

    private double calculateFat(UserBodyData userBodyData, double bodyMassIndexValue) {
        val bodyMassIndex = BodyMassIndex.get(bodyMassIndexValue);
        val weight = userBodyData.getWeight();
        return weight * bodyMassIndex.getFatFactor();
    }

    private double calculateBodyMassIndexValue(UserBodyData userBodyData) {
        val height = userBodyData.getHeight();
        val weight = userBodyData.getWeight();
        val heightMeters = height / 100;
        return weight / (heightMeters * heightMeters);
    }

    private double calculateProtein(UserBodyData userBodyData) {
        val proteinFactor = userBodyData.getWorkoutMode().getProteinFactor();
        val weight = userBodyData.getWeight();
        return weight * proteinFactor;
    }

    private double calculateCalories(UserBodyData userBodyData, double calBase) {
        val goal = userBodyData.getGoal();
        val gender = userBodyData.getGender();
        val goalFactor = goal.getFactor(gender);
        return calBase * goalFactor;
    }

    private double calculateCalBase(UserBodyData userBodyData, double basalExchange) {
        val gender = userBodyData.getGender();
        val activityFactor = userBodyData.getActivityFactor().getFactorValue(gender);
        return basalExchange * activityFactor;
    }

    private double calculateFatPercent(UserBodyData userBodyData) {
        val waist = userBodyData.getWaist();
        val neck = userBodyData.getNeck();
        val height = userBodyData.getHeight();
        val hips = userBodyData.getHips();
        return switch (userBodyData.getGender()) {
            case MALE -> 495 / (1.0324 - 0.19077 * (Math.log10(waist - neck)) + 0.15456 * (Math.log10(height))) - 450;
            case FEMALE -> 495 / (1.29579 - 0.35004 * (Math.log10(waist + hips - neck)) + 0.221 * (Math.log10(height))) - 450;
        };
    }
}
