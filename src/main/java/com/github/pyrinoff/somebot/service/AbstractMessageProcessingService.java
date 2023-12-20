package com.github.pyrinoff.somebot.service;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.api.command.ICommand;
import com.github.pyrinoff.somebot.api.command.ICommandWithTimestampAndChatId;
import com.github.pyrinoff.somebot.api.service.IAntiFloodService;
import com.github.pyrinoff.somebot.command.CommandPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.pyrinoff.somebot.api.service.IMessageProcessingService;

import java.util.List;

@Component
public abstract class AbstractMessageProcessingService<Z, M extends AbstractMessage<Z>>
        implements IMessageProcessingService<Z, M> {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractMessageProcessingService.class);

    @Autowired
    protected CommandPool<Z, M> commandPool;

    @Autowired
    protected IAntiFloodService antiFloodService;

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
        List<ICommand<Z, M>> firedCommands = commandPool.getFiredCommands(message);
        logger.debug("Processing command count: " + firedCommands.size() + ", list: " + firedCommands);
        for (final ICommand<Z, M> oneCommand : firedCommands) {
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

    abstract protected void preprocessMessage(final M message);

    abstract protected M convertUpdateToMessage(final Z update);

    abstract protected void postProcessMessage(M message);

    protected void onFloodMessage(M message) {

    }

}
