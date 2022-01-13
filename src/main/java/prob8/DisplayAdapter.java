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

    public void calculateTotalForAllRows() {
        Optional<Integer> totalForAllRows = dataRows.entrySet().
                                stream().
                                map(s -> {
                                    Map<Number, List<Character>> displayNumberKey = mapInputs(s.getKey());
                                    return calculateDisplayValue(s.getValue(), displayNumberKey);
                                }).reduce(Integer::sum);
        System.out.println(" the grand total is: " + totalForAllRows);
    }

    private Integer calculateDisplayValue(List<String> value, Map<Number, List<Character>> displayNumberKey) {
        String displayNumbers = value.
                                stream().
                                map(s -> {
                                    return displayNumberFor(s, displayNumberKey).getValue();
                                }).
                                collect(Collectors.joining());
        Integer displayValue = Integer.parseInt(displayNumbers);
        System.out.println("display numbers" + displayNumbers);
        return displayValue;
    }

    private Number displayNumberFor(String s, Map<Number, List<Character>> displayNumberKey) {
        List<Character> characterCode = charsFor(s);
        for( Entry<Number, List<Character>> keyEntry :displayNumberKey.entrySet()){
            if(keyEntry.getValue().size() == characterCode.size()
             && keyEntry.getValue().containsAll(characterCode)){
                return keyEntry.getKey();
            }
        }
        return null;
    }


    public Map<Number, List<Character>> mapInputs(List<String> list) {
        // The argument is a list of ten codes representing the digits 0-9, in a display.
        
        // the display number key lists each display number along with the list of characters that represent each number. 
        Map<Number, List<Character>> displayNumberKey = new HashMap<>();
        
        // the segment key defines the character used to display each segment of a number display.
        Map<Segment, Character> segmentKey = new HashMap<>();
        mapSimpleNumbers(displayNumberKey, list);
        List<Character> horizontalSegments = new ArrayList<>();
        
        // top segment is the one that is in 7 but not in 1.

        // Note that the order of these method calls is critical. Don't rearrange them!
        mapTopSegment(displayNumberKey, segmentKey);
        mapHorizontalSegments(list, horizontalSegments);
        mapCentreSegment(displayNumberKey, horizontalSegments, segmentKey);
        mapZero(list, displayNumberKey, segmentKey);
        mapBottomLeftSegment(displayNumberKey, horizontalSegments, segmentKey);
        mapNine(list, displayNumberKey, segmentKey);
        mapSix(list, displayNumberKey);
        mapTwo(list, displayNumberKey, segmentKey);
        mapThree(list, displayNumberKey, segmentKey);
        mapFive(list, displayNumberKey);
        displayNumberKey.entrySet().stream().forEach(s -> System.out.println(s.getKey().toString() + s.getValue()));
        return displayNumberKey;
    }


    private void mapFive(List<String> key, Map<Number, List<Character>> displayNumberKey) {
        // Five is the remaining 5 segment number.
        for(String code: key){
            if(code.length() == 5){
                if( charsFor(code).containsAll(displayNumberKey.get(Number.TWO))
                 || charsFor(code).containsAll(displayNumberKey.get(Number.THREE))){
                    continue;
                } else {
                    displayNumberKey.put(Number.FIVE, charsFor(code));
                    break;
                }
            }
        }
    }

    private void mapThree(List<String> key, Map<Number, List<Character>> displayNumberKey,
            Map<Segment, Character> segmentKey) {
        // Three is the five digit number with the same segments as one.
        for(String code: key){
            if(code.length() == 5){
                if(charsFor(code).containsAll(displayNumberKey.get(Number.ONE))){
                    displayNumberKey.put(Number.THREE, charsFor(code));
                }
            }
        }
    }

    private void mapTwo(List<String> key, Map<Number, List<Character>> displayNumberKey,
            Map<Segment, Character> segmentKey) {
        // Two is the five segment number with a bottom left segment.
        for(String code: key){
            if(code.length() == 5){
                if(charsFor(code).contains(segmentKey.get(Segment.LOWER_LEFT))){
                    displayNumberKey.put(Number.TWO, charsFor(code));
                }
            }
        }
    }

    private void mapSix(List<String> key, Map<Number, List<Character>> displayNumberKey) {
        // six is the remaining six segment number.
        for(String code: key){
            if(code.length() == 6){
                if( charsFor(code).containsAll(displayNumberKey.get(Number.ZERO))
                 || charsFor(code).containsAll(displayNumberKey.get(Number.NINE))){
                    continue;
                } else {
                    displayNumberKey.put(Number.SIX, charsFor(code));
                    break;
                }
            }
        }
    }

    private void mapBottomLeftSegment(Map<Number, List<Character>> displayNumberKey, List<Character> horizontalSegments,
            Map<Segment, Character> segmentKey) {
        // the bottom left segment is in zero but not in four, and is not horizontal.
        List<Character> segmentsInFour = displayNumberKey.get(Number.FOUR);
        List<Character> segmentsInZero = displayNumberKey.get(Number.ZERO);
        List<Character> bottomLeft = new ArrayList<>();
        bottomLeft.addAll(segmentsInZero);
        bottomLeft.removeAll(segmentsInFour);
        bottomLeft.removeAll(horizontalSegments);
        segmentKey.put(Segment.LOWER_LEFT, bottomLeft.get(0));
    }

    private void mapNine(List<String> key, Map<Number, List<Character>> displayNumberKey,
            Map<Segment, Character> segmentKey) {
        // Nine is the six segment number with no bottom left segment. 
        for(String code: key){
            if(code.length() == 6){
                if(!charsFor(code).contains(segmentKey.get(Segment.LOWER_LEFT))){
                    displayNumberKey.put(Number.NINE, charsFor(code));
                }
            }
        }
    }

    private void mapZero(List<String> key, Map<Number, List<Character>> displayNumberKey, Map<Segment, Character> segmentKey) {
        // zero is the six segment number with no middle.
        for(String code: key){
            if(code.length() == 6){
                if(!charsFor(code).contains(segmentKey.get(Segment.MIDDLE))){
                    displayNumberKey.put(Number.ZERO, charsFor(code));
                }
            }
        }
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

    private void mapSimpleNumbers(Map<Number, List<Character>> displayNumberKey, List<String> list) {
        for(String code: list) {
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
        return code.
                chars().
                mapToObj(e -> (char) e).
                collect(Collectors.toList());
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
