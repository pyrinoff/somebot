package com.github.pyrinoff.somebot.command.condition;


import com.github.pyrinoff.somebot.abstraction.AbstractCommand;
import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.model.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;


public class MultiRuleset<Z, U extends User, M extends AbstractMessage<Z, U>> {

    @Getter
    int priority;

    ArrayList<Ruleset<Z, U, M>> ruleset;

    public boolean isFired(M message) {
        for (Ruleset<Z, U, M> oneConditionList : ruleset) {
            if (oneConditionList.isFired(message)) {
                AbstractCommand.logger.debug("ONE OF RULESET IS FIRED! GO TO PROCESSING");
                return true;
            }
        }
        return false;
    }

    public MultiRuleset(int priority, Ruleset<Z, U, M>[] ruleset) {
        this.ruleset = new ArrayList<>(Arrays.asList(ruleset));
        this.priority = priority;
    }

}
