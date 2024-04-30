package com.kristina.coach.telegrambot.coachtelegrambot.service.provider;

public interface Provider<T> {

    T get(String key);

}
