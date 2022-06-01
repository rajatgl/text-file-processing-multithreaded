package org.headstrait.texteditor.unit;

import org.headstrait.texteditor.service.CSVReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class CSVReaderTest {

    CSVReader csvReader;
    Path path;

    @BeforeEach
    public void init(){
       csvReader = new CSVReader();
    }

    @Test
    public void csvToMap_givenValidFile_returnsHashMapOfTheContent() throws IOException {
        //provided valid csv file
        path = Paths.get("./src/test/resources/Words-Substitutes.csv");
        //expected hashmap
        HashMap<String, String> expectedHashMap = new HashMap<>();
        expectedHashMap.put("on","off");
        expectedHashMap.put("of", "the");

        //assert for the hashmap content
        Assertions.assertEquals(expectedHashMap,csvReader.csvToMap(path));
    }

    @Test
    public void csvToMap_givenInvalidFilePath_returnsEmptyHashMap(){
        //given invalid file path
        path = Paths.get("./src/test/resources/Word-Substitutions-Invalid.csv");

        //expect empty hashmap
        HashMap<String, String> expectedHashMap = new HashMap<>();

        Assertions.assertEquals(expectedHashMap,csvReader.csvToMap(path));
    }

    @Test
    public void csvToMap_givenInvalidFileContent_returnsEmptyHashMap(){
        //given invalid file content
        path = Paths.get("./src/test/resources/Word-Substitutions_Invalid.csv");

        //expect empty hashmap
        HashMap<String, String> expectedHashMap = new HashMap<>();

        //actual hashmap
        HashMap<String, String> actualHashMap = csvReader.csvToMap(path);

        Assertions.assertEquals(expectedHashMap,actualHashMap);

    }

    @AfterEach
    public void exit(){
        System.gc();
    }
}
