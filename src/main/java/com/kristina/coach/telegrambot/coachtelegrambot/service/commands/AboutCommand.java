package com.kristina.coach.telegrambot.coachtelegrambot.service.commands;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component(AboutCommand.NAME)
@RequiredArgsConstructor
public final class AboutCommand implements CommandExecutor {

    public static final String NAME = "/about";

    @Override
    public void execute(CoachTelegramBot bot) {
        bot.sendBotDescription();
    }

}
