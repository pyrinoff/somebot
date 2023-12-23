package com.github.pyrinoff.somebot.api.service;

public interface IMessageProcessingService<Z> {

    void processUpdate(Z update);

}
