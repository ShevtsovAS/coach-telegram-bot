package com.kristina.coach.telegrambot.coachtelegrambot.service.steps;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component(DefaultStep.NAME)
public class DefaultStep implements StepExecutor {

    public static final String NAME = "defaultStep";

    @SneakyThrows
    @Override
    public void execute(CoachTelegramBot bot, String inputMsg) {
        String text = new String(new ClassPathResource("messages/unknown-step.txt").getContentAsByteArray());
        bot.sendTextMessage(text);
    }
}
