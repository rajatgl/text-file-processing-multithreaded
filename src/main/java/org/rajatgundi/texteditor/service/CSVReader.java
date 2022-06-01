package org.headstrait.texteditor.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CSVReader {

    private final HashMap<String, String> wordSubstitutes = new HashMap<>();

    /**
     * @param path of the word-substitute file to be referenced
     * @return hashMap of words and list of its positions from the text file.
     */
    public HashMap<String, String> csvToMap(Path path) {
        try {
            List<String> lines = Files.readAllLines(path);
            List<String[]> intermediateFileContents = lines.subList(1, lines.size()).stream()
                    .map(line -> line.split(",")).collect(Collectors.toList());

            for (String[] intermediateFileContent:
                 intermediateFileContents) {
                if(intermediateFileContent.length!=2)
                    throw new Exception("Invalid Input");
                wordSubstitutes.put(intermediateFileContent[0]
                                .replaceAll("\"",""),
                        intermediateFileContent[1]
                                .replaceAll("\"",""));
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            wordSubstitutes.clear();
        }
        return wordSubstitutes;
    }



}
