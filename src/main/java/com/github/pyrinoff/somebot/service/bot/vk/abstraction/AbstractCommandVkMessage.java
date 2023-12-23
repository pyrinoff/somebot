package com.github.pyrinoff.somebot.service.bot.vk.abstraction;

import com.github.pyrinoff.somebot.abstraction.AbstractCommand;
import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.VkBot;
import com.vk.api.sdk.objects.callback.MessageObject;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public abstract class AbstractCommandVkMessage<U extends User, M extends AbstractVkMessage<U>> extends AbstractCommand<MessageObject, U, M> {

    @Autowired
    @Lazy
    @Getter
    private VkBot bot;

}
