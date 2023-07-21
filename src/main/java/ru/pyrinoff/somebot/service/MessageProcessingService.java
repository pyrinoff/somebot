package ru.pyrinoff.somebot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.model.SimpleMessage;

@Component
public class MessageProcessingService extends AbstractMessageProcessingService<AbstractMessage> {

    protected AbstractMessage formMessageByUpdate(final Update update) {
        return new SimpleMessage(update);
    }

    protected void preprocessMessage(final AbstractMessage message) {
    }

    protected void postProcessMessage(AbstractMessage message) {
    }

}
