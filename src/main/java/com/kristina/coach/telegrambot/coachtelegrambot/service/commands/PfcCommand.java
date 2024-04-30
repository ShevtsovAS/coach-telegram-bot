package com.kristina.coach.telegrambot.coachtelegrambot.service.commands;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component(PfcCommand.NAME)
@RequiredArgsConstructor
public final class PfcCommand implements CommandExecutor {

    public static final String NAME = "/pfc";

    @Override
    public void execute(CoachTelegramBot bot) {
        bot.calculatePfc();
    }

}
