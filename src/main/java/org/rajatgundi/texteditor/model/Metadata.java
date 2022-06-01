package org.headstrait.texteditor.model;

import java.util.HashMap;
import java.util.List;

public class Metadata {

    private static final HashMap<String, HashMap<String, List<Integer>>> metaData = new HashMap<>();

    /**
     *
     * @param fileName of source file
     * @param wordPositionsMap position mapping of replaced words
     */
    public static void setMetaData(String fileName, HashMap<String, List<Integer>> wordPositionsMap){
        metaData.put(fileName, wordPositionsMap);
    }

    /**
     *
     * @param fileName of source file
     * @return position mapping of replaced words
     */
    public static HashMap<String,List<Integer>> getPositionMap(String fileName){
        return metaData.get(fileName);
    }
}
