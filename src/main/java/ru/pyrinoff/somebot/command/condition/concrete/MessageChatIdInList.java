package ru.pyrinoff.somebot.command.condition.concrete;

import ru.pyrinoff.somebot.api.condition.ICondition;
import ru.pyrinoff.somebot.model.Message;

import java.util.Arrays;

public class MessageChatIdInList implements ICondition {

    protected long[] chatIds;

    public MessageChatIdInList(long... chatIds) {
        this.chatIds = chatIds;
    }

    @Override
    public boolean isFired(final Message message) {
        return message.getOriginalMessage().hasMessage()
                && Arrays.binarySearch(chatIds, message.getOriginalMessage().getMessage().getChatId()) >= 0;
    }

}
