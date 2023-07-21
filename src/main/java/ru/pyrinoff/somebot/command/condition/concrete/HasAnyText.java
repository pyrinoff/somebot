package ru.pyrinoff.somebot.command.condition.concrete;

import ru.pyrinoff.somebot.api.condition.ICondition;
import ru.pyrinoff.somebot.model.Message;

public class HasAnyText implements ICondition {

    protected final boolean simple;

    public HasAnyText(boolean simple) {
        this.simple = simple;
    }

    @Override
    public boolean isFired(final Message message) {
        return (message.getOriginalMessage().hasMessage() && message.getOriginalMessage().getMessage().hasText()) == simple;
    }

}
