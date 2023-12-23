package com.github.pyrinoff.somebot.abstraction;

import com.github.pyrinoff.somebot.api.command.ICommandWithTimestampAndChatId;
import com.github.pyrinoff.somebot.model.User;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
public abstract class AbstractMessage<M, U extends User> implements ICommandWithTimestampAndChatId {

    private final M originalMessage;

    @Nullable U user;

    @Setter
    boolean firstMessageFromUser = false;

    public AbstractMessage(M originalMessage) {
        this.originalMessage = originalMessage;
    }

    public M getOriginalMessage() {
        return originalMessage;
    }

    public AbstractMessage<M, U> setUser(U user) {
        this.user = user;
        return this;
    }

    public boolean hasUser() {
        return user != null;
    }

}
