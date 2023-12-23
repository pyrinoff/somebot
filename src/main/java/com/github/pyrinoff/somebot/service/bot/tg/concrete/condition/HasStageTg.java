package com.github.pyrinoff.somebot.service.bot.tg.concrete.condition;


import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.tg.abstraction.AbstractTgMessage;
import com.github.pyrinoff.somebot.service.bot.tg.api.AbstractTgCondition;

import java.util.Arrays;

public class HasStageTg <U extends User, M extends AbstractTgMessage<U>>
        implements AbstractTgCondition<U, M> {

    final int[] stages;

    public HasStageTg(int...stages) {
        this.stages = stages;
    }

    @Override
    public boolean isFired(final M message) {
        return message.hasUser() && Arrays.binarySearch(stages, message.getUser().getStage()) >=0;
    }

}
