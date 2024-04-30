package com.kristina.coach.telegrambot.coachtelegrambot.service.handlers;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Log4j2
@Component(UnknownButtonHandler.NAME)
public class UnknownButtonHandler implements ButtonHandler {

    public static final String NAME = "unknownButton";

    @Override
    public void execute(CoachTelegramBot bot) {
        String buttonKey = bot.getCallbackQueryButtonKey();
        if (StringUtils.isNotBlank(buttonKey)) {
            log.info("Unknown buttonKey was received - {}", buttonKey);
        }
    }
}
