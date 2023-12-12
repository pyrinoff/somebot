package ru.pyrinoff.somebot.service.bot.tg.condition.concrete;

import ru.pyrinoff.somebot.service.bot.tg.TgMessage;
import ru.pyrinoff.somebot.service.bot.tg.condition.AbstractTgCondition;

public class HasMessageTg<M extends TgMessage> implements AbstractTgCondition<M> {

    @Override
    public boolean isFired(final TgMessage message) {
        return message.getOriginalMessage().hasMessage();
    }

}
