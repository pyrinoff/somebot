package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;

public class IsReplyMessageTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    protected final boolean simple;

    public IsReplyMessageTg(boolean simple) {
        this.simple = simple;
    }

    @Override
    public boolean isFired(final M message) {
        return message.getOriginalMessage().hasMessage()
                && (message.getOriginalMessage().getMessage().getReplyToMessage() != null) == simple;
    }

}
