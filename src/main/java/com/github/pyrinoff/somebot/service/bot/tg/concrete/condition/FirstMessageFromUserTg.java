package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;

public class FirstMessageFromUserTg <U extends User, M extends AbstractTgMessage<U>>
        implements AbstractTgCondition<U, M> {

    public FirstMessageFromUserTg() {}

    @Override
    public boolean isFired(final M message) {
        return message.isFirstMessageFromUser();
    }

}
