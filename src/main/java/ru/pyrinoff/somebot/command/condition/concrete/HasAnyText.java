package ru.pyrinoff.somebot.command.condition.concrete;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.condition.ICondition;

public class HasAnyText implements ICondition<AbstractMessage> {

    protected final boolean simple;

    public HasAnyText(boolean simple) {
        this.simple = simple;
    }

    @Override
    public boolean isFired(final AbstractMessage message) {
        return (message.getOriginalMessage().hasMessage() && message.getOriginalMessage().getMessage().hasText()) == simple;
    }

}
