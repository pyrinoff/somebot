package ru.pyrinoff.somebot.command.condition.concrete;

import ru.pyrinoff.somebot.api.condition.ICondition;
import ru.pyrinoff.somebot.model.Message;

public class HasText implements ICondition {

    final String theText;

    public HasText(final String text) {
        this.theText = text;
    }

    @Override
    public boolean isFired(final Message message) {
        return message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasText()
                && message.getOriginalMessage().getMessage().getText().contains(theText);
    }

}
