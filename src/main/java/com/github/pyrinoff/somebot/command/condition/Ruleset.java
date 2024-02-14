package com.github.pyrinoff.somebot.command.condition;


import com.github.pyrinoff.somebot.abstraction.AbstractCommand;
import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.api.condition.ICanBeFired;
import com.github.pyrinoff.somebot.api.condition.IConcreteCondition;
import com.github.pyrinoff.somebot.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Ruleset<Z, U extends User, M extends AbstractMessage<Z, U>> implements ICanBeFired<Z, U, M> {

    ArrayList<IConcreteCondition<Z, U, M>> conditions;

    public boolean isFired(M message) {
        for (IConcreteCondition<Z, U, M> oneCondition : conditions) {
            AbstractCommand.logger.debug("Check condition: " + oneCondition);
            AbstractCommand.logger.debug("Condition class: " + oneCondition.getMessageClass().getName() + ", message class: " + message.getOriginalMessage().getClass());
            if (!oneCondition.getMessageClass().getName().equals("java.lang.Object")
                    && message.getOriginalMessage().getClass() != oneCondition.getMessageClass()) { //для предотвращения ошибки класскаста
                AbstractCommand.logger.debug("Original message class not matched (0): "
                        + message.getOriginalMessage().getClass().getName() + " "
                        + oneCondition.getMessageClass().getName());
                return false;
            }
            try {
                if (!oneCondition.isFired(message)) {
                    AbstractCommand.logger.debug("CONDITION FAILED! BREAK");
                    return false;
                } else AbstractCommand.logger.debug("CONDITION OK! BUT NEED TO CHECK NEXT");
            } catch (RuntimeException e) {
                AbstractCommand.logger.error("Exception during process this Ruleset " +
                        "("+ this.getClass().getName()  +")" +
                        ": " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        AbstractCommand.logger.debug("ALL CONDITIONS IN RULESET FIRED! GO!");
        return true;
    }

    public Ruleset<Z, U, M> addCondition(IConcreteCondition<Z, U, M> condition) {
        conditions.add(condition);
        return this;
    }

    public Ruleset(IConcreteCondition<Z, U, M>[] conditions) {
        this.conditions = new ArrayList<>(Arrays.asList(conditions));
    }

    public Ruleset(IConcreteCondition<Z, U, M> condition) {
        this.conditions = new ArrayList<>(Collections.singletonList(condition));
    }

}
