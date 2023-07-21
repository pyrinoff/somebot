package ru.pyrinoff.somebot.command.condition;


import ru.pyrinoff.somebot.abstraction.AbstractCommand;
import ru.pyrinoff.somebot.abstraction.AbstractMessage;

import java.util.ArrayList;
import java.util.Arrays;


public class MultiRuleset {

    ArrayList<Ruleset> ruleset;

    public boolean isFired(AbstractMessage message) {
        for (Ruleset oneConditionList : ruleset) {
            if (oneConditionList.isFired(message)) {
                AbstractCommand.logger.debug("ONE OF RULESET IS FIRED! GO TO PROCESSING");
                return true;
            }
        }
        return false;
    }

    public MultiRuleset(Ruleset[] ruleset) {
        this.ruleset = new ArrayList<>(Arrays.asList(ruleset));
    }

}
