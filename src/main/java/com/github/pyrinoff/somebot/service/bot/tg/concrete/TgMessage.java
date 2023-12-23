package com.github.pyrinoff.somebot.service.bot.tg.concrete;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TgMessage extends AbstractTgMessage<User> {

    public TgMessage(Update originalMessage) {
        super(originalMessage);
    }
}