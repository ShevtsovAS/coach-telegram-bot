package com.kristina.coach.telegrambot.coachtelegrambot.model;

import org.telegram.telegrambots.meta.api.objects.description.BotDescription;

@SuppressWarnings("unused")
public class MyBotDescription extends BotDescription {

    public MyBotDescription() {
        super(null);
    }

    public MyBotDescription(String description) {
        super(description);
    }
}
