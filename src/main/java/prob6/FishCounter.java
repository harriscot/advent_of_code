package main.java.prob6;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.java.util.DataReader;

public class FishCounter {

    private final String DATA_FILE = "src/main/java/resources/prob6_input.txt";
    
    // The shoal gets too big if represented by a linear list of individual fish. 
    // instead represent the shoal by a Map that contains the number of days to birth
    // as the key and the number of fish in that group as the value.
    Map<Integer, Long> shoal;
    Integer dayNumber;

    public void runBreedingSimulation() {
        initialise();
        System.out.println("number of fish in initial shoal = " + countFish());

        breedForDays();
    }
    
    private void breedForDays() {
        for(dayNumber = 0; dayNumber < 256; dayNumber ++){
            moveOnOneDay();
        }
    }
    
    private void moveOnOneDay() {
        Map<Integer, Long> shoalCopy = new TreeMap<>(); 
        
        for(Map.Entry<Integer, Long> entry: shoal.entrySet()){
            // If any fish are at zero days they produce a new fish with eight days until it breeds.
            if(entry.getKey() == 0){
                shoalCopy.put(8, entry.getValue());
            } else {
                // all fish are one day closer to giving birth.
                int key = entry.getKey();
                Long value = entry.getValue();
                shoalCopy.put(key - 1, value);
            }
        }
        // Add the fish that just gave birth to those at position 6. 
        if(shoalCopy.get(8) != null && shoalCopy.get(8) > 0L){
            long numFish = shoalCopy.get(8);
            if(shoalCopy.get(6) != null){
                numFish += shoalCopy.get(6);
            }
            shoalCopy.remove(6);
            shoalCopy.put(6, numFish);
        }
        shoal.clear();
        shoal.putAll(shoalCopy); 
        
        System.out.println("after " + (dayNumber + 1) + " days number of fish in shoal = " + countFish());
        System.out.println("shoal entries: " + printShoal());
    }

    private String printShoal() {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<Integer, Long> entry: shoal.entrySet()){
            builder.append(" " + entry.getKey() + " " + entry.getValue());
        }
        return builder.toString();
    }

    private Long countFish() {
        return shoal.
                entrySet().
                stream().
                map(s -> s.getValue()).
                reduce(0L, Long::sum);
    }

    private void initialise(){
        List<String> data = DataReader.readDataFromFile(DATA_FILE);
        String [] timesToBirth = data.get(0).split(",");
        shoal = new TreeMap<>();
        
        // process all the fish in the initial array, grouping them by days to birth and counting the number of fish in each group.
        shoal = Stream.of(timesToBirth).
        map(s -> Integer.parseInt(s)).
        collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    }
}
