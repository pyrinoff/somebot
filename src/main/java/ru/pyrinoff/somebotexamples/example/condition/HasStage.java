package ru.pyrinoff.somebotexamples.example.condition;

import ru.pyrinoff.somebot.api.condition.ICondition;
import ru.pyrinoff.somebotexamples.example.model.CustomMessage;

import java.util.Arrays;

public class HasStage implements ICondition<CustomMessage> {

    final int[] stages;

    public HasStage(int...stages) {
        this.stages = stages;
    }

    @Override
    public boolean isFired(final CustomMessage message) {
        return message.hasUser() && Arrays.binarySearch(stages, message.getUser().getStage()) >=0;
    }

}
