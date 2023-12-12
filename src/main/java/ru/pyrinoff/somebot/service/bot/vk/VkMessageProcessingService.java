package ru.pyrinoff.somebot.service.bot.vk;

import com.vk.api.sdk.objects.callback.MessageObject;
import org.springframework.stereotype.Component;
import ru.pyrinoff.somebot.service.AbstractMessageProcessingService;

@Component
public class VkMessageProcessingService<M extends VkMessage> extends AbstractMessageProcessingService<MessageObject, M> {

    @Override
    protected void preprocessMessage(M message) {
    }

    @Override
    protected void postProcessMessage(M message) {
    }

    @Override
    protected M convertUpdateToMessage(MessageObject update) {
        return (M) new VkMessage(update);
    }

}
