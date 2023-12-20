package com.github.pyrinoff.somebot.command.condition;


import com.github.pyrinoff.somebot.abstraction.AbstractCommand;
import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.api.condition.IConcreteCondition;
import com.github.pyrinoff.somebot.api.condition.ICanBeFired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Ruleset<Z, M extends AbstractMessage<Z>> implements ICanBeFired<Z, M> {

    ArrayList<IConcreteCondition<Z, M>> conditions;

    public boolean isFired(M message) {
        for (IConcreteCondition<Z, M> oneCondition : conditions) {
            AbstractCommand.logger.debug("Check condition: " + oneCondition);
            AbstractCommand.logger.debug("Condition class: " + oneCondition.getMessageClass().getName() + ", message class: " + message.getOriginalMessage().getClass());
            if (!oneCondition.getMessageClass().getName().equals("java.lang.Object")
                    && message.getOriginalMessage().getClass() != oneCondition.getMessageClass()) { //для предотвращения ошибки класскаста
                AbstractCommand.logger.debug("Original message class not matched (0): "
                        + message.getOriginalMessage().getClass().getName() + " "
                        + oneCondition.getMessageClass().getName());
                return false;
            }
            if (!oneCondition.isFired(message)) {
                AbstractCommand.logger.debug("CONDITION FAILED! BREAK");
                return false;
            } else AbstractCommand.logger.debug("CONDITION OK! BUT NEED TO CHECK NEXT");
        }
        AbstractCommand.logger.debug("ALL CONDITIONS IN RULESET FIRED! GO!");
        return true;
    }

    public Ruleset<Z, M> addCondition(IConcreteCondition<Z, M> condition) {
        conditions.add(condition);
        return this;
    }

    public Ruleset(IConcreteCondition<Z, M>[] conditions) {
        this.conditions = new ArrayList<>(Arrays.asList(conditions));
    }

    public Ruleset(IConcreteCondition<Z, M> condition) {
        this.conditions = new ArrayList<>(Collections.singletonList(condition));
    }

}
