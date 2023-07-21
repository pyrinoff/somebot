package ru.pyrinoff.somebot.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@MappedSuperclass
@Table(name = "users", schema = "public")
@Accessors(chain = true)
public abstract class AbstractUser {

    private static final long serialVersionUID = 1;

    @Id
    @Column(name = "chat_id")
    @NotNull
    protected Long chatId;

    @Column
    @NotNull
    protected Boolean registered = false;

    @Column
    @NotNull
    protected Boolean locked = false;

    @Column
    protected int stage = 0;

    @Column
    @Nullable
    @Lob
    protected byte[] customData = null;

    private static final int STAGE_NONE = 0;

    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", registered=" + registered +
                ", locked=" + locked +
                '}';
    }

    public void setDefaultStage() {
        setStage(STAGE_NONE);
    }

}
