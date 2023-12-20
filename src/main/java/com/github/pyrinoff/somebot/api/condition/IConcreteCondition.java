package com.github.pyrinoff.somebot.api.condition;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;

public interface IConcreteCondition<Z, M extends AbstractMessage<Z>> extends ICanBeFired<Z, M> {

    Class<Z> getMessageClass(); //для предотвращения ошибки класскаста, см. Ruleset.java

}
