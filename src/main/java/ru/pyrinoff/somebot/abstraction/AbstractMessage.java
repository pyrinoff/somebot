package ru.pyrinoff.somebot.abstraction;

import lombok.Getter;
import ru.pyrinoff.somebot.api.command.ICommandWithTimestampAndChatId;

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
