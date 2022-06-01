package org.headstrait.texteditor.unit;

import org.headstrait.texteditor.constants.FilePaths;
import org.headstrait.texteditor.service.FileProcessor;
import org.headstrait.texteditor.service.FileReProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileReProcessorTest {

    @BeforeEach
    public void init(){
        Map<String, String> mapOfWords = new HashMap<>();
        mapOfWords.put("on","off");
        mapOfWords.put("of", "the");
        FileProcessor fileProcessor = new FileProcessor(mapOfWords);
        File file = Objects.requireNonNull(new File(FilePaths.TEXT_FOLDER).listFiles())[0];
        fileProcessor.processFile(file);
    }

    @Test
    public void fileReProcessor_withValidFile_returnsTrue(){
        FileReProcessor fileReProcessor = new FileReProcessor();
        File file = Objects.requireNonNull(new File(FilePaths.PROCESSED_FOLDER).listFiles())[0];
        Assertions.assertTrue(fileReProcessor.reProcessFile(file));
    }

}
