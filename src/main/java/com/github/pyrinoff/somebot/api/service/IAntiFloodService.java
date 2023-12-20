package com.github.pyrinoff.somebot.api.service;

import com.github.pyrinoff.somebot.api.command.ICommandWithTimestampAndChatId;

public interface IAntiFloodService {

    boolean isFloodMessage(ICommandWithTimestampAndChatId message);

}
