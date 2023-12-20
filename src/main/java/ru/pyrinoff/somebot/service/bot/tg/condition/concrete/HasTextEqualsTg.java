package ru.pyrinoff.somebot.service.bot.tg.condition.concrete;

import ru.pyrinoff.somebot.service.bot.tg.TgMessage;
import ru.pyrinoff.somebot.service.bot.tg.condition.AbstractTgCondition;

public class HasTextEqualsTg<M extends TgMessage> implements AbstractTgCondition<M> {

    final String theText;

    public HasTextEqualsTg(final String text) {
        this.theText = text;
    }

    @Override
    public boolean isFired(final TgMessage message) {
        return message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasText()
                && message.getOriginalMessage().getMessage().getText().equals(theText);
    }

}
