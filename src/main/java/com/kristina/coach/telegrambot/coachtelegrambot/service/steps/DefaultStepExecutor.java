package com.kristina.coach.telegrambot.coachtelegrambot.service.steps;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component(DefaultStepExecutor.NAME)
public class DefaultStepExecutor extends AbstractStepExecutor {

    public static final String NAME = "defaultStep";

    @SneakyThrows
    @Override
    public boolean executeStep(CoachTelegramBot bot, String inputMsg) {
        bot.sendTextMessage(BotUtil.getMessageFromResource("unknown-step"));
        return false;
    }

    @Override
    public Step getCurrentStep() {
        return null;
    }
}
