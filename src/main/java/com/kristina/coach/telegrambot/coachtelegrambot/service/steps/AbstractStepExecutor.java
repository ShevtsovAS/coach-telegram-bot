package com.kristina.coach.telegrambot.coachtelegrambot.service.steps;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;

public abstract class AbstractStepExecutor implements StepExecutor {

    @Override
    public void execute(CoachTelegramBot bot, String inputMsg) {
        if (executeStep(bot, inputMsg)) {
            executeNextStep(bot);
        }
    }

    private void executeNextStep(CoachTelegramBot bot) {
        if (getCurrentStep() == null) {
            return;
        }

        getCurrentStep().getNext().ifPresentOrElse(
                nextStep -> executeNextStep(bot, nextStep),
                () -> bot.setCurrentStep(DefaultStepExecutor.NAME));
    }

    private void executeNextStep(CoachTelegramBot bot, Step nextStep) {
        bot.setCurrentStep(nextStep.getStepExecutorName());
        sendMessage(bot, nextStep);
    }

    protected void sendMessage(CoachTelegramBot bot, Step nextStep) {
        bot.sendTextMessage(nextStep.getMessage(), nextStep.getReplyKeyboard());
    }

    public abstract boolean executeStep(CoachTelegramBot bot, String inputMsg);
}
