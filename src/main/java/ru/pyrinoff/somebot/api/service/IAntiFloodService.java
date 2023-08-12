package ru.pyrinoff.somebot.api.service;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;

public interface IAntiFloodService {

    boolean isFloodMessage(AbstractMessage message);

}
