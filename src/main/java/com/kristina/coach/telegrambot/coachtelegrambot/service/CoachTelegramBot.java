package com.kristina.coach.telegrambot.coachtelegrambot.service;

import com.kristina.coach.telegrambot.coachtelegrambot.config.TelegramBotProperties;
import com.kristina.coach.telegrambot.coachtelegrambot.model.EventType;
import com.kristina.coach.telegrambot.coachtelegrambot.model.Food;
import com.kristina.coach.telegrambot.coachtelegrambot.service.commands.CommandExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.handlers.ButtonHandler;
import com.kristina.coach.telegrambot.coachtelegrambot.service.provider.Provider;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.DefaultStep;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.StepExecutor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Service
@Getter
public class CoachTelegramBot extends MultiSessionTelegramBot {


    private final Provider<CommandExecutor> commandExecutorProvider;
    private final Provider<ButtonHandler> buttonHandlerProvider;
    private final Provider<StepExecutor> stepExecutorProvider;
    private final Map<Long, StepExecutor> stepExecutors = new HashMap<>();
    private final Map<Long, Food> foods = new HashMap<>();


    public CoachTelegramBot(TelegramBotsApi botsApi,
                            TelegramBotProperties properties,
                            Provider<CommandExecutor> commandExecutorProvider,
                            Provider<ButtonHandler> buttonHandlerProvider, Provider<StepExecutor> stepExecutorProvider) throws TelegramApiException {
        super(properties.getName(), properties.getToken());
        this.commandExecutorProvider = commandExecutorProvider;
        this.buttonHandlerProvider = buttonHandlerProvider;
        this.stepExecutorProvider = stepExecutorProvider;
        botsApi.registerBot(this);
        initMainMenu(properties.getCommands());
    }

    @Override
    public void onUpdateEventReceived(EventType eventType) {
        switch (eventType) {
            case COMMAND_RECEIVED -> commandExecutorProvider.get(getMessageText()).execute(this);
            case BUTTON_PRESSED -> buttonHandlerProvider.get(getCallbackQueryButtonKey()).execute(this);
            case STEP_EXECUTION -> getCurrentStep().execute(this, getMessageText());
        }
    }

    public void setCurrentStep(String name) {
        stepExecutors.put(getCurrentChatId(), stepExecutorProvider.get(name));
    }

    public void setRawFoodWeight(double rawFoodWeight) {
        Long chatId = getCurrentChatId();
        Food food = getUserFood();
        food.setRawWeight(rawFoodWeight);
        foods.putIfAbsent(chatId, food);
    }

    public void setCookedFoodWeight(double cookedFoodWeight) {
        Long chatId = getCurrentChatId();
        Food food = getUserFood();
        food.setCookedWeight(cookedFoodWeight);
        foods.putIfAbsent(chatId, food);
    }

    public Food getUserFood() {
        return foods.getOrDefault(getCurrentChatId(), new Food());
    }

    public StepExecutor getCurrentStep() {
        return stepExecutors.getOrDefault(getCurrentChatId(), stepExecutorProvider.get(DefaultStep.NAME));
    }
}
