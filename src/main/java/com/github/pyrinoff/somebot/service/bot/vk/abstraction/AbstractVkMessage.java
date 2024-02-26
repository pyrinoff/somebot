package com.github.pyrinoff.somebot.service.bot.vk.abstraction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.api.command.ICommandWithPayload;
import com.github.pyrinoff.somebot.api.command.ICommandWithText;
import com.github.pyrinoff.somebot.exception.model.NonFatalException;
import com.github.pyrinoff.somebot.model.User;
import com.vk.api.sdk.objects.callback.MessageObject;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class AbstractVkMessage<U extends User> extends AbstractMessage<MessageObject, U> implements
        ICommandWithText, ICommandWithPayload {

    public AbstractVkMessage(MessageObject originalMessage) {
        super(originalMessage);
    }

    public String getText() {
        if (getOriginalMessage().getMessage() == null
                || getOriginalMessage().getMessage().getText() == null
                || getOriginalMessage().getMessage().getText().isEmpty()
        ) throw new NonFatalException("Cant get text from this vk message!");
        return getOriginalMessage().getMessage().getText();
    }

    public Long getSenderChatId() {
        return getOriginalMessage().getMessage().getPeerId().longValue();
    }

    public Integer getMessageTimestamp() {
        if (getOriginalMessage().getMessage() == null) return null;
        return getOriginalMessage().getMessage().getDate();
    }

    @SneakyThrows
    @Override
    public String getPayload() {
        if (getOriginalMessage().getMessage() == null
                || getOriginalMessage().getMessage().getPayload() == null
                || getOriginalMessage().getMessage().getPayload().isEmpty()
        ) return null; //throw new RuntimeException("Cant get payload from this vk message!");
        //System.out.println("Payload: " + getOriginalMessage().getMessage().getPayload());
        try {
            return new ObjectMapper().readValue(getOriginalMessage().getMessage().getPayload(), String.class);
            //return new ObjectMapper().readValue(getOriginalMessage().getMessage().getPayload(), Object.class);
        } catch (MismatchedInputException e) {
            return getOriginalMessage().getMessage().getPayload();
        }
    }

}
