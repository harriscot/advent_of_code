package main.java.prob6;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.java.util.DataReader;

public class FishCounter {

    private final String DATA_FILE = "src/main/java/resources/prob6_input.txt";
    List<Integer> initialBreedingState;
    List<LanternFish> shoal;
    Integer dayNumber;

    public void runBreedingSimulation() {
        initialise();
        System.out.println("number of fish in initial shoal = " + initialBreedingState.stream().count());

        breedForEightyDays();
    }
    
    private void breedForEightyDays() {
        for(dayNumber = 0; dayNumber < 80; dayNumber ++){
            moveOnOneDay();
        }
    }
    
    private void moveOnOneDay() {
        int numberOfFry = 0;
        
        for(LanternFish fish: shoal){
            if(fish.getDaysUntilGivesBirth() == 0){
                fish.setDaysUntilGivesBirth(6);
                numberOfFry ++;
            } else {
                int daysUntilBirth = fish.getDaysUntilGivesBirth() -1;
                fish.setDaysUntilGivesBirth(daysUntilBirth);
            }
        }

        for(int i=0; i< numberOfFry; i++){
            shoal.add(new LanternFish(8));
        }

        System.out.println("after " + (dayNumber + 1) + " days number of fish in shoal = " + shoal.stream().count());
    }

    private void initialise(){
        List<String> data = DataReader.readDataFromFile(DATA_FILE);
        String [] timesToBirth = data.get(0).split(",");
        shoal = new ArrayList<>();
        
        initialBreedingState = 
            Stream.of(timesToBirth).
            map(s -> Integer.parseInt(s)).
            collect(Collectors.toList());

        shoal = initialBreedingState.stream().map(s -> {
            return new LanternFish(s);
        }).collect(Collectors.toList());
    }
}
