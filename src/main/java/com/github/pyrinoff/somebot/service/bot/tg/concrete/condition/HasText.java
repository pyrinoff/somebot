/*
package ru.pyrinoff.somebot.service.bot.tg.condition.concrete;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.condition.ICondition;
import ru.pyrinoff.somebot.service.bot.tg.ITgCondition;
import ru.pyrinoff.somebot.service.bot.tg.TgMessage;

public class HasText<M extends TgMessage> implements ITgCondition {

    final String theText;

    public HasText(final String text) {
        this.theText = text;
    }

    @Override
    public boolean isFired(final TgMessage message) {
        return message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasText()
                && message.getOriginalMessage().getMessage().getText().contains(theText);
    }

}
*/
