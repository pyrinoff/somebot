package ru.pyrinoff.somebot.command.condition;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.condition.IConcreteCondition;

public interface AbstractAnyMessageCondition<M extends AbstractMessage<Object>> extends IConcreteCondition<Object, M> {

    default Class<Object> getMessageClass() {
        return Object.class;
    }

}
