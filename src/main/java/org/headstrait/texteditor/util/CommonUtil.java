package org.headstrait.texteditor.util;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class CommonUtil {

    /**
     * @param path of the directory to be created.
     * @return true if directory creation is successful.
     */
    public static Boolean createDirectory(Path path){
        try {
            Files.createDirectories(path);
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param path absolute path of file to be created.
     * @param fileContent of the file to be created.
     * @return true if operation is successful.
     */
    public static Boolean createFile(Path path, String fileContent){
        FileWriter fileWriter;
        try {
            File file = new File(path.toString());
            file.createNewFile();
            fileWriter = new FileWriter(String.valueOf(path));
            fileWriter.write(fileContent);
            fileWriter.close();
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
