package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;
import com.github.pyrinoff.utils.RegexUtil;
import com.github.pyrinoff.utils.StringUtil;

public class TextMatchesRegexTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    final String theRegex;

    public TextMatchesRegexTg(final String regex) {
        this.theRegex = regex;
    }

    @Override
    public boolean isFired(final M message) {
        return
                message.getOriginalMessage().hasMessage()
                        && message.getOriginalMessage().getMessage().hasText()
                        && RegexUtil.hasAtLeastOneMatch(
                        message.getOriginalMessage().getMessage().getText(),
                        theRegex
                );
    }

}
