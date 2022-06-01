package org.headstrait.texteditor.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.headstrait.texteditor.service.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class ReadValidCsv {
    CSVReader csvReader = new CSVReader();
    Path path;
    HashMap<String, String > hashMap;
    List<String> hashMapLines = new ArrayList<>();

    @Given("a {string}")
    public void aCsvFile(String csvFile) {
        path = Paths.get(csvFile);
        hashMap = csvReader.csvToMap(path);
        hashMap.forEach((k,v)->{
            hashMapLines.add("\""+k+"\""+","+"\""+v+"\"");
        });
    }

    @When("the csv file with exactly two columns")
    public void theCsvFileWithExactlyTwoColumns() {
        boolean isNumberOfColumnsTwo = true;
        for (String line:
             hashMapLines) {
            if(line.split(",").length!=2){
             isNumberOfColumnsTwo=false;
             break;
            }
        }
        Assertions.assertTrue(isNumberOfColumnsTwo);
    }

    @Then("get the corresponding hashmap")
    public void getCorrespondingHashmap() {
        hashMap = csvReader.csvToMap(path);
        HashMap<String, String> expectedHashMap = new HashMap<>();
        expectedHashMap.put("on","off");
        expectedHashMap.put("of", "the");
        Assertions.assertEquals(expectedHashMap, hashMap);
    }
}
