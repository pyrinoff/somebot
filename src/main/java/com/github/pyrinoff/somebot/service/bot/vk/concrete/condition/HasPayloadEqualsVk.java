package com.github.pyrinoff.somebot.service.bot.vk.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.abstraction.AbstractVkMessage;
import com.github.pyrinoff.somebot.service.bot.vk.api.AbstractVkCondition;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HasPayloadEqualsVk<U extends User, M extends AbstractVkMessage<U>>
        implements AbstractVkCondition<U, M> {

    final List<String> theText;

    public HasPayloadEqualsVk(final String ...payload) {
        this.theText = List.of(payload);
    }

    public HasPayloadEqualsVk(final int ...payload) {
        this.theText = Arrays.stream(payload).mapToObj(String::valueOf).collect(Collectors.toList());
    }

    @Override
    public boolean isFired(final M message) {
        return message.getPayload() != null
            && theText.contains( message.getPayload());
    }

}
