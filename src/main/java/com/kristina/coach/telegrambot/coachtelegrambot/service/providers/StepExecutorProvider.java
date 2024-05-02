package com.kristina.coach.telegrambot.coachtelegrambot.service.providers;

import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.DefaultStepExecutor;
import com.kristina.coach.telegrambot.coachtelegrambot.service.steps.StepExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StepExecutorProvider implements Provider<StepExecutor> {

    private final Map<String, StepExecutor> stepExecutors;
    private final DefaultStepExecutor defaultStepExecutor;

    @Override
    public StepExecutor get(String key) {
        return stepExecutors.getOrDefault(key, defaultStepExecutor);
    }
}
