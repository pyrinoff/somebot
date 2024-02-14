package com.github.pyrinoff.somebot.util;

import java.util.Map;

public interface MapUtil {

    static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null; // Если значение не найдено
    }

}
