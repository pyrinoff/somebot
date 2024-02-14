package com.github.pyrinoff.somebot.util;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface GetByUrlUtil {

    static String getContentFromURL(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } finally {
            connection.disconnect();
        }

        return content.toString();
    }

    static String getFileExtensionUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            String path = url.getPath();
            int lastDotIndex = path.lastIndexOf('.');
            if (lastDotIndex != -1 && lastDotIndex < path.length() - 1) {
                return path.substring(lastDotIndex + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    static String saveUrlToNearbyFolder(String urlString, String folder, @Nullable String extension) throws IOException {
        final URL url = new URL(urlString);
        final String currentDir = System.getProperty("user.dir");
        final Path tmpDir = Paths.get(currentDir, folder);
        if (!Files.exists(tmpDir)) Files.createDirectory(tmpDir);
        final String extFromUrl = getFileExtensionUrl(urlString);
        final String filePathString = System.currentTimeMillis()
                //расширение берем из аргумента, либо URL, либо ставим .tmp
                + "."
                + (extension != null ? extension : !extFromUrl.isEmpty() ? extFromUrl : "tmp");

        final Path filePath = tmpDir.resolve(filePathString);
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile());
             ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        ) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            return filePath.toString();
        }
    }

}
