package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;

/**
 * Имеет сообщение, ID пользователя, не от бота, приватное
 */
public class BasedNonPrivateMessageTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    @Override
    public boolean isFired(final M message) {
        return message.getOriginalMessage().hasMessage()
                && message.hasUser()
                && !"private".equals(message.getOriginalMessage().getMessage().getChat().getType())
                && !message.getOriginalMessage().getMessage().getFrom().getIsBot();
    }

}
