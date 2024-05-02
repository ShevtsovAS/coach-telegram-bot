package com.kristina.coach.telegrambot.coachtelegrambot.service.steps;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Optional;

public interface Step {
    String getMessage();

    String getStepExecutorName();

    ReplyKeyboard getReplyKeyboard();

    Optional<Step> getNext();
}
