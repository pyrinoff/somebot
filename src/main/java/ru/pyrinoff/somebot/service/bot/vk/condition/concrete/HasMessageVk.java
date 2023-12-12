package ru.pyrinoff.somebot.service.bot.vk.condition.concrete;

import ru.pyrinoff.somebot.service.bot.vk.AbstractVkCondition;
import ru.pyrinoff.somebot.service.bot.vk.VkMessage;

public class HasMessageVk<M extends VkMessage> implements AbstractVkCondition<M> {

    public HasMessageVk() {
    }

    @Override
    public boolean isFired(final VkMessage message) {
        return message.getText() != null && !message.getText().isEmpty();
    }

}
