package com.github.pyrinoff.somebot.service.bot.vk.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.abstraction.AbstractVkMessage;
import com.github.pyrinoff.somebot.service.bot.vk.api.AbstractVkCondition;

public class HasPayloadEqualsVk<U extends User, M extends AbstractVkMessage<U>>
        implements AbstractVkCondition<U, M> {

    final String theText;

    public HasPayloadEqualsVk(final String payload) {
        this.theText = payload;
    }

    public HasPayloadEqualsVk(final Integer payload) {
        this.theText = String.valueOf(payload);
    }

    @Override
    public boolean isFired(final M message) {
        return message.getOriginalMessage().getMessage().getPayload() != null
            && message.getOriginalMessage() != null
            && message.getOriginalMessage().getMessage() != null
            && message.getOriginalMessage().getMessage().getPayload() != null
            && message.getOriginalMessage().getMessage().getPayload().equals(theText);
    }

}
