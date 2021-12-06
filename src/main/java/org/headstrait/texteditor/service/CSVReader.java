package org.headstrait.texteditor.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class CSVReader {

    private final HashMap<String, String> wordSubstitutes = new HashMap<>();

    /**
     * @param path of the word-substitute file to be referenced
     * @return hashMap of words and list of its positions from the text file.
     */
    public HashMap<String, String> csvToMap(Path path) {
        try {
            List<String> lines = Files.readAllLines(path);
            lines.subList(1, lines.size()).stream()
                    .map(line -> line.split(","))
                    .forEach(
                            wordSubstitute ->
                                    wordSubstitutes.put(wordSubstitute[0]
                                                    .replaceAll("\"",""),
                                            wordSubstitute[1].replaceAll("\"",""))
                    );
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return wordSubstitutes;
    }



}
