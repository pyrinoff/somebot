package com.github.pyrinoff.somebot.service.bot.tg.abstraction;

import com.github.pyrinoff.somebot.abstraction.AbstractMessage;
import com.github.pyrinoff.somebot.api.command.ICommandWithText;
import com.github.pyrinoff.somebot.model.User;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public abstract class AbstractTgMessage<U extends User> extends AbstractMessage<Update, U> implements ICommandWithText {

    public AbstractTgMessage(Update originalMessage) {
        super(originalMessage);
    }

    public String getText() {
        if(!getOriginalMessage().hasMessage()
                || getOriginalMessage().getMessage().getText().isEmpty()
                || getOriginalMessage().getMessage().getText() == null)
            throw new RuntimeException("Cant get text from this update!");
        return getOriginalMessage().getMessage().getText();
    }

    @Nullable
    public Long getSenderChatId() {
        if(getOriginalMessage().getMessage() == null) return null;
        return getOriginalMessage().getMessage().getChatId();
    }

    @Nullable
    public Integer getMessageTimestamp() {
        if(getOriginalMessage().getMessage() == null) return null;
        return getOriginalMessage().getMessage().getDate();
    }

}
