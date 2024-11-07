package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;
import com.github.pyrinoff.utils.StringUtil;

public class TextContainsWordsTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    final String[] startWords;

    public TextContainsWordsTg(final String... words) {
        this.startWords = words;
    }

    @Override
    public boolean isFired(final M message) {
        return
                message.getOriginalMessage().hasMessage()
                        && message.getOriginalMessage().getMessage().hasText()
                        && StringUtil.containsAnyOfWords(
                        message.getOriginalMessage().getMessage().getText(),
                        startWords
                );
    }

}
