package ru.pyrinoff.somebot.api.condition;

import ru.pyrinoff.somebot.model.Message;

public interface ICondition {

    boolean isFired(final Message message);

}
