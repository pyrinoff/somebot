package com.github.pyrinoff.somebot.service;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.api.command.ICommand;
import com.github.pyrinoff.somebot.api.command.ICommandWithTimestampAndChatId;
import com.github.pyrinoff.somebot.api.service.IAntiFloodService;
import com.github.pyrinoff.somebot.api.service.IMessageProcessingService;
import com.github.pyrinoff.somebot.command.CommandPool;
import com.github.pyrinoff.somebot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class AbstractMessageProcessingService<Z, U extends User, M extends AbstractMessage<Z, U>>
        implements IMessageProcessingService<Z> {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractMessageProcessingService.class);

    @Autowired
    protected CommandPool<Z, U, M> commandPool;

    @Autowired
    protected IAntiFloodService antiFloodService;

    @Autowired
    private IUserService<U> userService;

    @Override
    public void processUpdate(final Z update) {
        messageProcessing(convertUpdateToMessage(update));
    }

    public void messageProcessing(final M message) {
        if (antiFloodService.isFloodMessage((ICommandWithTimestampAndChatId) message)) {
            onFloodMessage(message);
            return;
        }
        preprocessMessage(message);
        processMessage(message);
        postProcessMessage(message);
    }

    protected void processMessage(final M message) {
        List<ICommand<Z, U, M>> firedCommands = commandPool.getFiredCommands(message);
        logger.debug("Processing command count: " + firedCommands.size() + ", list: " + firedCommands);
        for (final ICommand<Z, U, M> oneCommand : firedCommands) {
            logger.debug("Processing command: " + oneCommand.getClass().getName());
            try {
                oneCommand.setMessage(message);
                oneCommand.process();
            } catch (Exception e) {
                logger.info("Error during processing command " + oneCommand.getClass().getName());
                e.printStackTrace();
                break;
            }
            if (oneCommand.isProcessNewCircle()) {
                processMessage(message);
                break;
            }
            if (!oneCommand.isProceedNextCommand()) break;
        }
    }

//    abstract protected void preprocessMessage(final M message);

    abstract protected M convertUpdateToMessage(final Z update);

//    abstract protected void postProcessMessage(M message);

    //TODO use it
    protected void onFloodMessage(M message) {

    }

    public void preprocessMessage(final M message) {
        if(message.getOriginalMessage() == null) return;
        if(message.getSenderChatId() == null) return;
        Long chatId = message.getSenderChatId();
        U user = userService.getUser(chatId);
        if (user == null) {
            user = userService.createUser(chatId);
            if (user == null) {
                logger.error("Cant registerUser!");
                return;
            }
            message.setFirstMessageFromUser(true);
        }
        message.setUser(user);
    }

    protected void postProcessMessage(M message) {
        if(message.hasUser()) userService.saveUser(message.getUser());
    }

}
