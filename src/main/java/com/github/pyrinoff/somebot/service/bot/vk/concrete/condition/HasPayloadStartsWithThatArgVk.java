package com.github.pyrinoff.somebot.service.bot.vk.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.abstraction.AbstractVkMessage;
import com.github.pyrinoff.somebot.service.bot.vk.api.AbstractVkCondition;
import com.github.pyrinoff.somebot.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HasPayloadStartsWithThatArgVk<U extends User, M extends AbstractVkMessage<U>>
        implements AbstractVkCondition<U, M> {

    final List<String> theText;

    public HasPayloadStartsWithThatArgVk(final String... payload) {
        this.theText = List.of(payload);
    }

    public HasPayloadStartsWithThatArgVk(final int... payload) {
        this.theText = Arrays.stream(payload).mapToObj(String::valueOf).collect(Collectors.toList());
    }

    @Override
    public boolean isFired(final M message) {
        if(message.getPayload() == null) return false;
        String firstArg = StringUtil.getArgString(message.getPayload(), 0, " ", 1024, false);
        return theText.contains(firstArg);
    }

}
