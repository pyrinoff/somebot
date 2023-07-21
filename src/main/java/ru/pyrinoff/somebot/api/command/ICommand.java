package ru.pyrinoff.somebot.api.command;

import ru.pyrinoff.somebot.command.condition.MultiRuleset;
import ru.pyrinoff.somebot.model.Message;

import java.util.ArrayList;

public interface ICommand {

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

    Message getMessage();

    void setMessage(Message message);

}
