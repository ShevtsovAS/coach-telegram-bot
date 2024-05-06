package com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.AbstractStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.Step;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.Getter;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfcSteps.GET_AGE_STEP;

@Getter
@Component(GetAgeStep.NAME)
public class GetAgeStep extends AbstractStepExecutor {

    public static final String NAME = "getAgeStep";
    private final Step currentStep = GET_AGE_STEP;
    private final Range<Integer> expectedAge = Range.of(10, 99);

    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        return Optional.ofNullable(BotUtil.getaDoubleValue(bot, inputMsg))
                .map(Double::intValue)
                .map(age -> {
                    var prevAge = bot.getUserBodyData().getAge();
                    if (expectedAge.contains(age) || (prevAge != null && prevAge.equals(age))) {
                        bot.getUserBodyData().setAge(age);
                        return true;
                    }

                    bot.sendTextMessage(BotUtil.getMessageFromResource("unexpected-value"));
                    return false;
                }).orElse(false);
    }
}
