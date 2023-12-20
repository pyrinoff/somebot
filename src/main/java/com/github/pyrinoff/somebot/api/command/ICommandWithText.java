package com.github.pyrinoff.somebot.api.command;

import com.github.pyrinoff.somebot.util.StringUtil;

public interface ICommandWithText {

    String getText();

    default String getArg(int index) {
        return StringUtil.getArgString(getText(), index, " ", 1024, false);
    }

    default Integer getArgInt(int index) {
        final String arg = getArg(index);
        return arg == null ? null : Integer.parseInt(arg);
    }

}
