package ru.pyrinoff.somebot.command.condition.concrete;

import ru.pyrinoff.somebot.api.condition.ICondition;
import ru.pyrinoff.somebot.model.Message;

public class HasMessage implements ICondition {

    public HasMessage() {
    }

    @Override
    public boolean isFired(final Message message) {
        return message.getOriginalMessage().hasMessage();
    }

}
