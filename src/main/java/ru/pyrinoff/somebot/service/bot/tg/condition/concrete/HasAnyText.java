/*
package ru.pyrinoff.somebot.service.bot.tg.condition.concrete;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.condition.ICondition;
import ru.pyrinoff.somebot.service.bot.tg.TgMessage;
import ru.pyrinoff.somebot.service.bot.tg.condition.AbstractTgCondition;

public class HasAnyText<M extends TgMessage> extends AbstractTgCondition<M> {

    protected final boolean simple;

    public HasAnyText(boolean simple) {
        this.simple = simple;
    }

    @Override
    public boolean isFired(final TgMessage message) {
        return (message.getOriginalMessage().hasMessage() && message.getOriginalMessage().getMessage().hasText()) == simple;
    }

}
*/
