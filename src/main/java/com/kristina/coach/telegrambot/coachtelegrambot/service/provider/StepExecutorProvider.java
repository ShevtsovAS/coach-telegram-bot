package com.kristina.coach.telegrambot.coachtelegrambot.service.provider;

import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.StepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.DefaultStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StepExecutorProvider implements Provider<StepExecutor> {

    private final Map<String, StepExecutor> stepExecutors;
    private final DefaultStep defaultStep;

    @Override
    public StepExecutor get(String key) {
        return stepExecutors.getOrDefault(key, defaultStep);
    }
}
