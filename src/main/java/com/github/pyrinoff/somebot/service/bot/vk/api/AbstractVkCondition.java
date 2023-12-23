package com.github.pyrinoff.somebot.service.bot.vk.api;

import com.github.pyrinoff.somebot.api.condition.IConcreteCondition;
import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.abstraction.AbstractVkMessage;
import com.vk.api.sdk.objects.callback.MessageObject;

public interface AbstractVkCondition<U extends User, M extends AbstractVkMessage<U>> extends IConcreteCondition<MessageObject, U, M> {

    default Class<MessageObject> getMessageClass() {
        return MessageObject.class;
    }

}
