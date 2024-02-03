package com.github.pyrinoff.somebot.service.bot.vk.abstraction;

import com.github.pyrinoff.somebot.abstraction.AbstractCommand;
import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.VkBot;
import com.vk.api.sdk.objects.callback.MessageObject;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import com.vk.api.sdk.objects.messages.KeyboardButtonAction;
import com.vk.api.sdk.objects.messages.TemplateActionTypeNames;
import lombok.Getter;
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

    protected static KeyboardButton createButton(String text, Integer payload) {
        return createButton(text, String.valueOf(payload));
    }

    protected static KeyboardButton createButton(String text, String payload) {
        return new KeyboardButton().
                setAction(new KeyboardButtonAction()
                        .setLabel(text)
                        .setPayload(payload)
                        .setType(TemplateActionTypeNames.TEXT)
                );
    }

}
