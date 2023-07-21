package ru.pyrinoff.somebot.command.condition.concrete;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.condition.ICondition;

import java.util.Arrays;

public class MessageChatIdInList implements ICondition<AbstractMessage> {

    protected long[] chatIds;

    public MessageChatIdInList(long... chatIds) {
        this.chatIds = chatIds;
    }

    @Override
    public boolean isFired(final AbstractMessage message) {
        return message.getOriginalMessage().hasMessage()
                && Arrays.binarySearch(chatIds, message.getOriginalMessage().getMessage().getChatId()) >= 0;
    }

}
