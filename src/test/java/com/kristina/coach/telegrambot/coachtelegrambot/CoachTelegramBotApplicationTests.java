package com.kristina.coach.telegrambot.coachtelegrambot;

import com.kristina.coach.telegrambot.coachtelegrambot.service.CoachTelegramBot;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@SpringBootTest
class CoachTelegramBotApplicationTests {

    @MockBean
    TelegramBotsApi telegramBotsApi;

    @MockBean
    CoachTelegramBot coachTelegramBot;

    @Test
    void contextLoads() {
    }

}
