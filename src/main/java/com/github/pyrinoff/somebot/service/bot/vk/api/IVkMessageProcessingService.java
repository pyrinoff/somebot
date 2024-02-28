package com.github.pyrinoff.somebot.service.bot.vk.api;

import com.github.pyrinoff.somebot.api.service.IMessageProcessingService;
import com.vk.api.sdk.objects.callback.MessageObject;
import com.vk.api.sdk.objects.callback.VkPayTransaction;

public interface IVkMessageProcessingService extends IMessageProcessingService<MessageObject> {

    void processUpdate(MessageObject update);

    @Deprecated
    void processVkPayTransaction(VkPayTransaction update);

}
