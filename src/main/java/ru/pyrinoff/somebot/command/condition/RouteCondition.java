package ru.pyrinoff.somebot.command.condition;

import lombok.Getter;
import ru.pyrinoff.somebot.model.Message;

@Getter
public class RouteCondition {

    protected MultiRuleset multiRuleset;

    protected boolean processNextHandlerAfterThat;

    protected String methodName;

    public RouteCondition(String methodName, boolean processNextHandlerAfterThat, MultiRuleset multiRuleset) {
        this.methodName = methodName;
        this.multiRuleset = multiRuleset;
        this.processNextHandlerAfterThat = processNextHandlerAfterThat;
    }

    public boolean isFired(Message message) {
        return multiRuleset.isFired(message);
    }


}
