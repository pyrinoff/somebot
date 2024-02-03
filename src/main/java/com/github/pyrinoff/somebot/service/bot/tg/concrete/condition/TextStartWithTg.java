package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;

public class TextStartWithTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    final String theText;

    public TextStartWithTg(final String text) {
        this.theText = text;
    }

    @Override
    public boolean isFired(final M message) {
        return message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasText()
                && message.getOriginalMessage().getMessage().getText().startsWith(theText);
    }

}
