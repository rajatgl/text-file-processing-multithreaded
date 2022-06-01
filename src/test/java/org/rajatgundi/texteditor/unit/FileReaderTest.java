package org.headstrait.texteditor.unit;

import org.headstrait.texteditor.service.FileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReaderTest {

    FileReader fileReader;
    Path path;

    @Test
    public void fileReaders_whenGivenValidPath_updatesFileContentField(){

        path = Paths.get("./src/test/resources/Words-Substitutes.csv");
        fileReader = new FileReader(path);

        //expected file content
        String expectedContent = "WORDS,SUBSTITUTES\n" +
                "on,off\n" +
                "of,the";

        //actual file content
        String actualContent = fileReader.getFileContent();

        Assertions.assertEquals(expectedContent,actualContent);

    }

    @Test
    public void fileReaders_whenGivenInvalidFilePath_thenFileContentRemainsNull(){

        path = Paths.get("./src/test/resources/Words-Substitutes-invalid-path.csv");
        fileReader = new FileReader(path);

        //expected file content
        String expectedContent = null;

        //actual file content
        String actualContent = fileReader.getFileContent();

        Assertions.assertEquals(expectedContent,actualContent);
    }

    @AfterEach
    public void exit(){
        System.gc();
    }
}
