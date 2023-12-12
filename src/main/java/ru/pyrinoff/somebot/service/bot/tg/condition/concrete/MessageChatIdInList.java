/*
package ru.pyrinoff.somebot.service.bot.tg.condition.concrete;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.condition.ICondition;
import ru.pyrinoff.somebot.service.bot.tg.ITgCondition;
import ru.pyrinoff.somebot.service.bot.tg.TgMessage;

import java.util.Arrays;

public class MessageChatIdInList<M extends TgMessage> implements ITgCondition {

    protected long[] chatIds;

    public MessageChatIdInList(long... chatIds) {
        this.chatIds = chatIds;
    }

    @Override
    public boolean isFired(final TgMessage message) {
        return message.getOriginalMessage().hasMessage()
                && Arrays.binarySearch(chatIds, message.getOriginalMessage().getMessage().getChatId()) >= 0;
    }

}
*/
