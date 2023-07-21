package ru.pyrinoff.somebot.api.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface IMessageProcessingService {

    void process(Update update);

}
