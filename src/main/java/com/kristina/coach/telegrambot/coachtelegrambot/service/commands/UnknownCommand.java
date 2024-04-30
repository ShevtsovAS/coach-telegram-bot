package com.kristina.coach.telegrambot.coachtelegrambot.service.commands;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Log4j2
@Component(UnknownCommand.NAME)
@RequiredArgsConstructor
public final class UnknownCommand implements CommandExecutor {

    public static final String NAME = "unknown";

    @Override
    public void execute(CoachTelegramBot bot) {
        if (StringUtils.isBlank(bot.getCallbackQueryButtonKey())) {
            log.info("Unknown command was received - {}", bot.getMessageText());
        }
    }

}
