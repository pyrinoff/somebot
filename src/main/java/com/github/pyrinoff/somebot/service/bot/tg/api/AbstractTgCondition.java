package com.github.pyrinoff.somebot.service.bot.tg.api;

import com.github.pyrinoff.somebot.api.condition.IConcreteCondition;
import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface AbstractTgCondition<U extends User, M extends AbstractTgMessage<U>>
        extends IConcreteCondition<Update, U, M> {

    default Class<Update> getMessageClass() {
        return Update.class;
    }

}
