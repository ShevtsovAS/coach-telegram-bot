package com.kristina.coach.telegrambot.coachtelegrambot.service.handlers;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.commands.CalculateFoodWeightCommand;
import org.springframework.stereotype.Component;

@Component(FoodWeightButtonHandler.NAME)
public class FoodWeightButtonHandler implements ButtonHandler {

    public static final String NAME = "foodweight_btn";

    @Override
    public void execute(CoachTelegramBot bot) {
        bot.getCommandExecutorProvider().get(CalculateFoodWeightCommand.NAME).execute(bot);
    }
}
