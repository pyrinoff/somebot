package com.github.pyrinoff.somebot.util.tgparser;

import com.github.pyrinoff.somebot.util.tgparser.model.ChatExportJson;
import com.github.pyrinoff.utils.FileUtil;
import com.github.pyrinoff.utils.JsonUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public interface ParserHistoryUtil {

    static ChatExportJson getTgHistory(@NotNull final String chatExportJsonFilepath) throws IOException {
        final @Nullable String fileContent = FileUtil.fileGetContentAsString(chatExportJsonFilepath);
        if (fileContent == null) throw new RuntimeException("No content in json file: " + chatExportJsonFilepath);
        return JsonUtil.jsonToObject(fileContent, ChatExportJson.class);
    }

}
