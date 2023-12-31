package com.github.pyrinoff.somebot.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Entity
@Getter
@Setter
//@MappedSuperclass
@Table(name = "users", schema = "public")
@Accessors(chain = true)
@NoArgsConstructor
public class User {

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
    protected int stage = STAGE_NONE;

    protected LocalDate birthDate;

    @Column
    @Lob
    protected byte[] customData = null;

    public static final int STAGE_NONE = 0;

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

    public boolean isDefaultStage() {
        return getStage() == STAGE_NONE;
    }

}