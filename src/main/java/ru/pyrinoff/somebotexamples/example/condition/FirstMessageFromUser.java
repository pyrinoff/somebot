package ru.pyrinoff.somebotexamples.example.condition;

import ru.pyrinoff.somebot.api.condition.ICondition;
import ru.pyrinoff.somebotexamples.example.model.CustomMessage;

public class FirstMessageFromUser implements ICondition<CustomMessage> {

    public FirstMessageFromUser() {}

    @Override
    public boolean isFired(final CustomMessage message) {
        return message.isFirstMessageFromUser();
    }

}
