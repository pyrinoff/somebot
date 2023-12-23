package com.github.pyrinoff.somebot.model;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;

public class AnyMessage<U extends User> extends AbstractMessage<Object, U> {

    public AnyMessage(Object originalMessage) {
        super(originalMessage);
    }

    @Override
    public Long getSenderChatId() {
        return null;
    }

    @Override
    public Integer getMessageTimestamp() {
        return (int) System.currentTimeMillis()/1000;
    }
}
