package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.Getter;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfcSteps.GET_WEIGHT_STEP;

@Getter
@Component(GetWeightStep.NAME)
public class GetWeightStep extends AbstractStepExecutor {

    public static final String NAME = "getWeightStep";
    private final Step currentStep = GET_WEIGHT_STEP;
    private final Range<Double> expectedWeight = Range.of(30.0, 300.0);

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg)).map(weight -> {
            Double prevWeight = bot.getUserBodyData().getWeight();
            if (expectedWeight.contains(weight) || (prevWeight != null && prevWeight.equals(weight))) {
                bot.getUserBodyData().setWeight(weight);
                return true;
            }

            bot.sendTextMessage("Вы уверенны? Введи значение ещё раз если да");
            return false;
        }).orElse(false);
    }
}
