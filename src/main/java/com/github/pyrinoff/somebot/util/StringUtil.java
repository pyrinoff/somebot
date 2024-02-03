package com.github.pyrinoff.somebot.util;

public interface StringUtil {

    int maxIntLength = String.valueOf(Integer.MAX_VALUE).length();

    static String getArgString(
            final String str,
            final int index,
            String delimiter,
            final int maxLen,
            final boolean cutIfLonger
    ) {
        if (index < 0) return null;
        if (delimiter == null) delimiter = " ";

        String[] exploded = str.split(delimiter);
        if ((index + 1) > exploded.length) return null;
        if (maxLen >= exploded[index].length()) return exploded[index];
        else {
            if (cutIfLonger) return exploded[index].substring(0, maxLen);
            else return null;
        }
    }

    static Integer getArgInt(String str, int index, String delimiter) {
        String s = getArgString(str, index, delimiter, maxIntLength, false);
        if (s == null) return null;
        return Integer.parseInt(s);
    }

    static Long getArgLong(String str, int index, String delimiter) {
        String s = getArgString(str, index, delimiter, maxIntLength, false);
        if (s == null) return null;
        return Long.parseLong(s);
    }

    static int getArgStringCount(
            final String str,
            String delimiter
    ) {
        String[] exploded = str.split(delimiter);
        return exploded.length;
    }

    static String getArgStringStartWithArg(
            final String str,
            int index,
            String delimiter
    ) {
        String[] exploded = str.split(delimiter);
        if ((index + 1) > exploded.length) return null;
        StringBuilder result = new StringBuilder();
        for (int i = index; i < exploded.length; i++) {
            result.append(exploded[i]).append(" ");
        }
        return result.toString().trim();
    }

}
