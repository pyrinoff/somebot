package com.github.pyrinoff.somebot.service.bot.vk;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.api.command.ICommandWithText;
import com.vk.api.sdk.objects.callback.MessageObject;
import lombok.Getter;

@Getter
public class VkMessage extends AbstractMessage<MessageObject> implements ICommandWithText {

    public VkMessage(MessageObject originalMessage) {
        super(originalMessage);
    }

    public String getText() {
        if (getOriginalMessage().getMessage() == null
                || getOriginalMessage().getMessage().getText() == null
                || getOriginalMessage().getMessage().getText().isEmpty()
        ) throw new RuntimeException("Cant get text from this update!");
        return getOriginalMessage().getMessage().getText();
    }

    public Long getSenderChatId() {
        return getOriginalMessage().getMessage().getPeerId().longValue();
    }

    public Integer getMessageTimestamp() {
        if (getOriginalMessage().getMessage() == null) return null;
        return getOriginalMessage().getMessage().getDate();
    }

}
