package com.github.pyrinoff.somebot.service.bot.tg.condition;

import com.github.pyrinoff.somebot.api.condition.IConcreteCondition;
import com.github.pyrinoff.somebot.service.bot.tg.TgMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface AbstractTgCondition<M extends TgMessage> extends IConcreteCondition<Update, M> {

    default Class<Update> getMessageClass() {
        return Update.class;
    }

}
