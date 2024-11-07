package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;
import com.github.pyrinoff.utils.StringUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextStartsWithITextTg<U extends User, M extends AbstractTgMessage<U>> implements AbstractTgCondition<U, M> {

    final List<String> startTexts;

    public TextStartsWithITextTg(final String... text) {
        this.startTexts = StringUtil.toLowerCase(List.of(text));
    }

    @Override
    public boolean isFired(final M message) {
        return
                message.getOriginalMessage().hasMessage()
                        && message.getOriginalMessage().getMessage().hasText()
                        && StringUtil.startsWithAnyOf(
                        message.getOriginalMessage().getMessage().getText().toLowerCase(),
                        startTexts
                );
    }

}
