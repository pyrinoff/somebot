package com.github.pyrinoff.somebot.service.bot.vk.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.abstraction.AbstractVkMessage;
import com.github.pyrinoff.somebot.service.bot.vk.api.AbstractVkCondition;

public class IsPrivateMessageVk<U extends User, M extends AbstractVkMessage<U>> implements AbstractVkCondition<U, M> {

    public IsPrivateMessageVk() {
    }

    @Override
    public boolean isFired(final M message) {
        return
                message.getOriginalMessage().getMessage().getPeerId() > 0
                && message.getOriginalMessage().getMessage().getPeerId() < 2000000000
                && message.getOriginalMessage().getMessage().getPeerId()
                        .equals(message.getOriginalMessage().getMessage().getFromId());
    }

}
