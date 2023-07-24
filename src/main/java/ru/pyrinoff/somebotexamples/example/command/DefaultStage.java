package ru.pyrinoff.somebotexamples.example.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pyrinoff.somebot.command.condition.MultiRuleset;
import ru.pyrinoff.somebot.command.condition.concrete.HasMessage;
import ru.pyrinoff.somebotexamples.example.abstracts.CustomCommand;
import ru.pyrinoff.somebotexamples.example.api.service.IUserService;
import ru.pyrinoff.somebotexamples.example.condition.HasStage;

import java.util.ArrayList;

import static ru.pyrinoff.somebotexamples.example.model.User.STAGE_NONE;

@Component
public class DefaultStage extends CustomCommand {

    public static final String TEXT_SHOW_YOUR_BIRTHDATE = "Ваша дата рождения: ";

    @Autowired IUserService userService;

    @Override
    public int getPriority() {
        return CommandPriority.DEFAULT_STAGE.ordinal();
    }

    @Override
    public ArrayList<MultiRuleset> setupFireConditions() {
        return fireConditions(multiRuleset(ruleset(new HasMessage(), new HasStage(STAGE_NONE))));
    }

    @Override
    public void process() {
        getTelegramBot().sendMessageBack(getOriginalMessage(), TEXT_SHOW_YOUR_BIRTHDATE + getUser().getBirthDate(), true);
        getUser().setDefaultStage();
    }

}
