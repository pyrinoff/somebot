package com.github.pyrinoff.somebot.util;

public interface RandomUtil {

    static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
