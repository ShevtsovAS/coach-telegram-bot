package com.kristina.coach.telegrambot.coachtelegrambot.service.providers;

import com.kristina.coach.telegrambot.coachtelegrambot.service.commands.CommandExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.commands.UnknownCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandExecutorProvider implements Provider<CommandExecutor> {

    private final Map<String, CommandExecutor> commandExecutors;
    private final UnknownCommand unknownCommand;

    @Override
    public CommandExecutor get(String key) {
        return commandExecutors.getOrDefault(key, unknownCommand);
    }

}
