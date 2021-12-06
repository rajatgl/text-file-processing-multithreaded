package org.headstrait.texteditor.model;

import java.util.HashMap;
import java.util.List;

public class Metadata {

    private final HashMap<String, HashMap<String, List<Integer>>> metaData = new HashMap<>();

    /**
     *
     * @param fileName of source file
     * @param mapOfWords position mapping of replaced words
     */
    public void setMetaData(String fileName, HashMap<String, List<Integer>> mapOfWords){
        if(!metaData.containsKey(fileName)){
            metaData.put(fileName,mapOfWords);
        }
        metaData.replace(fileName, mapOfWords);
    }

    /**
     *
     * @param fileName of source file
     * @return position mapping of replaced words
     */
    public HashMap<String,List<Integer>> getPositionMap(String fileName){
        return metaData.get(fileName);
    }

    /**
     *
     * @return metaData of source folder
     */
    public HashMap<String, HashMap<String, List<Integer>>> getMetaData(){
        return metaData;
    }
}
