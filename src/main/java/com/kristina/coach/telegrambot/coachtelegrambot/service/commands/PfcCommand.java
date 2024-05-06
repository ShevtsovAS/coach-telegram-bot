package com.kristina.coach.telegrambot.coachtelegrambot.service.commands;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.calculate_pfc.CalculatePfcSteps;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component(PfcCommand.NAME)
@RequiredArgsConstructor
public final class PfcCommand implements CommandExecutor {

    public static final String NAME = "/pfc";

    @SneakyThrows
    @Override
    public void execute(CoachTelegramBot bot) {
        bot.sendTextMessage(BotUtil.getMessageFromResource("collect-body-info"));

        var startStep = CalculatePfcSteps.getStart();
        bot.sendTextMessage(startStep.getMessage(), startStep.getReplyKeyboard());
        bot.setCurrentStep(startStep.getStepExecutorName());
    }

}
