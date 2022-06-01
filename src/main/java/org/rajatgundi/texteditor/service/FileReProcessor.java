package org.headstrait.texteditor.service;

import org.headstrait.texteditor.constants.FilePaths;
import org.headstrait.texteditor.model.Metadata;
import org.headstrait.texteditor.util.CommonUtil;

import java.io.File;
import java.nio.file.Paths;

import static org.headstrait.texteditor.util.CommonUtil.fileWeaver;


public class FileReProcessor {
    /**
     * @param sourceFile of the file being re-processed.
     */
    public Boolean reProcessFile(File sourceFile) {

        Thread thread = Thread.currentThread();
        System.out.println("Thread: "+thread.getName()+" Reprocessing file: " + sourceFile.getAbsolutePath());
        FileReader fileReader = new FileReader(sourceFile.toPath());
        String processedFileContent = fileReader.getFileContent();

        String nonWordPattern = "\\W+";
        String wordPattern = "\\w+";
        String[] processedWords = processedFileContent.split(nonWordPattern);
        String[] processedNonWords = processedFileContent.split(wordPattern);

        //update processedWords array only at required positions for each hash entries.
        Metadata.getPositionMap(sourceFile.getName()).forEach(
                (word,positions) ->{
                    for (int position:
                         positions) {
                        processedWords[position] = word;
                    }
                }
        );

        String reProcessedFileContent = fileWeaver(processedWords,processedNonWords);

        //create a directory to store re-processed files.
        CommonUtil.createDirectory(Paths.get(FilePaths.REPROCESSED_FOLDER));


        System.out.println("ReProcessed: "+sourceFile.getName());

        //create a new file with re-processed content.
        return CommonUtil.createFile(Paths.get(FilePaths.REPROCESSED_FOLDER+ "/"
                        + sourceFile.getName()),
                reProcessedFileContent);
    }
}
