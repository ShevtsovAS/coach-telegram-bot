package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.model.BodyMassIndex;
import com.kristina.coach.telegrambot.coachtelegrambot.model.WorkoutMode;
import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.calculator.PfsCalculator;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.text.DecimalFormat;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfcSteps.GET_WORKOUT_MODE_STEP;

@Getter
@Component(GetWorkoutModeStep.NAME)
@RequiredArgsConstructor
public class GetWorkoutModeStep extends AbstractStepExecutor {

    private final PfsCalculator pfsCalculator;

    public static final String NAME = "getWorkoutModeStep";
    public static final DecimalFormat DF = new DecimalFormat("#.#");
    private final Step currentStep = GET_WORKOUT_MODE_STEP;

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return WorkoutMode.getByMessage(inputMsg).map(workoutMode -> {
            bot.getUserBodyData().setWorkoutMode(workoutMode);
            return calculateResult(bot);
        }).orElseGet(() -> {
            bot.sendTextMessage("Прости, не понимаю. Выбери из предложенных вариантов пожалуйста");
            return false;
        });
    }

    @SneakyThrows
    private boolean calculateResult(CoachTelegramBot bot) {
        val result = pfsCalculator.calculate(bot.getUserBodyData());
        val text = BotUtil.getMessageFromResource("calculate-pfc-result")
                .replace("{BMI}", DF.format(result.getBodyMassIndexValue()))
                .replace("{BMI_DESCRIPTION}", BodyMassIndex.get(result.getBodyMassIndexValue()).getDescription())
                .replace("{GOAL}", bot.getUserBodyData().getGoal().getKey())
                .replace("{CALORIES}", String.valueOf(Math.round(result.getCalories())))
                .replace("{PROTEIN}", String.valueOf(Math.round(result.getProtein() / 10) * 10))
                .replace("{FAT}", String.valueOf(Math.round(result.getFat() / 10) * 10))
                .replace("{CARBS}", String.valueOf(Math.round(result.getCarbs() / 10) * 10));

        bot.sendTextMessage(text, new ReplyKeyboardRemove(true));
        return true;
    }
}
