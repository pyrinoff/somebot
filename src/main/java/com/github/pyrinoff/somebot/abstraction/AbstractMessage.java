package com.github.pyrinoff.somebot.abstraction;

import com.github.pyrinoff.somebot.api.command.ICommandWithTimestampAndChatId;
import lombok.Getter;

@Getter
public abstract class AbstractMessage<M> implements ICommandWithTimestampAndChatId {

    private M originalMessage;

    public AbstractMessage(M originalMessage) {
        this.originalMessage = originalMessage;
    }

    public M getOriginalMessage() {
        return originalMessage;
    }

//    public abstract Long getSenderChatId();

//    public abstract Integer getMessageTimestamp();



}
