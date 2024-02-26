package com.github.pyrinoff.somebot.util;

import java.lang.reflect.Field;
import java.util.HashMap;
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

    /**
     * Преобразовать POJO в Map. Можно использовать различными путями.
     */
    public static Map<String, Object> convertToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * Проверка, что в полях объекта есть указанная текстовая строка.
     */
    public static boolean isObjectContainsStringValue(String searchData, Object someObject) {
        return convertToMap(someObject).values().stream().anyMatch(oneField -> oneField != null && oneField.toString().contains(searchData));
    }


}
