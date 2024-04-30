package com.kristina.coach.telegrambot.coachtelegrambot.service.commands;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.service.handlers.FoodWeightButtonHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.LinkedHashMap;
import java.util.Map;

@Component(StartCommand.NAME)
@RequiredArgsConstructor
public final class StartCommand implements CommandExecutor {

    public static final String NAME = "/start";

    private static final Map<String, String> START_BUTTONS = creatStartButtons();

    private static Map<String, String> creatStartButtons() {
        Map<String, String> buttons = new LinkedHashMap<>();
        buttons.put("Расчитать БЖУ", "pfc_btn");
        buttons.put("Расчитать вес готовой еды", FoodWeightButtonHandler.NAME);
        return buttons;
    }

    @SneakyThrows
    @Override
    public void execute(CoachTelegramBot bot) {
        InputFile photo = new InputFile(new ClassPathResource("images/start.png").getInputStream(), bot.getBotUsername());
        String text = new String(new ClassPathResource("messages/start.txt").getContentAsByteArray());
        bot.sendPhotoMessage(photo, text, START_BUTTONS);
    }
}
