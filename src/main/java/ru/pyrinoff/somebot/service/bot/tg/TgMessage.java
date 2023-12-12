package ru.pyrinoff.somebot.service.bot.tg;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pyrinoff.somebot.abstraction.AbstractMessage;
import ru.pyrinoff.somebot.api.command.ICommandWithText;

@Getter
public class TgMessage extends AbstractMessage<Update> implements ICommandWithText {

    public TgMessage(Update originalMessage) {
        super(originalMessage);
    }

    public String getText() {
        if(!getOriginalMessage().hasMessage() || getOriginalMessage().getMessage().getText().isEmpty() || getOriginalMessage().getMessage().getText() == null)
            throw new RuntimeException("Cant get text from this update!");
        return getOriginalMessage().getMessage().getText();
    }

    public Long getSenderChatId() {
        return getOriginalMessage().getMessage().getChatId();
    }

    public Integer getMessageTimestamp() {
        return getOriginalMessage().getMessage().getDate();
    }

}
