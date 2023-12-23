package com.github.pyrinoff.somebot.service.bot.vk.api;

import com.github.pyrinoff.somebot.api.service.IMessageProcessingService;
import com.vk.api.sdk.objects.callback.MessageObject;

public interface IVkMessageProcessingService extends IMessageProcessingService<MessageObject> {

    void processUpdate(MessageObject update);

}
