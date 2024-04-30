package com.kristina.coach.telegrambot.coachtelegrambot.service.provider;

import com.kristina.coach.telegrambot.coachtelegrambot.service.commands.CommandExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.commands.UnknownCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandExecutorProvider {

    private final Map<String, CommandExecutor> commandExecutors;
    private final UnknownCommand unknownCommand;

    public CommandExecutor getByMessage(String commandName) {
        return commandExecutors.getOrDefault(commandName, unknownCommand);
    }

}
