package ru.pyrinoff.somebotexamples.example.model;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pyrinoff.somebot.abstraction.AbstractMessage;

@Getter
public class CustomMessage extends AbstractMessage {

    public CustomMessage(@NotNull Update originalMessage) {
        super(originalMessage);
    }

    @Nullable User user;

    @Setter
    boolean firstMessageFromUser = false;

    public CustomMessage setUser(User user) {
        this.user = user;
        return this;
    }

    public boolean hasUser() {
        return user != null;
    }

}
