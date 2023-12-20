package com.github.pyrinoff.somebot.abstraction;

import com.github.pyrinoff.somebot.api.condition.IConcreteCondition;
import com.github.pyrinoff.somebot.command.condition.MultiRuleset;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pyrinoff.somebot.api.command.ICommand;
import com.github.pyrinoff.somebot.command.condition.Ruleset;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractCommand<Z, M extends AbstractMessage<Z>> implements ICommand<Z, M> {

    public static final Logger logger = LoggerFactory.getLogger(AbstractCommand.class);

    private ArrayList<MultiRuleset<Z, M>> fireConditions;

    @Getter
    @Setter
    private boolean proceedNextCommand = false;

    @Getter
    @Setter
    private boolean processNewCircle = false;

    @Getter
    @Setter
    private boolean commandEnabled = true;

    @Getter
    @Setter
    private M message;

    public ArrayList<MultiRuleset<Z, M>> getFireConditions() {
        if(fireConditions == null) {
            fireConditions = setupFireConditions();
            if(fireConditions == null) fireConditions = new ArrayList<>(0);
        }
        return fireConditions;
    }

    //SETUP ALIASES START
    public ArrayList<MultiRuleset<Z, M>> fireConditions(MultiRuleset<Z, M>... multiRuleset) {
        return new ArrayList<>(Arrays.asList(multiRuleset));
    }

    public MultiRuleset<Z, M> multiRuleset(Ruleset<Z, M>... ruleset) {
        return new MultiRuleset<Z, M>(ruleset);
    }

    public Ruleset<Z, M> ruleset(IConcreteCondition<Z, M>... conditions) {
        return new Ruleset<Z, M>(conditions);
    }

/*    public Ruleset<Z, M> ruleset(IConcreteCondition<Z, M> condition) {
        return new Ruleset<Z, M>(condition);
    }*/
    //SETUP ALIASES END

    public Z getOriginalMessage() {
        return getMessage().getOriginalMessage();
    }

/*
    public @Nullable Long getChatId() {
        return getOriginalMessage().hasMessage() ? getOriginalMessage().getMessage().getChatId() : null;
    }
*/

}
