package ru.pyrinoff.somebot.command.condition.concrete;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.condition.ICondition;

public class HasVideoNote implements ICondition<AbstractMessage> {

    public HasVideoNote() {
    }

    @Override
    public boolean isFired(final AbstractMessage message) {
        return message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasVideoNote();
    }

}
