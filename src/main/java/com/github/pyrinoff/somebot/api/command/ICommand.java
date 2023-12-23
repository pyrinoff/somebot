package com.github.pyrinoff.somebot.api.command;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.command.condition.MultiRuleset;
import com.github.pyrinoff.somebot.model.User;

import java.util.ArrayList;

public interface ICommand<Z, U extends User, M extends AbstractMessage<Z, U>> {

    ArrayList<MultiRuleset<Z, U, M>> getFireConditions();

    int getPriority();

    ArrayList<MultiRuleset<Z, U, M>> setupFireConditions();

    boolean isProceedNextCommand();

    void setProceedNextCommand(boolean proceed);

    boolean isCommandEnabled();

    void setCommandEnabled(boolean disabled);

    boolean isProcessNewCircle();

    void setProcessNewCircle(boolean processNewCircle);

    void process();

    M getMessage();

    default Z getOriginalMessage() {
        return getMessage().getOriginalMessage();
    }

    void setMessage(M message);

}
