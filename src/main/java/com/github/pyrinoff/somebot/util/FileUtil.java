package com.github.pyrinoff.somebot.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public interface FileUtil {

    static String getFileExtensionFileSystem(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    static void deleteFilesInFolder(String folderPath, boolean failOnError) throws IOException {
        Path directory = Paths.get(folderPath);
        if (!Files.isDirectory(directory)) return;
        try (Stream<Path> listOfFiles = Files.list(directory)) {
            listOfFiles.filter(Files::isRegularFile).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.err.println("Failed to delete file: " + path);
                    if(failOnError) throw new RuntimeException(e);
                }
            });
        }
    }

    static void deleteFilesFromDisc(List<String> filepathsOfSavedPhotos, boolean failOnError) {
        for (String oneFile : filepathsOfSavedPhotos) {
            try {
                Files.deleteIfExists(Path.of(oneFile));
            } catch (IOException e) {
                System.err.println("Failed to delete file: " + oneFile);
                if(failOnError) throw new RuntimeException(e);
            }
        }
    }

}
