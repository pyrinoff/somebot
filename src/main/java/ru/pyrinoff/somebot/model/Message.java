package ru.pyrinoff.somebot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@AllArgsConstructor
public class Message {

    @NotNull Update originalMessage;

    @Nullable AbstractUser user;

    @Setter
    boolean firstMessageFromUser = false;

    public Message(@NotNull Update originalMessage) {
        this.originalMessage = originalMessage;
    }

    public Message setUser(AbstractUser user) {
        this.user = user;
        return this;
    }

    public boolean hasUser() {
        return user != null;
    }

    public <T extends User> T getUserCast(Class<T> clazz) {
        return clazz.cast(user);
    }

}
