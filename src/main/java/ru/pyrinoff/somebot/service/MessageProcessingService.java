package ru.pyrinoff.somebot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pyrinoff.somebot.api.command.ICommand;
import ru.pyrinoff.somebot.api.service.IMessageProcessingService;
import ru.pyrinoff.somebot.api.service.IUserService;
import ru.pyrinoff.somebot.command.CommandPool;
import ru.pyrinoff.somebot.model.Message;

import java.util.List;

@Component
@Primary
public class MessageProcessingService implements IMessageProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessingService.class);

    @Autowired
    private CommandPool commandPool;

    @Autowired
    private IUserService userService;

    @Override
    public void process(final Update update) {
        Message message = formMessage(update);
        process(message);
    }

    public void process(final Message message) {
        logger.info("Process message!");
        logger.info("Current stage in message: " +  message.getUser().getStage());

        List<ICommand> firedCommands = commandPool.getFiredCommands(message);
        for (final ICommand oneCommand : firedCommands) {
            try {
                logger.info("Processing command: " + oneCommand.getClass());
                oneCommand.setMessage(message);
                oneCommand.process();
            } catch (Exception e) {
                logger.info("Error during processing command " + oneCommand.getClass().getName());
                e.printStackTrace();
                break;
            }
            if (oneCommand.isProcessNewCircle()) {
                process(message);
                break;
            }
            if (!oneCommand.isProceedNextCommand()) break;
        }
        if (message.hasUser()) userService.saveUser(message.getUser());
    }

    private Message formMessage(final Update update) {
        Message message = new Message(update);
        userService.updateUserInMessage(message);
        return message;
    }

}
