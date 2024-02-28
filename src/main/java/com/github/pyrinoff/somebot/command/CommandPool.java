package com.github.pyrinoff.somebot.command;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.api.command.ICommand;
import com.github.pyrinoff.somebot.command.condition.MultiRuleset;
import com.github.pyrinoff.somebot.model.User;
import javassist.bytecode.DuplicateMemberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandPool<Z, U extends User, M extends AbstractMessage<Z, U>> {

    public static final Logger logger = LoggerFactory.getLogger(CommandPool.class);

    private List<ICommand<Z, U, M>> commandList = Collections.emptyList();

    public CommandPool(@Autowired final ICommand<Z, U, M>[] commands) {
        setCommandList(new ArrayList<>(List.of(commands)));
        logger.info("Initialized with " + commandList.size() + " commands! " + commandList);
    }

    public CommandPool<Z, U, M> setCommandList(final List<ICommand<Z, U, M>> commandList) {
        this.commandList = commandList;
        prepareCommandList();
        return this;
    }

    private void prepareCommandList() {
        commandList = commandList.stream().filter(ICommand::isCommandEnabled).collect(Collectors.toList());
    }

    private void sortCommandList(List<ICommand<Z, U, M>> commandList) {
        commandList.sort(Comparator.comparingInt(ICommand::getPriority));
    }

    public CommandPool<Z, U, M> addSingleCommand(Class<? extends ICommand<Z, U, M>> commandClass) throws DuplicateMemberException {
        ICommand<Z, U, M> command;
        try {
            command = commandClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            logger.info("Can't add command with class: " + commandClass, e);
            throw new DuplicateMemberException("Error adding command: " + e.getMessage());
        }
        commandList.add(command);
        prepareCommandList();
        logger.info("Another command added. Full size of commands: " + commandList.size());
        return this;
    }

    public List<ICommand<Z, U, M>> getFiredCommands(final M message) {
        final List<ICommand<Z, U, M>> commandsFired = new ArrayList<>();
        for (final ICommand<Z, U, M> command : commandList) {
            if (isCommandFired(message, command)) {
                commandsFired.add(command);
            }
        }
        sortCommandList(commandsFired);
        logger.debug("Fired commands count: " + commandsFired.size() + ", list: " + commandsFired);
        for (ICommand<Z, U, M> zumiCommand : commandsFired) {
        }
        return commandsFired;
    }

    public boolean isCommandFired(final M message, final ICommand<Z, U, M> command) {
        logger.debug("Check fire condition of command: " + command.getClass());
        for (MultiRuleset<Z, U, M> oneRuleset : command.getFireConditions()) {
            logger.debug("Checking MultiRuleset: " + oneRuleset.getClass().getName());
            if (oneRuleset.isFired(message)) {
                logger.debug("Command should be fired: " + command.getClass());
                command.setPriority(oneRuleset.getPriority());
                return true;
            }
        }
        logger.debug("Command should NOT be fired: " + command.getClass());
        return false;
    }

}