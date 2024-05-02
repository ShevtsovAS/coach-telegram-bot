package com.kristina.coach.telegrambot.coachtelegrambot.model;

import lombok.Data;

@Data
public class UserBodyData {
    private Goal goal;
    private Gender gender;
    private Integer age;
    private Double height;
    private Double weight;
    private Double waist;
    private Double hips;
    private Double underBust;
    private Double neck;
    private ActivityFactor activityFactor;
    private WorkoutMode workoutMode;
}
