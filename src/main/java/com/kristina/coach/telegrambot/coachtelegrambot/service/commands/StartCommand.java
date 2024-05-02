package com.kristina.coach.telegrambot.coachtelegrambot.service.commands;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.handlers.FoodWeightButtonHandler;
import com.kristina.coach.telegrambot.coachtelegrambot.service.handlers.PfcButtonHandler;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.DefaultStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.util.KeyboardCreator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.LinkedHashMap;
import java.util.Map;

@Component(StartCommand.NAME)
@RequiredArgsConstructor
public final class StartCommand implements CommandExecutor {

    public static final String NAME = "/start";

    private static final Map<String, String> START_BUTTONS = creatStartButtons();

    private static Map<String, String> creatStartButtons() {
        Map<String, String> buttons = new LinkedHashMap<>();
        buttons.put("Расчитать БЖУ", PfcButtonHandler.NAME);
        buttons.put("Расчитать вес готовой еды", FoodWeightButtonHandler.NAME);
        return buttons;
    }

    @SneakyThrows
    @Override
    public void execute(CoachTelegramBot bot) {
        String userNameFrom = bot.getUserNameFrom();
        bot.sendTextMessage(String.format("Привет %s!", userNameFrom), new ReplyKeyboardRemove(true));
        InputFile photo = new InputFile(new ClassPathResource("images/start.jpg").getInputStream(), bot.getBotUsername());
        String text = new String(new ClassPathResource("messages/start.txt").getContentAsByteArray());
        ReplyKeyboard keyboardMarkup = KeyboardCreator.createInlineKeyboardMarkup(START_BUTTONS);
        bot.sendPhotoMessage(photo, String.format(text, userNameFrom), keyboardMarkup);
        bot.setCurrentStep(DefaultStepExecutor.NAME);
    }
}
