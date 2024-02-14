package com.github.pyrinoff.somebot.service.bot.vk.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.abstraction.AbstractVkMessage;
import com.github.pyrinoff.somebot.service.bot.vk.api.AbstractVkCondition;

import java.util.Arrays;

public class IsMessageFromIdVk<U extends User, M extends AbstractVkMessage<U>> implements AbstractVkCondition<U, M> {

    protected long[] chatIds;

    public IsMessageFromIdVk(long... chatIds) {
        this.chatIds = chatIds;
    }

    @Override
    public boolean isFired(final M message) {
        return message.getOriginalMessage().getMessage() != null
                && Arrays.binarySearch(this.chatIds, message.getOriginalMessage().getMessage().getPeerId()) >= 0;
    }

}
