package ru.pyrinoff.somebotexamples.example2.command;

import org.springframework.stereotype.Component;
import ru.pyrinoff.somebot.abstraction.AbstractCommandSimpleMessage;
import ru.pyrinoff.somebot.command.condition.MultiRuleset;
import ru.pyrinoff.somebot.command.condition.concrete.HasTextEquals;
import ru.pyrinoff.somebotexamples.example2.util.MemoryUsageUtil;

import java.util.ArrayList;

@Component
public class Memory extends AbstractCommandSimpleMessage {

    @Override
    public int getPriority() {
        return CommandPriority.MEMORY.ordinal();
    }

    @Override
    public ArrayList<MultiRuleset> setupFireConditions() {
        return fireConditions(multiRuleset(ruleset(new HasTextEquals("/memory"))));
    }

    @Override
    public void process() {
        telegramBot.sendMessageBack(getMessage().getOriginalMessage(), "Memory usage: " + MemoryUsageUtil.getMemoryUsageMb(), true);
    }

}
