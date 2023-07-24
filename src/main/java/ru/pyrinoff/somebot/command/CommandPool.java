package ru.pyrinoff.somebot.command;

import javassist.bytecode.DuplicateMemberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.command.ICommand;
import ru.pyrinoff.somebot.command.condition.MultiRuleset;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CommandPool<M extends AbstractMessage> {

    private static final Logger logger = LoggerFactory.getLogger(CommandPool.class);

    private List<ICommand<M>> commandList = Collections.emptyList();

    public CommandPool(@Autowired final ICommand<M>[] commands) {
        setCommandList(new ArrayList<>(List.of(commands)));
        logger.info("Initialized with "+ commandList.size()+" commands! " + commandList);
    }

    public CommandPool<M> setCommandList(final List<ICommand<M>> commandList) {
        this.commandList = commandList;
        prepareCommandList();
        return this;
    }

    private void prepareCommandList() {
        commandList = commandList.stream().filter(ICommand::isCommandEnabled).collect(Collectors.toList());
        Collections.sort(commandList, Comparator.comparingInt(ICommand::getPriority));
    }

    public CommandPool<M> addSingleCommand(Class<? extends ICommand<M>> commandClass) throws DuplicateMemberException {
        ICommand<M> command;
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

    public List<ICommand<M>> getFiredCommands(final M message) {
        final List<ICommand<M>> commandsFired = new ArrayList<>();
        for (final ICommand<M> command : commandList) {
            if (isCommandFired(message, command)) {
                commandsFired.add(command);
            }
        }
        logger.debug("Fired commands count: " + commandsFired.size() + ", list: " + commandsFired);
        return commandsFired;
    }

    public boolean isCommandFired(final M message, final ICommand<M> command) {
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