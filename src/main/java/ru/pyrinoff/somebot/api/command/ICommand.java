package ru.pyrinoff.somebot.api.command;

import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.command.condition.MultiRuleset;

import java.util.ArrayList;

public interface ICommand<M extends AbstractMessage> {

    ArrayList<MultiRuleset> getFireConditions();

    int getPriority();

    ArrayList<MultiRuleset> setupFireConditions();

    boolean isProceedNextCommand();

    void setProceedNextCommand(boolean proceed);

    boolean isCommandEnabled();

    void setCommandEnabled(boolean disabled);

    boolean isProcessNewCircle();

    void setProcessNewCircle(boolean processNewCircle);

    void process();

    M getMessage();

    void setMessage(M message);

}
