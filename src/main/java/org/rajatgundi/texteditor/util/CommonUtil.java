package org.headstrait.texteditor.util;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Future;

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

    /**
     *
     * @param tasks
     */
    public static void await(List<Future<Boolean>> tasks) {
        tasks.forEach(task -> {
            try{
//                PerformanceUtil.startTimer();
                task.get();
//                PerformanceUtil.printTimeTaken();
            }
            catch (Exception ignored) {}
        });
    }

    /**
     *
     * @param words array of strings in file content
     * @param nonWords array of non-words in file content
     * @return resultant file content after the union of words and nonWords
     */
    public static String fileWeaver(String[] words, String[] nonWords){

        int i=0,j=0;
        if(words[0].isEmpty())
            i++;

        StringBuilder fileContent = new StringBuilder();

        //start the stitch with a non-word
        boolean turn = false;

        //alternately iterate through both word and nonWord arrays to construct whole file content
        while (i<words.length || j<nonWords.length ){
            if(turn){
                if(i>=words.length) {
                    turn = false;
                    continue;
                }
                fileContent.append(words[i]);
                i++;
            }
            else {
                if(j>=words.length) {
                    turn = true;
                    continue;
                }
                fileContent.append(nonWords[j]);
                j++;
            }
            turn = !turn;
        }
        return fileContent.toString();
    }

    /**
     *
     * @param message to be printed.
     */
    public static void prettyPrint(String message) {
        System.out.println("--------------------------------------------------------------------");
        System.out.println(message);
        System.out.println("--------------------------------------------------------------------");
    }
}
