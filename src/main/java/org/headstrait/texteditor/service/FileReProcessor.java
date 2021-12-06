package org.headstrait.texteditor.service;

import org.headstrait.texteditor.constants.FilePaths;
import org.headstrait.texteditor.util.CommonUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;


public class FileReProcessor {
    /**
     * @param wordHash hashMap of word and list of positions at with it is replaced.
     * @param sourceFilePath of the file being re-processed.
     */
    public void reProcessFile(HashMap<String, List<Integer>> wordHash,
                           Path sourceFilePath) {
        FileReader fileReader = new FileReader(sourceFilePath);
        String processedFileContent = fileReader.getFileContent();

        String nonWordPattern = "\\W+";
        String wordPattern = "[a-zA-Z]+";
        String[] processedWords = processedFileContent.split(nonWordPattern);
        String[] processedNonWords = processedFileContent.split(wordPattern);

        //update processedWords array only at required positions for each hash entries.
        wordHash.forEach(
                (word,positions) ->{
                    for (int position:
                         positions) {
                        processedWords[position] = word;
                    }
                }
        );

        int i=0,j=0;
        if(processedWords[0].isEmpty())
            i++;

        StringBuilder reprocessedFileContent = new StringBuilder();

        //firstChar logic not needed, always begin with a nonWord
//        char firstChar = processedFileContent.charAt(0);
//        boolean turn = firstChar >= 'a' && firstChar <= 'z' || firstChar >= 'A' && firstChar <= 'Z';

        boolean turn = false;

        //alternatingly iterate through both word and nonWord arrays to construct whole file content
        while (i<processedWords.length || j<processedNonWords.length ){
            if(turn){
                if(i>=processedWords.length) {
                    turn = false;
                    continue;
                }
                reprocessedFileContent.append(processedWords[i]);
                i++;
            }
            else {
                if(j>=processedNonWords.length) {
                    turn = true;
                    continue;
                }
                reprocessedFileContent.append(processedNonWords[j]);
                j++;
            }
            turn = !turn;
        }

        //create a directory to store re-processed files.
        CommonUtil.createDirectory(Paths.get(FilePaths.REPROCESSED_FOLDER));

        //create a new file with re-processed content.
        CommonUtil.createFile(Paths.get(FilePaths.REPROCESSED_FOLDER+ "/"
                        + sourceFilePath.getFileName().toString()),
                reprocessedFileContent.toString());
    }
}
