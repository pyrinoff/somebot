package ru.pyrinoff.somebot.service.bot.tg.condition;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pyrinoff.somebot.api.condition.IConcreteCondition;
import ru.pyrinoff.somebot.service.bot.tg.TgMessage;

public interface AbstractTgCondition<M extends TgMessage> extends IConcreteCondition<Update, M> {

    default Class<Update> getMessageClass() {
        return Update.class;
    }

}
