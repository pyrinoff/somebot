package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;
import com.github.pyrinoff.somebot.service.bot.tg.concrete.TgMessage;

public class HasAnyTextTg2 implements AbstractTgCondition<User, TgMessage> {

    protected final boolean simple;

    public HasAnyTextTg2(boolean simple) {
        this.simple = simple;
    }

    @Override
    public boolean isFired(final TgMessage message) {
        return (message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasText()) == simple;
    }

}
