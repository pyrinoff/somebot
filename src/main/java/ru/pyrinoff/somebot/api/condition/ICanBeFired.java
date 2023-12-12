package ru.pyrinoff.somebot.api.condition;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;

public interface ICanBeFired<Z, M extends AbstractMessage<Z>> {

    boolean isFired(final M message);

}
