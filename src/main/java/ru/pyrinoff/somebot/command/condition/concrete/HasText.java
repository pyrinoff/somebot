package ru.pyrinoff.somebot.command.condition.concrete;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.condition.ICondition;

public class HasText implements ICondition<AbstractMessage> {

    final String theText;

    public HasText(final String text) {
        this.theText = text;
    }

    @Override
    public boolean isFired(final AbstractMessage message) {
        return message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasText()
                && message.getOriginalMessage().getMessage().getText().contains(theText);
    }

}
