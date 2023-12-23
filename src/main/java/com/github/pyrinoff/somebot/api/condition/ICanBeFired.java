package com.github.pyrinoff.somebot.api.condition;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.model.User;

public interface ICanBeFired<Z, U extends User, M extends AbstractMessage<Z, U>> {

    boolean isFired(final M message);

}
