package org.headstrait.texteditor.service;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * to read a file and fetch its content.
 */
public class FileReader {
    private String fileContent;
    public FileReader(Path filePath){
        try {
            fileContent = Files.readString(filePath);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public String getFileContent(){
        return fileContent;
    }
}
