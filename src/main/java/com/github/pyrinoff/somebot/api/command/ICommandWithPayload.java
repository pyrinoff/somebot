package com.github.pyrinoff.somebot.api.command;

import com.github.pyrinoff.somebot.service.bot.vk.concrete.VkPayload;
import com.github.pyrinoff.utils.StringUtil;

public interface ICommandWithPayload {

    String DEFAULT_DELIMITER = " ";

    String getPayload();

    VkPayload getPayloadVk();

    default Integer getPayloadInt() {
        if(getPayload() == null) return null;
        return Integer.parseInt(getPayload());
    }

    default String getPayloadArg(int index) {
        return getPayloadArg(index, DEFAULT_DELIMITER);
    }

    default String getPayloadArg(int index, String delimiter) {
        if(getPayload() == null) return null;
        return StringUtil.getArgString(getPayload(), index, delimiter, 1024, false);
    }

    default int getPayloadArgCount() {
        if(getPayload() == null) return 0;
        int wordsCount = StringUtil.getArgStringCount(getPayload(), DEFAULT_DELIMITER);
        return wordsCount < 1 ? 0 : wordsCount - 1;
    }

    default boolean hasPayloadArgs() {
        return getPayloadArgCount() > 0;
    }

    default Integer getPayloadArgInt(int index) {
        return getPayloadArgInt(index, DEFAULT_DELIMITER);
    }

    default Integer getPayloadArgInt(int index, String delimiter) {
        final String arg = getPayloadArg(index, delimiter);
        return arg == null ? null : Integer.parseInt(arg);
    }

    default Long getPayloadArgLong(int index) {
        return getPayloadArgLong(index, DEFAULT_DELIMITER);
    }

    default Long getPayloadArgLong(int index, String delimiter) {
        final String arg = getPayloadArg(index, delimiter);
        return arg == null ? null : Long.parseLong(arg);
    }

    default String getPayloadArgStringStartWithArg(int index) {
        return getPayloadArgStringStartWithArg(index, DEFAULT_DELIMITER);
    }

    default String getPayloadArgStringStartWithArg(int index, String delimiter) {
        if(getPayload() == null) return null;
        return StringUtil.getArgStringStartWithArg(getPayload(), index, delimiter);
    }

}
