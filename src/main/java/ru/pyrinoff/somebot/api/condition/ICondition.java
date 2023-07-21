package ru.pyrinoff.somebot.api.condition;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;

public interface ICondition<M extends AbstractMessage> {

    boolean isFired(final M message);

}
