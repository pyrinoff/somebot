package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;
import com.github.pyrinoff.utils.StringUtil;

import java.util.List;

public class TextContainsITextTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    final List<String> thePhrases;

    public TextContainsITextTg(final String... phrases) {
        this.thePhrases = StringUtil.toLowerCase(List.of(phrases));
    }

    @Override
    public boolean isFired(final M message) {
        return message.getOriginalMessage().hasMessage()
                && message.getOriginalMessage().getMessage().hasText()
                && StringUtil.containsAnyOfPhrases( 
                message.getOriginalMessage().getMessage().getText().toLowerCase(),
                thePhrases);
    }

}
