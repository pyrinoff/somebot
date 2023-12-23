package com.github.pyrinoff.somebot.service.bot.tg.concrete;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.AbstractMessageProcessingService;
import com.github.pyrinoff.somebot.service.bot.tg.api.ITgMessageProcessingService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TgMessageProcessingService
        extends AbstractMessageProcessingService<Update, User, TgMessage>
        implements ITgMessageProcessingService
{

    @Override
    protected TgMessage convertUpdateToMessage(Update update) {
        return new TgMessage(update);
    }

}
