package com.github.pyrinoff.somebot.service.bot.vk.abstraction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.pyrinoff.somebot.abstraction.AbstractCommand;
import com.github.pyrinoff.somebot.api.command.ICommandWithPayload;
import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.PropertyService;
import com.github.pyrinoff.somebot.service.bot.vk.VkBot;
import com.github.pyrinoff.somebot.service.bot.vk.concrete.VkPayload;
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
import java.util.Map;
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
        return createButton(text, makePayload(payload));
    }

    @SneakyThrows
    public static String makePayload(String payload) {
        return new ObjectMapper().writeValueAsString(payload);
    }

    public static String readPayload(String payload) throws JsonProcessingException {
        return new ObjectMapper().readValue(payload, String.class).replaceAll("\"", "");
    }

    @SneakyThrows
    public static String makePayload(Integer... payload) {
        return makePayload(String.join(ICommandWithPayload.DEFAULT_DELIMITER,
                Arrays.stream(payload)
                        .map(Object::toString)
                        .toArray(String[]::new))
        );
    }

    protected static KeyboardButton createButton(String text, String payload) {
        return createButtonWithEncodedPayload(text, makePayload(payload));
    }

    protected static KeyboardButton createButton(String text, VkPayload payload) {
        try {
            return createButtonWithEncodedPayload(text,  payload.toJSON());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static KeyboardButton createButtonWithEncodedPayload(String text, String completePayload) {
        if (completePayload.length() >= 1000)
            throw new IllegalArgumentException("Payload can't be longer than 1000 symbols!");
        return new KeyboardButton().
                setAction(new KeyboardButtonAction()
                        .setLabel(text)
                        .setPayload(completePayload)
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

    @SneakyThrows
    protected static KeyboardButton createGroupPaymentButton(Integer vkAppId,
                                                             Integer groupId,
                                                             Integer amount,
                                                             String description) {
        return (new KeyboardButton())
                .setAction((new KeyboardButtonAction())
                        .setType(TemplateActionTypeNames.VKPAY)
                        .setHash(
                                "action=pay-to-group" +
                                        "&group_id=" + groupId +
                                        "&amount=" + amount +
                                        "&aid=" + vkAppId +
                                        "&description=" + description
                        )
                );
    }

    public boolean isUserAdmin(U user) {
        if (user == null) return false;
        return propertyService.getVkAdminId().equals((user.getChatId().intValue()));
    }

}
