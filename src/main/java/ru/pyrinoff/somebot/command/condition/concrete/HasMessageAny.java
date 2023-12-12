package ru.pyrinoff.somebot.command.condition.concrete;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.command.condition.AbstractAnyMessageCondition;

public class HasMessageAny<M extends AbstractMessage<Object>> implements AbstractAnyMessageCondition<M> {

    public HasMessageAny() {
    }

    @Override
    public boolean isFired(final M message) {
        return message.getOriginalMessage() != null;
    }

}
