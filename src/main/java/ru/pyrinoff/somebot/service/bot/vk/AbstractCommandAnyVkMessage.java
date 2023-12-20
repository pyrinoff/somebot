package ru.pyrinoff.somebot.service.bot.vk;

import com.vk.api.sdk.objects.callback.MessageObject;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.pyrinoff.somebot.abstraction.AbstractCommand;

public abstract class AbstractCommandAnyVkMessage<M extends VkMessage> extends AbstractCommand<MessageObject, M> {

    @Autowired
    @Lazy
    @Getter
    private VkBot bot;

}
