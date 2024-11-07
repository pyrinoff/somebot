
package com.github.pyrinoff.somebot.util.tgparser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import javax.annotation.processing.Generated;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "text",
    "href"
})
@Generated("jsonschema2pojo")
public class TextEntity {

    @JsonProperty("type")
    public String type;

    @JsonProperty("text")
    public String text;

    @JsonProperty("href")
    public String href;

    @JsonProperty("document_id")
    public String documentId;

    @JsonProperty("language")
    public String language;

}
