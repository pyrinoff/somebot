package ru.pyrinoff.somebot.service.bot.tg;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pyrinoff.somebot.service.AbstractMessageProcessingService;

@Component
public class TgMessageProcessingService<M extends TgMessage> extends AbstractMessageProcessingService<Update, M> {

    @Override
    protected void preprocessMessage(M message) {
    }

    @Override
    protected void postProcessMessage(M message) {
    }

    @Override
    protected M convertUpdateToMessage(Update update) {
        return (M) new TgMessage(update);
    }

}
