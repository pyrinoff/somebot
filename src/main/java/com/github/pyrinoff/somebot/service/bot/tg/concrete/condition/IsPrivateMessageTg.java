package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;

public class IsPrivateMessageTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    protected final boolean flag;

    public IsPrivateMessageTg(boolean flag) {
        this.flag = flag;
    }

    @Override
    public boolean isFired(final M message) {
        return (
                message.getOriginalMessage().hasMessage()
                        && "private".equals(message.getOriginalMessage().getMessage().getChat().getType())
        ) == flag;
    }

}
