package com.github.pyrinoff.somebot.command.condition.concrete;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.command.condition.AbstractAnyMessageCondition;

public class HasMessageAny<M extends AbstractMessage<Object>> implements AbstractAnyMessageCondition<M> {

    public HasMessageAny() {
    }

    @Override
    public boolean isFired(final M message) {
        return message.getOriginalMessage() != null;
    }

}
