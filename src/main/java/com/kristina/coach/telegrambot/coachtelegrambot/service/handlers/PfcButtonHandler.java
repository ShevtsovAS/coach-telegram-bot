package com.kristina.coach.telegrambot.coachtelegrambot.service.handlers;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.commands.PfcCommand;
import org.springframework.stereotype.Component;

@Component(PfcButtonHandler.NAME)
public class PfcButtonHandler implements ButtonHandler {

    public static final String NAME = "pfc_btn";

    @Override
    public void execute(CoachTelegramBot bot) {
        bot.getCommandExecutorProvider().get(PfcCommand.NAME).execute(bot);
    }
}
