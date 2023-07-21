package ru.pyrinoff.somebot.model;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pyrinoff.somebot.abstraction.AbstractMessage;

@Getter
public class SimpleMessage extends AbstractMessage {

    public SimpleMessage(@NotNull Update originalMessage) {
        super(originalMessage);
    }

}
