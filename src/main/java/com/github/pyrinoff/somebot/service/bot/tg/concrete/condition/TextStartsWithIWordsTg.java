package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;
import com.github.pyrinoff.utils.StringUtil;

import java.util.List;

public class TextStartsWithIWordsTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    final List<String> startWords;

    public TextStartsWithIWordsTg(final String... text) {
        this.startWords = StringUtil.toLowerCase(List.of(text));
    }

    @Override
    public boolean isFired(final M message) {
        return
                message.getOriginalMessage().hasMessage()
                        && message.getOriginalMessage().getMessage().hasText()
                        && StringUtil.startsWithAnyOfWords(
                        message.getOriginalMessage().getMessage().getText().toLowerCase(),
                        startWords
                );
    }

}
