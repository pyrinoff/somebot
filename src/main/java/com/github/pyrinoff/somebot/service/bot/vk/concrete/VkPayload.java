package com.github.pyrinoff.somebot.service.bot.vk.concrete;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.abstraction.AbstractVkMessage;
import com.vk.api.sdk.objects.callback.MessageObject;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Properties;

@Data
public class VkPayload implements Serializable {

    private Integer stage;

    private Properties properties;

}