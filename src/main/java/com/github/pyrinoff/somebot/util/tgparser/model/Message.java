
package com.github.pyrinoff.somebot.util.tgparser.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
    "id",
    "type",
    "date",
    "date_unixtime",
    "edited",
    "edited_unixtime",
    "actor",
    "actor_id",
    "action",
    "message_id",
    "from",
    "from_id",
    "text",
    "text_entities"
})
@Generated("jsonschema2pojo")
public class Message {

    @JsonProperty("id")
    public Integer id;

    @JsonIgnore
    @JsonProperty("type")
    public String type;

    @JsonIgnore
    @JsonProperty("date")
    public String date;

    @JsonProperty("date_unixtime")
    public String dateUnixtime;

    @JsonIgnore
    @JsonProperty("edited")
    public String edited;

    @JsonIgnore
    @JsonProperty("edited_unixtime")
    public String editedUnixtime;

    //@JsonIgnore
    @JsonProperty("actor")
    public String actor;

    //@JsonIgnore
    @JsonProperty("actor_id")
    public String actor_id;

    //@JsonIgnore
    @JsonProperty("action")
    public String action;

    @JsonIgnore
    @JsonProperty("message_id")
    public String messageId;

    //@JsonIgnore
    @JsonProperty("from")
    public String from;

    //@JsonIgnore
    @JsonProperty("from_id")
    public String fromId;

    @JsonIgnore
    @JsonProperty("forwarded_from")
    public String forwardedFrom;

    @JsonIgnore
    @JsonProperty("reply_to_message_id")
    public String replyToMessageId;

    @JsonIgnore
    @JsonProperty("photo")
    public String photo;

    @JsonIgnore
    @JsonProperty("width")
    public Integer width;

    @JsonIgnore
    @JsonProperty("height")
    public Integer height;

    @JsonIgnore
    @JsonProperty("text")
    public String text;

    @JsonProperty("text_entities")
    public List<TextEntity> textEntities = new ArrayList<TextEntity>();

    @JsonProperty("members")
    public List<String> members = new ArrayList<>();

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", dateUnixtime='" + dateUnixtime + '\'' +
                ", edited='" + edited + '\'' +
                ", editedUnixtime='" + editedUnixtime + '\'' +
                ", actor='" + actor + '\'' +
                ", actor_id='" + actor_id + '\'' +
                ", action='" + action + '\'' +
                ", messageId='" + messageId + '\'' +
                ", from='" + from + '\'' +
                ", fromId='" + fromId + '\'' +
                ", forwardedFrom='" + forwardedFrom + '\'' +
                ", replyToMessageId='" + replyToMessageId + '\'' +
                ", photo='" + photo + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", text='" + text + '\'' +
                ", textEntities=" + textEntities +
                '}';
    }

}
