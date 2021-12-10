package main.java.prob3;

import main.java.util.DataReader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PowerMeter {
    private final String DATA_FILE = "src/main/java/resources/prob3_input.txt";
    private List<String> data;
    private int lengthOfDatum;

    public PowerMeter() {
        data = DataReader.readDataFromFile(DATA_FILE);
        lengthOfDatum = data.get(0).length();
    }

    public void calculatePowerConsumption() {

        String gammaRate = calculateGammaRate();
        String thetaRate = invertBytes(gammaRate);
        Integer gamma = Integer.parseInt(gammaRate, 2);
        Integer theta = Integer.parseInt(thetaRate, 2);
        Integer power = gamma * theta;
        System.out.println("gamma: " + gamma + " theta: " + theta + " power " + power);
    }

    private String invertBytes(String gammaRate) {
        char[] chars = gammaRate.toCharArray();
        StringBuffer sb = new StringBuffer();
        for(int i=0; i< chars.length; i++){
            sb.append(invert(chars[i]));
        }
        return sb.toString();
    }

    private char invert(char c) {
        if(c == '1'){
            return '0';
        }
        else return '1';
    }

    private String calculateGammaRate() {
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i < lengthOfDatum; i++){
            buffer.append(getCommonestCharAtPosition(i, data));
        }
        return buffer.toString();
    }

    private char getCommonestCharAtPosition(int pos, List<String> list){
        int listSize = list.size();
        System.out.println("checking position " + pos + " for list size " + listSize);
        long numOnes =
        list.
        stream().
        map(s -> {return s.charAt(pos);}).
        filter(s -> s.equals('1')).
        count();
        char commonestChar = (numOnes >= (listSize / 2) ? '1' : '0');
        System.out.println("commonest character at position " + pos + " is: " + commonestChar);
        return commonestChar;
    }

    public int getLifeSupportRating() {
        int oxygenGeneratorRating = getOxygenGeneratorRating();
        int co2ScrubberRating = getCo2ScrubberRating();
        int lifeSupportRating = oxygenGeneratorRating * co2ScrubberRating ;
        System.out.println("o2 rating: " + oxygenGeneratorRating + " co2 rating: " + co2ScrubberRating + " life supp rating: " + lifeSupportRating);
        return lifeSupportRating;
    }

    private int getCo2ScrubberRating() {
        List<String> ratings = new ArrayList<String>(data);
        System.out.println("getting Co2 scrubber rating from data size " + ratings.size());
        for(int i = 0; i< lengthOfDatum; i++){
            if(ratings.size() == 1) {
                break;
            } else {
                char searchBit = invert(getCommonestCharAtPosition(i, ratings));
                ratings = filterList(i, searchBit, ratings);
            }
        }
        System.out.println("list size is " + ratings.size());
        System.out.println("CO2 scrubber rating is: " + ratings.get(0));
        return Integer.parseInt(ratings.get(0), 2);
    }

    private Integer getOxygenGeneratorRating() {
        List<String> ratings = new ArrayList<String>(data);
        System.out.println("getting oxygen generator rating from data size " + ratings.size());
        for(int i = 0; i< lengthOfDatum; i++){
            if(ratings.size() == 1) {
                break;
            } else {
                char searchBit = getCommonestCharAtPosition(i, ratings);
                ratings = filterList(i, searchBit, ratings);
            }
        }
        System.out.println("list size is " + ratings.size());
        Integer o2rating = Integer.parseInt(ratings.get(0), 2);
        System.out.println("O2 generator rating is: " + o2rating);
        return o2rating;
    }

    private List<String> filterList(int position, char searchBit, List<String> input){
        System.out.println("filtering list size " + input.size() + " keeping the " + searchBit);
        List<String> filteredList = new ArrayList<>();
        for(String datum: input){
            if(datum.charAt(position) == searchBit){
                filteredList.add(datum);
            }
        }
        System.out.println("list size after filtering is: " + filteredList.size());
        if(filteredList.size() == 0){
            filteredList.add(input.get(0));
        }
        System.out.println("first entry is: " + filteredList.get(0));
        System.out.println("last entry is: " + filteredList.get(filteredList.size() -1));
        return filteredList;
    }

    private List<String> filterRating(int i, List<String> ratings, boolean invert) {
        char searchBit = getCommonestCharAtPosition(i, ratings);
        if(invert) {
            searchBit = invert(searchBit);
        }
        char key = searchBit;
        System.out.println("filtering list size " + ratings.size() + " keeping the " + key);
        List<String> filtered = ratings.
        stream().
        filter(s -> s.charAt(i) == key).
        collect(Collectors.toCollection(ArrayList::new));
        System.out.println("list size after filtering is: " + filtered.size());
        System.out.println("first entry is: " + filtered.get(0));
        System.out.println("last entry is: " + filtered.get(filtered.size() -1));
        return filtered;
    }

    
}
