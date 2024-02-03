package com.github.pyrinoff.somebot.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Date;

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

    @Column (name = "created_date", nullable = false, updatable = false, columnDefinition = "datetime DEFAULT '1970-01-01 00:00:00'")
    @NotNull
    @CreatedDate //not working
    protected Date createdDate = new Date();

    //@Column (name = "modified_date", , columnDefinition = "datetime DEFAULT '1970-01-01 00:00:00'")
    //@Nullable
    //@LastModifiedDate
    //protected Date modifiedDate;

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