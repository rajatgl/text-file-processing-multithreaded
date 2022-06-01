package org.headstrait.texteditor.unit;

import org.headstrait.texteditor.constants.FilePaths;
import org.headstrait.texteditor.service.FileProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FileProcessorTest {

    HashMap<String, String> mapOfWords;
    FileProcessor fileProcessor;
    @BeforeEach
    public void init(){
        mapOfWords = new HashMap<>();
    }

    @Test
    public void processFile_givenMapOfWords_returnsTrue(){
        mapOfWords.put("on","off");
        mapOfWords.put("of", "the");
        fileProcessor = new FileProcessor(mapOfWords);

        File file = Objects.requireNonNull(new File(FilePaths.TEXT_FOLDER).listFiles())[0];

        Assertions.assertTrue(fileProcessor.processFile(file));
    }

}
