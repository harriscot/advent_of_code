package main.java.prob8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.java.util.DataReader;

public class DisplayAdapter {

    private final String DATA_FILE = "src/main/java/resources/prob8_input.txt";
    private Map<List<String>,List<String>> dataRows;
    private final static int NUMBER_OF_SEGMENTS_IN_DIGIT_1 = 2;
    private final static int NUMBER_OF_SEGMENTS_IN_DIGIT_4 = 4;
    private final static int NUMBER_OF_SEGMENTS_IN_DIGIT_7 = 3;
    private final static int NUMBER_OF_SEGMENTS_IN_DIGIT_8 = 7;

    public DisplayAdapter() {
        initialise();
    }

    public void mapInputs() {
        Entry<List<String>, List<String>> entry = dataRows.entrySet().iterator().next();
        // map the segments
        // top segment is the one that is in 7 but not in 1.
        // middle segment is the one 
        Map<Number, List<Character>> displayNumberKey = new HashMap<>();
        Map<Segment, Character> segmentKey = new HashMap<>();
        List<Character> horizontalSegments = new ArrayList<>();
        mapSimpleNumbers(displayNumberKey, entry);
        displayNumberKey.entrySet().stream().forEach(s -> System.out.println(s.getKey().toString() + s.getValue()));
        mapTopSegment(displayNumberKey, segmentKey);
        mapHorizontalSegments(entry.getKey(), horizontalSegments);
        mapCentreSegment(displayNumberKey, horizontalSegments, segmentKey);
        
    }


    private void mapCentreSegment(Map<Number, List<Character>> displayNumberKey, List<Character> horizontalSegments,
            Map<Segment, Character> segmentKey) {
                // the centre segment is the horizontal one that is also in four.
                List<Character> four = displayNumberKey.get(Number.FOUR);
                for(Character horizontalSegment: horizontalSegments){
                    for(Character fourSegment: four){
                        if(fourSegment == horizontalSegment){
                            segmentKey.put(Segment.MIDDLE, fourSegment);
                        }
                    }
                }

                char bottomValue = 'z';
                // the bottom segment is the remaining horizontal segment that is not in the segment key yet.
                for(Character horizontalSegment: horizontalSegments){
                    for(Entry<Segment, Character> entry: segmentKey.entrySet()){
                        if(entry.getValue() != horizontalSegment){
                            bottomValue = horizontalSegment;
                        }
                    }
                }
                segmentKey.put(Segment.BOTTOM, bottomValue);
                System.out.println("top segment: " + segmentKey.get(Segment.TOP));
                System.out.println("centre segment: " + segmentKey.get(Segment.MIDDLE));
                System.out.println("bottom segment: " + segmentKey.get(Segment.BOTTOM));
    }

    private void mapHorizontalSegments(List<String> entry, List<Character> horizontalSegments) {
        // horizontal segments are common to all five segment numbers.
        List<List<Character>> codeList = new ArrayList<>();
        for(String code: entry){
            if(code.length() == 5){
                codeList.add(charsFor(code));
            }
        }
        horizontalSegments.addAll(codeList.get(0));
        
        for(List<Character> code: codeList){
            horizontalSegments.retainAll(code);
        }
        System.out.println("horizontal segments: ");
        horizontalSegments.stream().forEach(System.out::println);
    }

    private void mapTopSegment(Map<Number, List<Character>> displayNumberKey, Map<Segment, Character> segmentKey) {
        // the top segment is in 7 but not in 1. 
        List<Character> segmentsInSeven = displayNumberKey.get(Number.SEVEN);
        List<Character> segmentsInOne = displayNumberKey.get(Number.ONE);
        Character topSegment = 'z';
        for(Character c: segmentsInSeven){
            if(!segmentsInOne.contains(c)){
                topSegment =  c;
            }
        }
        segmentKey.put(Segment.TOP, topSegment);
        
        System.out.println("top segment: " + topSegment);
    }

    private void mapSimpleNumbers(Map<Number, List<Character>> displayNumberKey, Entry<List<String>, List<String>> entry) {
        for(String code: entry.getKey()) {
            switch(code.length()){
                case NUMBER_OF_SEGMENTS_IN_DIGIT_1:
                    displayNumberKey.put(Number.ONE, charsFor(code));
                    break;
                case NUMBER_OF_SEGMENTS_IN_DIGIT_4:
                    displayNumberKey.put(Number.FOUR, charsFor(code));
                    break;
                case NUMBER_OF_SEGMENTS_IN_DIGIT_7:
                    displayNumberKey.put(Number.SEVEN, charsFor(code));
                    break;
                case NUMBER_OF_SEGMENTS_IN_DIGIT_8:
                    displayNumberKey.put(Number.EIGHT, charsFor(code));
                    break;
            }
        }
    }

    private List<Character> charsFor(String code) {
        return code.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    }

    public void adapt() {
        System.out.println("number of unique segments: " + countUniqueSegments());
    }

    public int countUniqueSegments(){
        int uniqueSegments = 0;
        for(Entry<List<String>, List<String>> entry: dataRows.entrySet()){
            for(String outString: entry.getValue()){
                if (outString.length() == NUMBER_OF_SEGMENTS_IN_DIGIT_1
                 || outString.length() == NUMBER_OF_SEGMENTS_IN_DIGIT_4
                 || outString.length() == NUMBER_OF_SEGMENTS_IN_DIGIT_7
                 || outString.length() == NUMBER_OF_SEGMENTS_IN_DIGIT_8){
                    uniqueSegments ++;
                 }
            }
        }
        return uniqueSegments;
    }

    private void initialise() {
        dataRows = new LinkedHashMap<>();
        List<String> data = DataReader.readDataFromFile(DATA_FILE);
        // for each line in the data file: 
        //   split the line into input and output, delimited by |
        //   split the input into values, delimited by spaces   
        //   split the output into values, delimited by spaces.
        //   collect the input and output values in the dataRows map. 

        for(String line: data){
            String[] io = line.split("\\|");
            String[] inputs = io[0].trim().split(" ");
            String[] outputs = io[1].trim().split(" ");
            List<String> in = Arrays.asList(inputs);
            List<String> out = Arrays.asList(outputs);
            dataRows.put(in, out);
        }
        // dataRows.entrySet().stream().forEach(System.out::println);
    }

    

}
