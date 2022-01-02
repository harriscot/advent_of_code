package main.java.prob6;

import java.util.List;

import main.java.util.DataReader;

public class FishCounter {

    private final String DATA_FILE = "main/java/resources/prob6_input.txt";
    int[] shoal;
    Integer dayNumber;

    public void runBreedingSimulation() {
        initialise();
        System.out.println("number of fish in initial shoal = " + countFish());

        breedForEightyDays();
    }

    private int countFish(){
        int count = 0;
        for(int i=0; i< shoal.length; i++){
            if(shoal[i] == -1){
                break;
            } else {
                count ++;
            }
        }
        return count;
    }
    
    private void breedForEightyDays() {
        for(dayNumber = 0; dayNumber < 256; dayNumber ++){
            moveOnOneDay();
        }
    }
    
    private void moveOnOneDay() {
        int numberOfFry = 0;
        
        for(int i=0; i<shoal.length; i++){
            if(shoal[i] == -1){
                break;
            } else {
                if(shoal[i] == 0){
                    shoal[i] = 6;
                    numberOfFry ++;
                } else {
                    int daysUntilBirth = shoal[i] -1;
                    shoal[i] = daysUntilBirth;
                }
            }
        }

        for(int i=0; i< numberOfFry; i++){
            int lastFish = countFish();
            shoal[lastFish] = 8;
            shoal[lastFish + 1] = -1;
        }

        System.out.println("after " + (dayNumber + 1) + " days number of fish in shoal = " + countFish());
    }

    private void initialise(){
        List<String> data = DataReader.readDataFromFile(DATA_FILE);
        String [] timesToBirth = data.get(0).split(",");
        shoal = new int[100000000];
        
        for(int i=0; i< timesToBirth.length; i++){
            shoal[i] = Integer.parseInt(timesToBirth[i]);
        }
        shoal[timesToBirth.length] = -1;
    }
}
