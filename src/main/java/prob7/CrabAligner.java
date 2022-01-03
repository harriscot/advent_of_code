package main.java.prob7;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.java.util.DataReader;

public class CrabAligner {
    private final String DATA_FILE = "src/main/java/resources/prob7_input.txt";
    List<Integer> initialCrabPositions;
    Map<Integer, Long> dataMap;
    TreeMap<Integer, Long> sortedDataMap;
    Map<Integer, Integer> fuelCosts;

    /*
     * We have a large set of numbers and need to align them all using the smallest amount of movement. 
     * Analyse the numbers - are they all unique? How are they distributed?
     * 
     * Could try moving to the mode, or failing that the mean. 
     * 
     */
    public void alignCrabs() {
        initialise();
        calculateMetadata();
        moveIntoAlignment();
    }

    private void moveIntoAlignment() {
        // Loop through the sorted map of values. Get each position and calculate the cost, storing it in another map.
        fuelCosts = new TreeMap<>();
        for(Map.Entry<Integer, Long> entry: sortedDataMap.entrySet()){
            Integer position = entry.getKey();
            fuelCosts.put(position, calculateCostAtPosition(position));
        }

        Entry<Integer, Integer> minFuel = Collections.min(fuelCosts.entrySet(), Comparator.comparing(Entry::getValue));
        System.out.println("minimum fuel is at position " + minFuel.getKey() + " for a cost of " + minFuel.getValue());
    }
    
    private Integer calculateCostAtPosition(Integer position){
        // Calculate how many places each crab has to move to get to required position.
        return initialCrabPositions.stream().map(s -> {
            return Math.abs(s - position);
        }).reduce(0, Integer::sum);
    }

    private void initialise() {
        List<String> data = DataReader.readDataFromFile(DATA_FILE);
        String [] initialCrabPos = data.get(0).split(",");

        initialCrabPositions = Stream.of(initialCrabPos).
                                        map(Integer::valueOf).
                                        collect(Collectors.toList());

        // group the data by value (position).
        dataMap = initialCrabPositions.
        stream().
        collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        sortedDataMap = new TreeMap<>(dataMap);
        // System.out.println(printPositions(sortedDataMap));

    }

    // calculate the average, median and mode in the data set.
    private void calculateMetadata(){
        OptionalDouble average = initialCrabPositions.stream().mapToDouble(a -> a).average();

        sortedDataMap.entrySet().iterator().next();
        
         Entry<Integer, Long> mode = calculateMode();
        
        long modeCount = sortedDataMap.entrySet().stream().filter(s -> s.getValue() == 8L).count();
        System.out.println("average is: " + average + " mode is " + mode.getKey() + " " + mode.getValue() + " there are " + modeCount + " modes.");
        // is there more than one mode in the data set? No. 
        // We know that the average is 473.609, the mode is 32 with 8 entries. 
        // the first thing to try is to move everything to the mode and submit that answer. 
        // If the answer is wrong we need to try a sweep, calculating the fuel cost of moving every crab to every point. 
        // this should create a new fuel cost map that should provide the answer to the problem. 
    }
    
    private Entry<Integer, Long> calculateMode() {
        Map.Entry<Integer, Long> highestEntry = sortedDataMap.entrySet().iterator().next();
        for(Map.Entry<Integer, Long> entry: sortedDataMap.entrySet()){
            if(entry.getValue() > highestEntry.getValue()){
                highestEntry = entry;
            }
        }
        return highestEntry;
    }

    private String printPositions(Map<Integer, Long> map) {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<Integer, Long> entry: map.entrySet()){
            builder.append("key: " + entry.getKey() + " count: " + entry.getValue() + "\n");
        }
        return builder.toString();
    }

}
