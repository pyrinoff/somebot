package com.github.pyrinoff.somebot.abstraction;

import com.github.pyrinoff.somebot.api.command.ICommand;
import com.github.pyrinoff.somebot.api.condition.IConcreteCondition;
import com.github.pyrinoff.somebot.command.condition.MultiRuleset;
import com.github.pyrinoff.somebot.command.condition.Ruleset;
import com.github.pyrinoff.somebot.exception.model.UserNullException;
import com.github.pyrinoff.somebot.model.User;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractCommand<Z, U extends User, M extends AbstractMessage<Z, U>> implements ICommand<Z, U, M> {

    public static final Logger logger = LoggerFactory.getLogger(AbstractCommand.class);

    private ArrayList<MultiRuleset<Z, U, M>> fireConditions;

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

    public ArrayList<MultiRuleset<Z, U, M>> getFireConditions() {
        if (fireConditions == null) {
            fireConditions = setupFireConditions();
            if (fireConditions == null) fireConditions = new ArrayList<>(0);
        }
        return fireConditions;
    }

    public U getUser() {
        return getMessage().getUser();
    }

    public Integer getStage() {
        if(getMessage().getUser() == null) throw new UserNullException();
        return getMessage().getUser().getStage();
    }

    public Integer isStage(int stage) {
        if(getMessage().getUser() == null) throw new UserNullException();
        return getMessage().getUser().getStage();
    }

    public void setStage(int stage) {
        if(getMessage().getUser() == null) throw new UserNullException();
        getMessage().getUser().setStage(stage);
    }

    public void setDefaultStage() {
        getUser().setDefaultStage();
    }

    public boolean isDefaultStage() {
        if(getMessage().getUser() == null) throw new UserNullException();
        return getUser().isDefaultStage();
    }

    //SETUP ALIASES START
    public ArrayList<MultiRuleset<Z, U, M>> fireConditions(MultiRuleset<Z, U, M>... multiRuleset) {
        return new ArrayList<>(Arrays.asList(multiRuleset));
    }

    public MultiRuleset<Z, U, M> multiRuleset(Ruleset<Z, U, M>... ruleset) {
        return new MultiRuleset<>(ruleset);
    }

    public Ruleset<Z, U, M> ruleset(IConcreteCondition<Z, U, M>... conditions) {
        return new Ruleset<>(conditions);
    }

}
