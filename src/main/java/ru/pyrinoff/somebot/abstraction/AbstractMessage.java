package ru.pyrinoff.somebot.abstraction;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pyrinoff.somebot.util.StringUtil;

@Getter
public abstract class AbstractMessage {

    @NotNull Update originalMessage;

    public AbstractMessage(@NotNull Update originalMessage) {
        this.originalMessage = originalMessage;
    }

    public String getText() {
        if(!getOriginalMessage().hasMessage() || getOriginalMessage().getMessage().getText().isEmpty() || getOriginalMessage().getMessage().getText() == null)
            throw new RuntimeException("Cant get text from this update!");
        return getOriginalMessage().getMessage().getText();
    }

    public String getArg(int index) {
        return StringUtil.getArgString(getText(), index, " ", 1024, false);
    }

    public Integer getArgInt(int index) {
        final String arg = getArg(index);
        return arg == null ? null : Integer.parseInt(arg);
    }

    public Long getSenderChatId() {
        return getOriginalMessage().getMessage().getChatId();
    }

    public Integer getMessageTimestamp() {
        return getOriginalMessage().getMessage().getDate();
    }

}
