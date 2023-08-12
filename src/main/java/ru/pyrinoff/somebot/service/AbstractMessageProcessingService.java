package ru.pyrinoff.somebot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.command.ICommand;
import ru.pyrinoff.somebot.api.service.IAntiFloodService;
import ru.pyrinoff.somebot.api.service.IMessageProcessingService;
import ru.pyrinoff.somebot.command.CommandPool;

import java.util.List;

@Component
public abstract class AbstractMessageProcessingService<M extends AbstractMessage>
        implements IMessageProcessingService {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractMessageProcessingService.class);

    @Autowired
    protected CommandPool<M> commandPool;

    @Autowired
    protected IAntiFloodService antiFloodService;

    @Override
    public void processUpdate(final Update update) {
        M message = formMessageByUpdate(update);
        if(antiFloodService.isFloodMessage(message)) {
            onFloodMessage(message);
            return;
        }
        preprocessMessage(message);
        processMessage(message);
        postProcessMessage(message);
    }

    protected void processMessage(final M message) {
        List<ICommand<M>> firedCommands = commandPool.getFiredCommands(message);
        logger.debug("Processing command count: " + firedCommands.size() + ", list: " + firedCommands);
        for (final ICommand<M> oneCommand : firedCommands) {
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

    abstract protected M formMessageByUpdate(final Update update);

    abstract protected void preprocessMessage(final M message) ;

    abstract protected void postProcessMessage(M message);

    protected void onFloodMessage(M message) {

    }

}
