package com.github.pyrinoff.somebot.service.bot.vk.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.abstraction.AbstractVkMessage;
import com.github.pyrinoff.somebot.service.bot.vk.api.AbstractVkCondition;

public class TextStartWithVk<U extends User, M extends AbstractVkMessage<U>>
        implements AbstractVkCondition<U, M> {

    final String theText;

    public TextStartWithVk(final String text) {
        this.theText = text;
    }

    @Override
    public boolean isFired(final M message) {
        return message.getText() != null && !message.getText().isEmpty()
                && message.getText().startsWith(theText);
    }

}
