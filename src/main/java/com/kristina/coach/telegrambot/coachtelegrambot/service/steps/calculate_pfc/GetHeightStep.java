package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.Getter;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfcSteps.GET_HEIGHT_STEP;

@Getter
@Component(GetHeightStep.NAME)
public class GetHeightStep extends AbstractStepExecutor {

    public static final String NAME = "getHeightStep";
    private final Step currentStep = GET_HEIGHT_STEP;
    private final Range<Double> expectedHeight = Range.of(100.0, 500.0);

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg)).map(height -> {
            var prevHeight = bot.getUserBodyData().getHeight();
            if (expectedHeight.contains(height) || (prevHeight != null && prevHeight.equals(height))) {
                bot.getUserBodyData().setHeight(height);
                return true;
            }

            bot.sendTextMessage("Вы уверенны? Введи значение ещё раз если да");
            return false;
        }).orElse(false);
    }
}
