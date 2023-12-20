package ru.pyrinoff.somebot.service.bot.tg;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pyrinoff.somebot.abstraction.AbstractCommand;

public abstract class AbstractCommandAnyTgMessage<M extends TgMessage> extends AbstractCommand<Update, M> {

    @Autowired
    @Lazy
    @Getter
    private TelegramBot bot;

}
