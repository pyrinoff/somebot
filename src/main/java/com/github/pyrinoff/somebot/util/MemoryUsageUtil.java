package com.github.pyrinoff.somebot.util;

public interface MemoryUsageUtil {

    static long getMemoryUsageMb() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024*1024);
    }

}
