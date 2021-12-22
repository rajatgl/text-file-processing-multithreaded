package org.headstrait.texteditor.service;

import org.headstrait.texteditor.constants.FilePaths;
import org.headstrait.texteditor.model.Metadata;
import org.headstrait.texteditor.util.CommonUtil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.headstrait.texteditor.util.CommonUtil.fileWeaver;

public class FileProcessor {

    private final Map<String, String> mapOfWords;

    public FileProcessor(Map<String, String> mapOfWords) {
        this.mapOfWords = mapOfWords;
    }

    public Boolean processFile(File file) {

        FileReader fileReader;
        String fileContent;

        Path processedFolder = Paths.get(FilePaths.PROCESSED_FOLDER);

        Thread thread = Thread.currentThread();
        System.out.println("Thread: "+thread.getName()+" Processing file: " + file.getAbsolutePath());
        fileReader = new FileReader(Paths.get(file.getAbsolutePath()));
        fileContent = fileReader.getFileContent();
//        System.out.println("File content: " + fileContent);

        Set<String> words = mapOfWords.keySet();

        HashMap<String, List<Integer>> wordPositionsMap = new HashMap<>();
        AtomicInteger count = new AtomicInteger();

        //update the word-positions for each hashMap entry.
        //split by non-word chars
        Arrays.stream(fileContent.split("\\W+"))
                .forEach(
                        fileWord -> {
                            if (words.contains(fileWord)) {
                                if (!wordPositionsMap.containsKey(fileWord)) {
                                    wordPositionsMap.put(fileWord, new ArrayList<>());
                                }
                                wordPositionsMap.get(fileWord).add(count.get());
                            }
                            count.getAndIncrement();
                        }
                );
        Metadata.setMetaData(file.getName(),wordPositionsMap);

        String nonWordPattern = "\\W+";
        String wordPattern = "\\w+";
        String[] fileContentWords = fileContent.split(nonWordPattern);
        String[] fileContentNonWords = fileContent.split(wordPattern);

        //replace words in file with provided substitute
//        mapOfWords.forEach((word,substitute)->{
//            for (int i = 0; i<fileContentWords.length;i++){
//                if(Objects.equals(fileContentWords[i], word))
//                    fileContentWords[i]=substitute;
//            }
//        });

        //optimized
        for(int i = 0; i<fileContentWords.length;i++){
            String fileWord = fileContentWords[i];
            if(mapOfWords.containsKey(fileWord)){
                fileContentWords[i] = mapOfWords.get(fileWord);
            }
        }

        fileContent = fileWeaver(fileContentWords,fileContentNonWords);

        //replace all whole words in text file with required substitute.
//        for (String word : words) {
//            fileContent = fileContent
//                    .replaceAll("\\b" + word + "\\b",
//                            mapOfWords.get(word));
//        }
//        System.out.println("New File content: "
//                + fileContent + ", Changed Words Indices: "
//                + wordPositionsMap);
        //for keeping track of thread processing
        System.out.println("Processed: "+file.getName());

        //create new file with updated content.
        return CommonUtil.createFile(
                Paths
                        .get(processedFolder + "/" + file.getName()),
                fileContent
        );
    }
}
