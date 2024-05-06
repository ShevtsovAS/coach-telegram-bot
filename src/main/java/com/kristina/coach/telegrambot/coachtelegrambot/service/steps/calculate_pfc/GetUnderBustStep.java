package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.Getter;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfcSteps.GET_UNDER_BUST_STEP;

@Getter
@Component(GetUnderBustStep.NAME)
public class GetUnderBustStep extends AbstractStepExecutor {

    public static final String NAME = "getUnderBustStep";
    private final Step currentStep = GET_UNDER_BUST_STEP;
    private final Range<Double> expectedUnderBust = Range.of(40.0, 200.0);

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg)).map(underBust -> {
            var prevUnderBust = bot.getUserBodyData().getUnderBust();
            if (expectedUnderBust.contains(underBust) || (prevUnderBust != null && prevUnderBust.equals(underBust))) {
                bot.getUserBodyData().setUnderBust(underBust);
                return true;
            }

            bot.sendTextMessage(BotUtil.getMessageFromResource("unexpected-value"));
            return false;
        }).orElse(false);
    }
}
