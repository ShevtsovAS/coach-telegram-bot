package com.kristina.coach.telegrambot.coachtelegrambot.service.providers;

public interface Provider<T> {

    T get(String key);

}
