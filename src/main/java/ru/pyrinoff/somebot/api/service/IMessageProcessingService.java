package ru.pyrinoff.somebot.api.service;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;

public interface IMessageProcessingService<Z, M extends AbstractMessage<Z>> {

    void processUpdate(Z update);

}
