package com.github.pyrinoff.somebot.service.bot.tg.abstraction;

import com.github.pyrinoff.somebot.abstraction.AbstractCommand;
import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.TelegramBot;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractCommandTgMessage<U extends User, M extends AbstractTgMessage<U>> extends AbstractCommand<Update, U, M> {

    @Autowired
    @Lazy
    @Getter
    private TelegramBot bot;

    protected static ReplyKeyboardMarkup createKeyboard(boolean oneTime, KeyboardButton... buttons) {
        return ReplyKeyboardMarkup.builder()
                .keyboard(Arrays.stream(buttons)
                        .map(o -> new KeyboardRow(List.of(o)))   //1 кнопка на строку.
                        .collect(Collectors.toList()))
                .oneTimeKeyboard(oneTime)
                .build();
    }

/*    protected static InlineKeyboardMarkup createKeyboard(InlineKeyboardButton... buttons) {
        return InlineKeyboardMarkup.builder()
                .keyboard(Arrays.stream(buttons)
                        .map(List::of)
                        .collect(Collectors.toList()))
                .build();
    }*/


    protected static ReplyKeyboardRemove clearKeyboard() {
        return ReplyKeyboardRemove.builder()
                .removeKeyboard(true)
                //.selective(false)
                .build();
    }

    protected static ForceReplyKeyboard forceReply (String placeholder) {
        return ForceReplyKeyboard.builder()
                .forceReply(true)
                .inputFieldPlaceholder(placeholder)
                .build();
    }

    protected static KeyboardButton createButton(String text) {
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText(text);
        return keyboardButton;
    }

/*    protected static InlineKeyboardButton createButtonInline(String text, String callback) {
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        keyboardButton.setText(text);
        keyboardButton.setCallbackData(callback);
        return keyboardButton;
    }*/


}
