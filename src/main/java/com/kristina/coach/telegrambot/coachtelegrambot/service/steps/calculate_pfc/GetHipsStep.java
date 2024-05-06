package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.Getter;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfcSteps.GET_HIPS_STEP;

@Getter
@Component(GetHipsStep.NAME)
public class GetHipsStep extends AbstractStepExecutor {

    public static final String NAME = "getHipsStep";
    private final Step currentStep = GET_HIPS_STEP;
    private final Range<Double> expectedHips = Range.of(40.0, 200.0);

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg)).map(hips -> {
            var prevHips = bot.getUserBodyData().getHips();
            if (expectedHips.contains(hips) || (prevHips != null && prevHips.equals(hips))) {
                bot.getUserBodyData().setHips(hips);
                return true;
            }

            bot.sendTextMessage(BotUtil.getMessageFromResource("unexpected-value"));
            return false;
        }).orElse(false);
    }
}
