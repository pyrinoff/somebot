package com.github.pyrinoff.somebot.api.command;

import com.github.pyrinoff.somebot.util.StringUtil;

public interface ICommandWithText {

    String DEFAULT_DELIMITER = " ";

    String getText();

    default String getArg(int index) {
        return getArg(index, DEFAULT_DELIMITER);
    }

    default String getArg(int index, String delimiter) {
        return StringUtil.getArgString(getText(), index, delimiter, 1024, false);
    }

    default int getArgCount() {
        int wordsCount = StringUtil.getArgStringCount(getText(), DEFAULT_DELIMITER);
        return wordsCount < 1 ? 0 : wordsCount - 1;
    }

    default boolean hasArgs() {
        return getArgCount() > 0;
    }

    default Integer getArgInt(int index) {
        return getArgInt(index, DEFAULT_DELIMITER);
    }

    default Integer getArgInt(int index, String delimiter) {
        final String arg = getArg(index, delimiter);
        return arg == null ? null : Integer.parseInt(arg);
    }

    default Long getArgLong(int index) {
        return getArgLong(index, DEFAULT_DELIMITER);
    }

    default Long getArgLong(int index, String delimiter) {
        final String arg = getArg(index, delimiter);
        return arg == null ? null : Long.parseLong(arg);
    }

    default String getArgStringStartWithArg(int index) {
        return getArgStringStartWithArg(index, DEFAULT_DELIMITER);
    }

    default String getArgStringStartWithArg(int index, String delimiter) {
        return StringUtil.getArgStringStartWithArg(getText(), index, delimiter);
    }

}
