package ru.pyrinoff.somebot.abstraction;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public abstract class AbstractMessage {

    @NotNull Update originalMessage;

    public AbstractMessage(@NotNull Update originalMessage) {
        this.originalMessage = originalMessage;
    }

}
