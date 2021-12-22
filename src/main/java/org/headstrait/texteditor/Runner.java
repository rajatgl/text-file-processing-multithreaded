package org.headstrait.texteditor;

import org.headstrait.texteditor.constants.FilePaths;
import org.headstrait.texteditor.service.CSVReader;
import org.headstrait.texteditor.service.FileProcessor;
import org.headstrait.texteditor.service.FileReProcessor;
import org.headstrait.texteditor.util.CommonUtil;
import org.headstrait.texteditor.util.PerformanceUtil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Runner {

    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    private final FileProcessor fileProcessor;
    private final FileReProcessor fileReProcessor;

    public Runner(FileProcessor fileProcessor, FileReProcessor fileReProcessor) {
        this.fileProcessor = fileProcessor;
        this.fileReProcessor = fileReProcessor;
    }

    public Future<Boolean> initiateFileProcessing(File sourceFile) {
        return executorService.submit(() -> fileProcessor.processFile(sourceFile));
    }

    public Future<Boolean> initiateFileReProcessing(File sourceFile) {
        return executorService.submit(() -> fileReProcessor.reProcessFile(sourceFile));
    }

    public static void main(String[] args) {
        //TODO:step1-create a map of words and its substitutes from the provided csv

        //TODO:step2-replace all the "words" in text file with respective "substitutes"
        //create the file with modified content into new folder, "processedFiles"

        //TODO:step3-replace all the "substitutes" in the 'processedFiles' folder with their respective words
        //create the re-processed files in "reProcessedFiles" folder

        CSVReader csvReader = new CSVReader();

        //path for substitute reference
        final Path substituteFilePath =
                Paths.get(FilePaths.SUBSTITUTE_FILE);
        final Path processedFolderPath = Paths.get(FilePaths.PROCESSED_FOLDER);
        HashMap<String, String> mapOfWords = csvReader.csvToMap(substituteFilePath);


        //create directory to store processed files
        if (CommonUtil.createDirectory(processedFolderPath)) {
            System.out.println(processedFolderPath.getFileName() + "folder created at: "
                    + processedFolderPath);
        }

        FileProcessor fileProcessor = new FileProcessor(mapOfWords);
        FileReProcessor fileReProcessor = new FileReProcessor();
        Runner runner = new Runner(fileProcessor, fileReProcessor);

        //text file folder reference
        File[] textFiles = new File(FilePaths.TEXT_FOLDER).listFiles();
        assert textFiles != null;

        List<Future<Boolean>> processedTasks = new ArrayList<>();

        for (File textFile :
                textFiles) {
            processedTasks.add(runner.initiateFileProcessing(textFile));
        }
        PerformanceUtil.startTimer();
        CommonUtil.await(processedTasks);
        PerformanceUtil.printTimeTaken();

        //processed file folder reference
        File[] processedFiles = new File(FilePaths.PROCESSED_FOLDER).listFiles();
        assert processedFiles != null;

        List<Future<Boolean>> reProcessedTasks = new ArrayList<>();

        for (File processedFile :
                processedFiles) {
            reProcessedTasks.add(runner.initiateFileReProcessing(processedFile));
        }
        PerformanceUtil.startTimer();
        CommonUtil.await(reProcessedTasks);
        PerformanceUtil.printTimeTaken();

        executorService.shutdown();
    }
}
