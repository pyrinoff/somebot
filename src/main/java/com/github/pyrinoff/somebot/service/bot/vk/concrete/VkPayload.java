package com.github.pyrinoff.somebot.service.bot.vk.concrete;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class VkPayload implements Serializable {

    @JsonProperty("s")
    private Integer stage;

    @JsonProperty("p")
    //@JsonDeserialize(using = EmptyArrayToMapDeserializer.class)
    private Map<String, Object> properties = new HashMap<>();

    public Object getProperty(String propertyName) {
        return properties.get(propertyName);
    }

    public VkPayload setProperty(String propertyName, Object value) {
        properties.put(propertyName, value);
        return this;
    }


    public static VkPayload fromPayload(String payload) throws JsonProcessingException {
        //т.к. VK почему-то заменяет {} на [] - использую костыль с заменой
        return new ObjectMapper().readValue(payload.replace("[]", "{}"), VkPayload.class);
    }

    public String toJSON() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    @SneakyThrows
    public static void main(String[] args) {
        //String s = makePayload(new VkPayload().setStage(1));
        //System.out.println(s);
        String json = "{\"s\":1,\"p\":[]}";
        System.out.println(fromPayload(json));
    }

   /*
    public static class EmptyArrayToMapDeserializer extends JsonDeserializer<Map<String, Object>> {
        @Override
        public Map<String, Object> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            if (p.getCurrentToken().isStructStart()) {
                return p.readValueAs(HashMap.class);
            } else {
                return new HashMap<>();
            }
        }
    }*/

}