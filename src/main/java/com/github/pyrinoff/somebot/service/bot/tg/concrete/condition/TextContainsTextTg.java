package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;
import com.github.pyrinoff.utils.StringUtil;

import java.util.List;

public class TextContainsTextTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    final String[] thePhrases;

    public TextContainsTextTg(final String... phrases) {
        this.thePhrases = phrases;
    }

    @Override
    public boolean isFired(final M message) {
        return message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasText()
                && StringUtil.containsAnyOfPhrases( 
                message.getOriginalMessage().getMessage().getText(),
                thePhrases);
    }

}
