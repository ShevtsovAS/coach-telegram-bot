package com.kristina.coach.telegrambot.coachtelegrambot.service.commands;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import com.kristina.coach.telegrambot.coachtelegrambot.util.BotUtil;
import com.kristina.coach.telegrambot.coachtelegrambot.util.KeyboardCreator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.LinkedHashMap;
import java.util.Map;

@Component(ContactCommand.NAME)
@RequiredArgsConstructor
public final class ContactCommand implements CommandExecutor {

    public static final String NAME = "/contact";

    private static final Map<String, String> BUTTONS = creatStartButtons();

    private static Map<String, String> creatStartButtons() {
        Map<String, String> buttons = new LinkedHashMap<>();
        buttons.put("Я в Instagram", "https://www.instagram.com/kristy_shevtsova_ifbb/");
        buttons.put("Я в VK", "https://vk.com/kristy85");
        buttons.put("Я в Telegram", "https://t.me/KristyShevtsova");
        return buttons;
    }

    @SneakyThrows
    @Override
    public void execute(CoachTelegramBot bot) {
        InputFile photo = new InputFile(new ClassPathResource("images/contact.jpg").getInputStream(), bot.getBotUsername());
        String text = BotUtil.getMessageFromResource("contact-info");
        ReplyKeyboard keyboardMarkup = KeyboardCreator.createUrlKeyboardMarkup(BUTTONS);
        bot.sendPhotoMessage(photo, text, keyboardMarkup);
    }
}
