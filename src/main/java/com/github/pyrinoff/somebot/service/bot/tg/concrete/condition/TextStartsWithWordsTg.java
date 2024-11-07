package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;
import com.github.pyrinoff.utils.StringUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextStartsWithWordsTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    final String[] startWords;

    public TextStartsWithWordsTg(final String... words) {
        this.startWords = words;
    }

    @Override
    public boolean isFired(final M message) {
        return
                message.getOriginalMessage().hasMessage()
                        && message.getOriginalMessage().getMessage().hasText()
                        && StringUtil.startsWithAnyOfWords(
                        message.getOriginalMessage().getMessage().getText(),
                        startWords
                );
    }

}
