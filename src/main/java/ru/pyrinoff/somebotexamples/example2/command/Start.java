package ru.pyrinoff.somebotexamples.example2.command;

import org.springframework.stereotype.Component;
import ru.pyrinoff.somebot.abstraction.AbstractCommandSimpleMessage;
import ru.pyrinoff.somebot.command.condition.MultiRuleset;
import ru.pyrinoff.somebot.command.condition.concrete.HasAnyText;
import ru.pyrinoff.somebot.command.condition.concrete.HasTextEquals;

import java.util.ArrayList;

@Component
public class Start extends AbstractCommandSimpleMessage {

    @Override
    public int getPriority() {
        return CommandPriority.EXAMPLE.ordinal();
    }

    @Override
    public ArrayList<MultiRuleset> setupFireConditions() {
        return fireConditions(multiRuleset(ruleset(new HasAnyText(true), new HasTextEquals("/start"))));
    }

    @Override
    public void process() {
        getTelegramBot().sendMessageBack(getOriginalMessage(), "Start message answer", true);
    }

}
