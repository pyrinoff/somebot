package com.github.pyrinoff.somebot.api.component;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface IMessagePool {

    void addMessageToProcessingList(Update update);

}
