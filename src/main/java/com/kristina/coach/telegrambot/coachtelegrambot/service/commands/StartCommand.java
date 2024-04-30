package com.kristina.coach.telegrambot.coachtelegrambot.service.commands;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component(StartCommand.NAME)
@RequiredArgsConstructor
public final class StartCommand implements CommandExecutor {

    public static final String NAME = "/start";

    @Override
    public void execute(CoachTelegramBot bot) {
        bot.start();
    }
}
