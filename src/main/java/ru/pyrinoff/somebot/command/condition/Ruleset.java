package ru.pyrinoff.somebot.command.condition;


import ru.pyrinoff.somebot.abstraction.AbstractCommand;
import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.condition.ICondition;

import java.util.ArrayList;
import java.util.Arrays;

public class Ruleset implements ICondition {

    ArrayList<ICondition> conditions;

    public boolean isFired(AbstractMessage message) {
        for (ICondition oneCondition : conditions) {
            AbstractCommand.logger.debug("Check condition: " + oneCondition);
            if (!oneCondition.isFired(message)) {
                AbstractCommand.logger.debug("CONDITION FAILED! BREAK");
                return false;
            } else AbstractCommand.logger.debug("CONDITION OK! BUT NEED TO CHECK NEXT");
        }
        AbstractCommand.logger.debug("ALL CONDITIONS IN RULESET FIRED! GO!");
        return true;
    }

    public Ruleset addCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    public Ruleset(ICondition[] conditions) {
        this.conditions = new ArrayList<>(Arrays.asList(conditions));
    }


}
