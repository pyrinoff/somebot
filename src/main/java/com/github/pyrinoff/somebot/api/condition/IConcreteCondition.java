package com.github.pyrinoff.somebot.api.condition;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.model.User;

public interface IConcreteCondition<Z, U extends User, M extends AbstractMessage<Z, U>> extends ICanBeFired<Z, U, M> {

    Class<Z> getMessageClass(); //для предотвращения ошибки класскаста, см. Ruleset.java

}
