package com.github.pyrinoff.somebot.service.bot.vk.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.abstraction.AbstractVkMessage;
import com.github.pyrinoff.somebot.service.bot.vk.api.AbstractVkCondition;

/**
 * Имеет сообщение, ID пользователя, не от бота, приватное
 */
public class BasedPrivateMessageVk<U extends User, M extends AbstractVkMessage<U>> implements AbstractVkCondition<U, M> {

    @Override
    public boolean isFired(final M message) {
        return
                //has user
                message.hasUser()
                //private, not bot or group
                && message.getOriginalMessage().getMessage().getPeerId() > 0
                && message.getOriginalMessage().getMessage().getPeerId() < 2000000000
                && message.getOriginalMessage().getMessage().getPeerId()
                .equals(message.getOriginalMessage().getMessage().getFromId());
    }

}
