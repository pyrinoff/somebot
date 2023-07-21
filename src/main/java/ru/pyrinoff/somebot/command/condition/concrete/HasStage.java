package ru.pyrinoff.somebot.command.condition.concrete;

import ru.pyrinoff.somebot.api.condition.ICondition;
import ru.pyrinoff.somebot.model.Message;

import java.util.Arrays;

public class HasStage implements ICondition {

    final int[] stages;

    public HasStage(int...stages) {
        this.stages = stages;
    }

    @Override
    public boolean isFired(final Message message) {
        return message.hasUser() && Arrays.binarySearch(stages, message.getUser().getStage()) >=0;
    }

}
