package com.github.pyrinoff.somebot.model;


import com.github.pyrinoff.somebot.exception.model.CantBeNullException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedDate;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

@Entity
@Getter
@Setter
//@MappedSuperclass
@Table(name = "users", schema = "public")
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    protected static final Logger logger = LoggerFactory.getLogger(User.class);

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

    //@Lob
    @Column(name = "custom_data", length = 65536)
    protected byte[] customData = null;

    @Transient
    protected Properties buffer;

    public static final int STAGE_NONE = 0;

    @Column (name = "created_date", nullable = false, updatable = false, columnDefinition = "datetime DEFAULT '1970-01-01 00:00:00'")
    @NotNull
    @CreatedDate //not working
    protected Date createdDate = new Date();

    //@Column (name = "modified_date", , columnDefinition = "datetime DEFAULT '1970-01-01 00:00:00'")
    //@Nullable
    //@LastModifiedDate
    //protected Date modifiedDate;

    @Column(name = "balance", nullable = false)
    @NotNull
    protected Double balance = 0.0;

    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", registered=" + registered +
                ", locked=" + locked +
                ", balance=" + balance +
                '}';
    }

    public void setDefaultStage() {
        setStage(STAGE_NONE);
    }

    public boolean isDefaultStage() {
        return getStage() == STAGE_NONE;
    }

    protected void deserializeBuffer() {
        if (customData != null) {
            try {
                buffer = (Properties) (new ObjectInputStream(new ByteArrayInputStream(customData))).readObject();
            } catch (IOException | ClassNotFoundException e) {
                logger.error("Cant deserialize buffer, ex: " + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    protected void serializeBuffer() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(buffer);
            objectOutputStream.flush();
            customData = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            logger.error("Cant serialize buffer, ex: " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void bufferSetup() {
        if (buffer == null) deserializeBuffer();
        if (buffer == null) buffer = new Properties();
    }

    public void bufferPut(Object key, Object value) {
        if(value == null) throw new CantBeNullException();
        bufferSetup();
        buffer.put(key, value);
        serializeBuffer();
    }

    public void bufferRemove(Object key) {
        bufferSetup();
        buffer.remove(key);
        serializeBuffer();
    }

    public void bufferClear() {
        bufferSetup();
        buffer = new Properties();
        serializeBuffer();
    }

    public Object bufferGet(Object key) {
        bufferSetup();
        if (key == null || buffer == null) return null;
        return buffer.get(key);
    }

    public Integer bufferGetInt(Object key) {
        return (Integer) bufferGet(key);
    }

    public String bufferGetString(Object key) {
        return (String) bufferGet(key);
    }

    public Boolean bufferGetBool(Object key) {
        return (Boolean) bufferGet(key);
    }



}