package com.github.pyrinoff.somebot.service.bot.vk.abstraction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pyrinoff.somebot.abstraction.AbstractCommand;
import com.github.pyrinoff.somebot.api.command.ICommandWithPayload;
import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.PropertyService;
import com.github.pyrinoff.somebot.service.bot.vk.VkBot;
import com.vk.api.sdk.objects.callback.MessageObject;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import com.vk.api.sdk.objects.messages.KeyboardButtonAction;
import com.vk.api.sdk.objects.messages.TemplateActionTypeNames;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractCommandVkMessage<U extends User, M extends AbstractVkMessage<U>> extends AbstractCommand<MessageObject, U, M> {

    @Autowired
    @Lazy
    @Getter
    private VkBot bot;

    @Autowired
    @Lazy
    private PropertyService propertyService;

    protected static Keyboard createKeyboard(boolean inline, boolean oneTime, KeyboardButton... buttons) {
        Keyboard keyboard = new Keyboard();
        keyboard.setInline(inline);
        keyboard.setOneTime(oneTime);
        keyboard.setButtons(
                Arrays.stream(buttons)
                        .map(List::of)
                        .collect(Collectors.toList())
        );
        return keyboard;
    }

    protected static Keyboard createKeyboard(KeyboardButton... buttons) {
        return createKeyboard(false, false, buttons);
    }

    protected static Keyboard clearKeyboard() {
        return new Keyboard().setInline(false).setOneTime(false);
    }

    protected static Keyboard dontChangeKeyboard() {
        return null;
    }

    protected static KeyboardButton createButton(String text, Integer... payload) {
        return createButton(text, String.join(ICommandWithPayload.DEFAULT_DELIMITER,
                Arrays.stream(payload)
                        .map(Object::toString)
                        .toArray(String[]::new))
        );
    }

    @SneakyThrows
    protected static KeyboardButton createButton(String text, String payload) {
        String payloadJson = new ObjectMapper().writeValueAsString(payload);
        if(payloadJson.length() >= 1000) throw new IllegalArgumentException("Payload can't be longer than 1000 symbols!");
        return new KeyboardButton().
                setAction(new KeyboardButtonAction()
                        .setLabel(text)
                        .setPayload(payloadJson) //JSON, max 1000 symbols
                        .setType(TemplateActionTypeNames.TEXT)
                );
    }

    @SneakyThrows
    protected static KeyboardButton createButton(String text) {
        return new KeyboardButton().
                setAction(new KeyboardButtonAction()
                        .setLabel(text)
                        .setType(TemplateActionTypeNames.TEXT)
                );
    }

    public boolean isUserAdmin(U user) {
        if(user == null) return false;
        return propertyService.getVkAdminId().equals((user.getChatId().intValue()));
    }

}
