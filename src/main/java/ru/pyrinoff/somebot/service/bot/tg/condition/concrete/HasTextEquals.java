/*
package ru.pyrinoff.somebot.service.bot.tg.condition.concrete;

import ru.pyrinoff.somebot.service.bot.tg.ITgCondition;
import ru.pyrinoff.somebot.service.bot.tg.TgMessage;

public class HasTextEquals<M extends TgMessage> implements ITgCondition {

    final String theText;

    public HasTextEquals(final String text) {
        this.theText = text;
    }

    @Override
    public boolean isFired(final TgMessage message) {
        return message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasText()
                && message.getOriginalMessage().getMessage().getText().equals(theText);
    }

}
*/
