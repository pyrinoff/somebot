package com.github.pyrinoff.somebot.api.condition;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;

public interface ICanBeFired<Z, M extends AbstractMessage<Z>> {

    boolean isFired(final M message);

}
