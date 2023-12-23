package com.github.pyrinoff.somebot.service.bot.tg.abstraction;

import com.github.pyrinoff.somebot.abstraction.AbstractCommand;
import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.TelegramBot;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractCommandTgMessage<U extends User, M extends AbstractTgMessage<U>> extends AbstractCommand<Update, U, M> {

    @Autowired
    @Lazy
    @Getter
    private TelegramBot bot;

}
