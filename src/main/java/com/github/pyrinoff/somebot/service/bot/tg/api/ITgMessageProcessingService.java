package com.github.pyrinoff.somebot.service.bot.tg.api;

import com.github.pyrinoff.somebot.api.service.IMessageProcessingService;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ITgMessageProcessingService extends IMessageProcessingService<Update> {

    void processUpdate(Update update);

}
