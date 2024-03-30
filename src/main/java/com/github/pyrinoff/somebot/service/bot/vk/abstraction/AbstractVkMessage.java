package com.github.pyrinoff.somebot.service.bot.vk.abstraction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.api.command.ICommandWithPayload;
import com.github.pyrinoff.somebot.api.command.ICommandWithText;
import com.github.pyrinoff.somebot.exception.model.NonFatalException;
import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.concrete.VkPayload;
import com.vk.api.sdk.objects.callback.MessageObject;
import com.vk.api.sdk.objects.messages.Message;
import lombok.Getter;

@Getter
public class AbstractVkMessage<U extends User> extends AbstractMessage<MessageObject, U> implements
        ICommandWithText, ICommandWithPayload {

    private VkPayload payloadVk;
    private String payload;

    @Override
    public String getPayload() {
        return payload;
    }

    @Override
    public VkPayload getPayloadVk() {
        return payloadVk;
    }

    public AbstractVkMessage(MessageObject originalMessage) {
        super(originalMessage);
        //сразу парсим в переменные, т.к. будет использоваться часто в проверках, каждый раз парсить нет смысла
        payloadVk = getPayloadVk(originalMessage.getMessage());
        payload = getStringPayload(originalMessage.getMessage());
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
        if (getOriginalMessage() == null) return null;
        return getOriginalMessage().getMessage().getDate();
    }

    private static String getStringPayload(Message message) {
        if (message == null || message.getPayload() == null || message.getPayload().isEmpty())
            return null; //throw new RuntimeException("Cant get payload from this vk message!");
        try {
            return AbstractCommandVkMessage.readPayload(message.getPayload());
        } catch (JsonProcessingException e) {
            //VkBotHandler.logger.error("Cant deserialize payload as string: " + message.getPayload());
            //e.printStackTrace();
            return null;
        }
    }

    private static VkPayload getPayloadVk(Message message) {
        if (message == null || message.getPayload() == null || message.getPayload().isEmpty()
        ) return null; //throw new RuntimeException("Cant get payload from this vk message!");
        try {
            return VkPayload.fromPayload(message.getPayload());
        } catch (JsonProcessingException e) {
            //VkBotHandler.logger.error("Cant deserialize payload as VkPayload: " + message.getPayload());
            //e.printStackTrace();
            return null;
        }
    }

}
