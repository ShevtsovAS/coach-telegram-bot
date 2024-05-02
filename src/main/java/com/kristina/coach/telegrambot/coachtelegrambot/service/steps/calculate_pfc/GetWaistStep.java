package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.Getter;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfcSteps.GET_WAIST_STEP;

@Getter
@Component(GetWaistStep.NAME)
public class GetWaistStep extends AbstractStepExecutor {

    public static final String NAME = "getWaistStep";
    private final Step currentStep = GET_WAIST_STEP;
    private final Range<Double> expectedWaist = Range.of(40.0, 200.0);

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg)).map(waist -> {
            var prevWaist = bot.getUserBodyData().getWaist();
            if (expectedWaist.contains(waist) || (prevWaist != null && prevWaist.equals(waist))) {
                bot.getUserBodyData().setWaist(waist);
                return true;
            }

            bot.sendTextMessage("Вы уверенны? Введи значение ещё раз если да");
            return false;
        }).orElse(false);
    }
}
