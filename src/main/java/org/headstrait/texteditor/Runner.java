package org.headstrait.texteditor;

import org.headstrait.texteditor.constants.FilePaths;
import org.headstrait.texteditor.service.CSVReader;
import org.headstrait.texteditor.service.FileProcessor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Runner {
    public static void main(String[] args) {
        //TODO:step1-create a map of words and its substitutes from the provided csv

        //TODO:step2-replace all the "words" in text file with respective "substitutes"
        //create the file with modified content into new folder, "processedFiles"

        //TODO:step3-replace all the "substitutes" in the 'processedFiles' folder with their respective words
        //create the re-processed files in "reProcessedFiles" folder

        CSVReader csvReader = new CSVReader();
        FileProcessor fileProcessor = new FileProcessor();

        //path for substitute reference
        final Path substituteFilePath =
                Paths.get(FilePaths.SUBSTITUTE_FILE);
        HashMap<String,String> mapOfWords = csvReader.csvToMap(substituteFilePath);
        //path for processed files
        fileProcessor.processFile(mapOfWords);
    }
}
