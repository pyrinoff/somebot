package com.github.pyrinoff.somebot.api.service;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;

public interface IMessageProcessingService<Z, M extends AbstractMessage<Z>> {

    void processUpdate(Z update);

}
