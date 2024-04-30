package com.kristina.coach.telegrambot.coachtelegrambot.service;

import com.kristina.coach.telegrambot.coachtelegrambot.config.TelegramBotProperties;
import com.kristina.coach.telegrambot.coachtelegrambot.service.provider.CommandExecutorProvider;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class CoachTelegramBot extends MultiSessionTelegramBot {


    private final CommandExecutorProvider commandExecutorProvider;


    public CoachTelegramBot(TelegramBotsApi botsApi, TelegramBotProperties properties, CommandExecutorProvider commandExecutorProvider) throws TelegramApiException {
        super(properties.getName(), properties.getToken());
        System.out.println(properties.getName());
        System.out.println(properties.getToken());
        this.commandExecutorProvider = commandExecutorProvider;
        botsApi.registerBot(this);
        initMainMenu(properties.getCommands());
    }

    @Override
    public void onUpdateEventReceived() {
        String message = getMessageText();
        commandExecutorProvider.getByMessage(message).execute(this);
    }

    public void start() {
        //TODO: Начало работы
        sendTextMessage("Я ещё нихера не умею, но скоро научусь, здесь будет обрабатываться комманда /start");
    }

    public void calculatePfc() {
        //TODO: Расчитать БЖУ
        sendTextMessage("Я ещё нихера не умею, но скоро научусь, здесь будет обрабатываться комманда /pfc");
    }

    public void calculateFoodWeight() {
        //TODO: Расчитать вес готовой еды
        sendTextMessage("Я ещё нихера не умею, но скоро научусь, здесь будет обрабатываться комманда /foodweight");
    }

}
