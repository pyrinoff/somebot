package com.github.pyrinoff.somebot.service.bot.vk.condition.concrete;

import com.github.pyrinoff.somebot.service.bot.vk.VkMessage;
import com.github.pyrinoff.somebot.service.bot.vk.AbstractVkCondition;

public class HasMessageVk<M extends VkMessage> implements AbstractVkCondition<M> {

    public HasMessageVk() {
    }

    @Override
    public boolean isFired(final VkMessage message) {
        return message.getText() != null && !message.getText().isEmpty();
    }

}
