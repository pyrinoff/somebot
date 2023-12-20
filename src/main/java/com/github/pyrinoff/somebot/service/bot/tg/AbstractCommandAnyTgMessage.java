package com.github.pyrinoff.somebot.service.bot.tg;

import com.github.pyrinoff.somebot.abstraction.AbstractCommand;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractCommandAnyTgMessage<M extends TgMessage> extends AbstractCommand<Update, M> {

    @Autowired
    @Lazy
    @Getter
    private TelegramBot bot;

}
