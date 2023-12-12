package ru.pyrinoff.somebot.command.condition;


import ru.pyrinoff.somebot.abstraction.AbstractCommand;
import ru.pyrinoff.somebot.abstraction.AbstractMessage;

import java.util.ArrayList;
import java.util.Arrays;


public class MultiRuleset<Z, M extends AbstractMessage<Z>> {

    ArrayList<Ruleset<Z, M>> ruleset;

    public boolean isFired(M message) {
        for (Ruleset<Z, M> oneConditionList : ruleset) {
            if (oneConditionList.isFired(message)) {
                AbstractCommand.logger.debug("ONE OF RULESET IS FIRED! GO TO PROCESSING");
                return true;
            }
        }
        return false;
    }

    public MultiRuleset(Ruleset<Z, M>[] ruleset) {
        this.ruleset = new ArrayList<>(Arrays.asList(ruleset));
    }

}
