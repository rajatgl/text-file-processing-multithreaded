package org.headstrait.texteditor.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.*;

/**
 * to read a file and fetch its content.
 */
public class FileReader {

    private String fileContent;

    public FileReader(Path filePath) {
        try {
            fileContent = Files.readString(filePath, ISO_8859_1);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public String getFileContent() {
        return fileContent;
    }
}
