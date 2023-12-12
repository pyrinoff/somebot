package ru.pyrinoff.somebot.api.service;

import org.jetbrains.annotations.NotNull;

public interface ITelegramDataProvider {

    @NotNull String getTgToken();

    @NotNull String getTgBotname();

}
