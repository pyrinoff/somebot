package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;

import java.util.Arrays;

public class IsMessageFromIdTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    //final Set<Long> chatIds;
    protected long[] chatIds;

    public IsMessageFromIdTg(long... chatIds) {
        this.chatIds = chatIds;
    }

    @Override
    public boolean isFired(final M message) {
        //return message.getOriginalMessage().hasMessage() && chatIds.contains(message.getOriginalMessage().getMessage().getChat().getId());
        return message.getOriginalMessage().hasMessage()
                && Arrays.binarySearch(this.chatIds, message.getOriginalMessage().getMessage().getChatId()) >= 0;
    }

}
