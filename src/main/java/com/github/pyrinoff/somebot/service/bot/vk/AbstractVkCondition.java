package com.github.pyrinoff.somebot.service.bot.vk;

import com.github.pyrinoff.somebot.api.condition.IConcreteCondition;
import com.vk.api.sdk.objects.callback.MessageObject;

public interface AbstractVkCondition<M extends VkMessage> extends IConcreteCondition<MessageObject, M> {

    default Class<MessageObject> getMessageClass() {
        return MessageObject.class;
    }

}
