package ru.pyrinoff.somebotexamples.example.abstracts;

import ru.pyrinoff.somebot.abstraction.AbstractCommand;
import ru.pyrinoff.somebotexamples.example.model.User;
import ru.pyrinoff.somebotexamples.example.model.CustomMessage;

public abstract class CustomCommand extends AbstractCommand<CustomMessage> {

    @Override
    public CustomMessage getMessage() {
        return super.getMessage();
    }

    public User getUser() {
        return getMessage().getUser();
    }

}
