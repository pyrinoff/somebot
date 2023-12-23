package com.github.pyrinoff.somebot.service.bot.vk.concrete.condition;

import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.bot.vk.abstraction.AbstractVkMessage;
import com.github.pyrinoff.somebot.service.bot.vk.api.AbstractVkCondition;

import java.util.Arrays;

public class HasStageVk <U extends User, M extends AbstractVkMessage<U>>
        implements AbstractVkCondition<U, M> {

    final int[] stages;

    public HasStageVk(int...stages) {
        this.stages = stages;
    }

    @Override
    public boolean isFired(final M message) {
        return message.hasUser() && Arrays.binarySearch(stages, message.getUser().getStage()) >=0;
    }

}
