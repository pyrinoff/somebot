package ru.pyrinoff.somebotexamples.example2.command;

import org.springframework.stereotype.Component;
import ru.pyrinoff.somebot.abstraction.AbstractCommandSimpleMessage;
import ru.pyrinoff.somebot.command.condition.MultiRuleset;
import ru.pyrinoff.somebot.command.condition.concrete.HasTextEquals;

import java.util.ArrayList;

@Component
public class Whois extends AbstractCommandSimpleMessage {

    @Override
    public int getPriority() {
        return CommandPriority.WHOIS.ordinal();
    }

    @Override
    public ArrayList<MultiRuleset> setupFireConditions() {
        return fireConditions(multiRuleset(ruleset(new HasTextEquals("/whois"))));
    }

    @Override
    public void process() {
        getTelegramBot().sendMessageBack(getOriginalMessage(),
                "Your chat id is: " + getOriginalMessage().getMessage().getChatId() + "\n"
                        + "You user id: " + getOriginalMessage().getMessage().getFrom().getId() + "\n"
                        + "Your username: '" + getOriginalMessage().getMessage().getFrom().getUserName() + "'\n"
                        + "Your name: '" + getOriginalMessage().getMessage().getFrom().getFirstName() + " "
                        + getOriginalMessage().getMessage().getFrom().getLastName() + "'\n"
                , true
        );
    }

}
