package ru.pyrinoff.somebot.command.condition.concrete;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.condition.ICondition;

public class HasVideo implements ICondition<AbstractMessage> {

    public HasVideo() {
    }

    @Override
    public boolean isFired(final AbstractMessage message) {
        return message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasVideo();
    }

}
