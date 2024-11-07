package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;
import com.github.pyrinoff.utils.StringUtil;

import java.util.Arrays;

public class TextStartsWithTextTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    final String[] startTexts;

    public TextStartsWithTextTg(final String... text) {
        this.startTexts = text;
    }

    @Override
    public boolean isFired(final M message) {
        return
                message.getOriginalMessage().hasMessage()
                        && message.getOriginalMessage().getMessage().hasText()
                        && StringUtil.startsWithAnyOf(
                        message.getOriginalMessage().getMessage().getText(),
                        startTexts
                );
    }

}
