package ru.pyrinoff.somebot.command;

import javassist.bytecode.DuplicateMemberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pyrinoff.somebot.api.command.ICommand;
import ru.pyrinoff.somebot.command.condition.MultiRuleset;
import ru.pyrinoff.somebot.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandPool {

    private static final Logger logger = LoggerFactory.getLogger(CommandPool.class);

    private List<ICommand> commandList = Collections.emptyList();

    public CommandPool(@Autowired final ICommand[] commands) {
        setCommandList(new ArrayList<>(List.of(commands)));
        logger.info("Initialized with "+ commandList.size()+" commands! " + commandList);
    }

    public CommandPool setCommandList(final List<ICommand> commandList) {
        this.commandList = commandList;
        prepareCommandList();
        return this;
    }

    private void prepareCommandList() {
        commandList = commandList.stream().filter(ICommand::isCommandEnabled).collect(Collectors.toList());
        Collections.sort(commandList, Comparator.comparingInt(ICommand::getPriority));
    }

    public CommandPool addSingleCommand(Class<? extends ICommand> commandClass) throws DuplicateMemberException {
        ICommand command;
        try {
            command = commandClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            logger.info("Can't add command with class: " + commandClass, e);
            throw new DuplicateMemberException("Error adding command: " + e.getMessage());
        }
        commandList.add(command);
        prepareCommandList();
        logger.info("Another command added. Full size of commands: "+ commandList.size());
        return this;
    }

    public List<ICommand> getFiredCommands(final Message message) {
        final List<ICommand> commandsFired = new ArrayList<>();
        for (final ICommand command : commandList) {
            if (isCommandFired(message, command)) {
                commandsFired.add(command);
            }
        }
        logger.debug("Fired commands count: " + commandsFired.size());
        return commandsFired;
    }

    public boolean isCommandFired(final Message message, final ICommand command) {
        logger.debug("Check fire condition of command: " + command.getClass());
        for (MultiRuleset oneRuleset : command.getFireConditions()) {
            logger.debug("Checking MultiRuleset: " + oneRuleset.getClass().getName());
            if (oneRuleset.isFired(message)) {
                logger.debug("Command should be fired: " + command.getClass());
                return true;
            }
        }
        logger.debug("Command should NOT be fired: " + command.getClass());
        return false;
    }

}