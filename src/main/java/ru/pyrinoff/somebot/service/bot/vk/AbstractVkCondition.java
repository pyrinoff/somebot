package ru.pyrinoff.somebot.service.bot.vk;

import com.vk.api.sdk.objects.callback.MessageObject;
import ru.pyrinoff.somebot.api.condition.IConcreteCondition;

public interface AbstractVkCondition<M extends VkMessage> extends IConcreteCondition<MessageObject, M> {

    default Class<MessageObject> getMessageClass() {
        return MessageObject.class;
    }

}
