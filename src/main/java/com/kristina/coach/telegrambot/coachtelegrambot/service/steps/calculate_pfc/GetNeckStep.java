package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.Getter;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfcSteps.GET_NECK_STEP;

@Getter
@Component(GetNeckStep.NAME)
public class GetNeckStep extends AbstractStepExecutor {

    public static final String NAME = "getNeckStep";
    private final Step currentStep = GET_NECK_STEP;
    private final Range<Double> expectedNeck = Range.of(10.0, 100.0);

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg)).map(neck -> {
            var prevNeck = bot.getUserBodyData().getNeck();
            if (expectedNeck.contains(neck) || (prevNeck != null && prevNeck.equals(neck))) {
                bot.getUserBodyData().setNeck(neck);
                return true;
            }

            bot.sendTextMessage("Вы уверенны? Введи значение ещё раз если да");
            return false;
        }).orElse(false);
    }
}
