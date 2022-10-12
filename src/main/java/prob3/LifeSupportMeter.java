package main.java.prob3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.java.util.DataReader;

public class LifeSupportMeter {

    private final String DATA_FILE = "src/main/java/resources/prob3_input.txt";
    private List<String> data;
    
    public void getLifeSupportRating() {
        int o2rating = getOxygenGeneratorRating();
        int co2rating = getCo2ScrubberRating();
        System.out.println("oxygen rating is: " + o2rating);
        System.out.println("co2 scrubber rating is: " + co2rating);
        System.out.println("life support rating is: " + o2rating * co2rating);
    }

    private Integer getCo2ScrubberRating() {
        initialise();
        List<String> filteredData = new ArrayList<>(data);
        int pos = 0;
        do {
            filteredData = filterCo2Data(pos, filteredData);
            pos ++;
        }
        while(filteredData.size() > 1);
        Integer co2Rating = Integer.parseInt(filteredData.get(0), 2);
        return co2Rating;
    }
    
    private List<String> filterCo2Data(int pos, List<String> list) {
        char leastCommonValueAtPosition = getLeastCommonValueAtPosition(pos, list);
        List<String> filteredList = list    
                                    .stream()
                                    .filter(s -> s.charAt(pos) == leastCommonValueAtPosition)  
                                                                  .collect(Collectors.toList());
        System.out.println("co2 data: at bit position " + pos + " we retained " + filteredList.size() + " entries.");
        return filteredList;
    }

    private char getLeastCommonValueAtPosition(int pos, List<String> list) {
        double listSize = list.size();
        long numOnes = list.
        stream().
        map(s -> {return s.charAt(pos);}).
        filter(s -> s.equals('1')).
        count();
        char leastCommon = numOnes < (listSize / 2) ? '1' : '0';
        System.out.println("For position " + pos + ", with list size " + listSize + 
                           " the number of ones is "+ numOnes + " and the least common character is " + leastCommon);
        return leastCommon;
    }

    private Integer getOxygenGeneratorRating() {
        initialise();
        List<String> filteredData = new ArrayList<>(data);
        int pos = 0;
        do {
            filteredData = filterData(pos, filteredData);
            pos ++;
        }
        while(filteredData.size() > 1);
        Integer oxygenGeneratorRating = Integer.parseInt(filteredData.get(0), 2);
        return oxygenGeneratorRating;
    }

    private List<String> filterData(int bitPosition, List<String> list) {
        char commonestValueAtPosition = getCommonestValueAtPosition(bitPosition, list);
        List<String> filteredList = list.stream()
        .filter(s -> s.charAt(bitPosition) == commonestValueAtPosition).collect(Collectors.toList());
        return filteredList;
    }

    private char getCommonestValueAtPosition(int bitPosition, List<String> list) {
        double listSize = list.size();
        long numOnes = list.
        stream().
        map(s -> {return s.charAt(bitPosition);}).
        filter(s -> s.equals('1')).
        count();
        char commonest = numOnes >= (listSize / 2) ? '1' : '0';
        return commonest;
    }

    public void initialise() {
        data = DataReader.readDataFromFile(DATA_FILE);
    }

}
