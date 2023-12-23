package com.github.pyrinoff.somebot.service.bot.vk.concrete;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.abstraction.AbstractVkMessage;
import com.vk.api.sdk.objects.callback.MessageObject;

public class VkMessage extends AbstractVkMessage<User> {

    public VkMessage(MessageObject originalMessage) {
        super(originalMessage);
    }
}