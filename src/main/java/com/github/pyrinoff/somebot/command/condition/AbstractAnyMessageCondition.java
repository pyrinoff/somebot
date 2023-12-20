package com.github.pyrinoff.somebot.command.condition;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.api.condition.IConcreteCondition;

public interface AbstractAnyMessageCondition<M extends AbstractMessage<Object>> extends IConcreteCondition<Object, M> {

    default Class<Object> getMessageClass() {
        return Object.class;
    }

}
