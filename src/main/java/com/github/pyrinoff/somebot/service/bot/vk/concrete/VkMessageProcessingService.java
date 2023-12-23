package com.github.pyrinoff.somebot.service.bot.vk.concrete;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.AbstractMessageProcessingService;
import com.github.pyrinoff.somebot.service.bot.vk.api.IVkMessageProcessingService;
import com.vk.api.sdk.objects.callback.MessageObject;
import org.springframework.stereotype.Component;

@Component
public class VkMessageProcessingService
        extends AbstractMessageProcessingService<MessageObject, User, VkMessage>
        implements IVkMessageProcessingService {


    @Override
    protected VkMessage convertUpdateToMessage(MessageObject update) {
        return new VkMessage(update);
    }

}
