package ru.pyrinoff.somebot.service.bot.tg.condition.concrete;

import ru.pyrinoff.somebot.service.bot.tg.TgMessage;
import ru.pyrinoff.somebot.service.bot.tg.condition.AbstractTgCondition;

public class HasAnyTextTg<M extends TgMessage> implements AbstractTgCondition<M> {

    protected final boolean simple;

    public HasAnyTextTg(boolean simple) {
        this.simple = simple;
    }

    @Override
    public boolean isFired(final TgMessage message) {
        return (message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasText()) == simple;
    }

}
