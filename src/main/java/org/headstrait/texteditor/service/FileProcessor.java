package org.headstrait.texteditor.service;

import org.headstrait.texteditor.constants.FilePaths;
import org.headstrait.texteditor.util.CommonUtil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FileProcessor {
    /**
     * @param mapOfWords to be referenced during processing.
     */
    public void processFile(Map<String, String> mapOfWords){

        File[] files;
        FileReader fileReader;
        String fileContent;
        FileReProcessor fileReProcessor = new FileReProcessor();

        Path processedFolder = Paths.get(FilePaths.PROCESSED_FOLDER);
        //create directory to store processed files
        if(CommonUtil.createDirectory(processedFolder)){
            System.out.println(processedFolder.getFileName()+"folder created at: "
                    +processedFolder);
        }

        try {
            //array of files in TEXT_FOLDER
            files = new File(FilePaths.TEXT_FOLDER).listFiles();
            assert files != null;

            for (File file: files) {
                System.out.println("Fetching file: "+file.getAbsolutePath());
                fileReader = new FileReader(Paths.get(file.getAbsolutePath()));
                fileContent = fileReader.getFileContent();
                System.out.println("File content: "+fileContent);

                Set<String> words = mapOfWords.keySet();

                HashMap<String, List<Integer>> wordHash = new HashMap<>();
                AtomicInteger count = new AtomicInteger();

                //update the word-positions for each hashMap entry.
                //split by non-word chars
                Arrays.stream(fileContent.split("\\W+"))
                        .forEach(
                        word->{
                            if(words.contains(word)){
                                if(!wordHash.containsKey(word)){
                                    wordHash.put(word,new ArrayList<>());
                                }
                                wordHash.get(word).add(count.get());
                            }
                            count.getAndIncrement();
                        }
                );

                //replace all whole words in text file with required substitute.
                for (String word: words) {
                      fileContent = fileContent
                              .replaceAll("\\b"+word+"\\b",
                                      mapOfWords.get(word));
                }
                System.out.println("New File content: "
                        + fileContent + ", Changed Words Indices: "
                        + wordHash);

                //create new file with updated content.
                CommonUtil.createFile(
                        Paths
                        .get(processedFolder+"/"+file.getName()),
                        fileContent
                );
                fileReProcessor.reProcessFile(wordHash, file.toPath());
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
