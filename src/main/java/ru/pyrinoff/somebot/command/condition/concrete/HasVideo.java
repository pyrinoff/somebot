package ru.pyrinoff.somebot.command.condition.concrete;

import ru.pyrinoff.somebot.api.condition.ICondition;
import ru.pyrinoff.somebot.model.Message;

public class HasVideo implements ICondition {


    public HasVideo() {
    }

    @Override
    public boolean isFired(final Message message) {
        return message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasVideo();
    }

}
