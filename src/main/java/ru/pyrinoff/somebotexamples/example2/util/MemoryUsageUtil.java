package ru.pyrinoff.somebotexamples.example2.util;

public interface MemoryUsageUtil {

    static long getMemoryUsageMb() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024*1024);
    }

}
