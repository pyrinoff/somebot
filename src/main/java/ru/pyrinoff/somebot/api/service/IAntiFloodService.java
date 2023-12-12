package ru.pyrinoff.somebot.api.service;

import ru.pyrinoff.somebot.api.command.ICommandWithTimestampAndChatId;

public interface IAntiFloodService {

    boolean isFloodMessage(ICommandWithTimestampAndChatId message);

}
