package ru.pyrinoff.somebot.abstraction;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pyrinoff.somebot.api.command.ICommand;
import ru.pyrinoff.somebot.api.condition.ICondition;
import ru.pyrinoff.somebot.command.condition.MultiRuleset;
import ru.pyrinoff.somebot.command.condition.Ruleset;
import ru.pyrinoff.somebot.service.bot.TelegramBot;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractCommand<M extends AbstractMessage> implements ICommand<M> {

    public static final Logger logger = LoggerFactory.getLogger(AbstractCommand.class);

    private ArrayList<MultiRuleset> fireConditions;

    @Autowired
    @Lazy
    @Getter
    private TelegramBot telegramBot;

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

    public ArrayList<MultiRuleset> getFireConditions() {
        if(fireConditions == null) {
            fireConditions = setupFireConditions();
            if(fireConditions == null) fireConditions = new ArrayList<>(0);
        }
        return fireConditions;
    }

    //SETUP ALIASES START
    public ArrayList<MultiRuleset> fireConditions(MultiRuleset... multiRuleset) {
        return new ArrayList<>(Arrays.asList(multiRuleset));
    }

    public MultiRuleset multiRuleset(Ruleset... ruleset) {
        return new MultiRuleset(ruleset);
    }

    public Ruleset ruleset(ICondition... conditions) {
        return new Ruleset(conditions);
    }
    //SETUP ALIASES END

    public Update getOriginalMessage() {
        return getMessage().getOriginalMessage();
    }

    public @Nullable Long getChatId() {
        return getOriginalMessage().hasMessage() ? getOriginalMessage().getMessage().getChatId() : null;
    }

}
