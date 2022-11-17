package main.java.countDuplicates;

import java.util.HashMap;
import java.util.List;

/*
 * HackerRank problem used in AutoRek tech check.
 * Given an input array of integers, count the number of duplicates. 
 * Return the number of integer values that are duplicated. 
 */
public class CountDuplicates {
    public static void main(String[] args) {
        CountDuplicates dups = new CountDuplicates();
        List<Integer> data = List.of(1, 2, 2, 4, 2, 6, 7, 1, 9, 10);
        dups.countDuplicates(data);
    }

    private void countDuplicates(List<Integer> data) {        
        HashMap<Integer, Integer> counts = mapValues(data);
        System.out.println(counts.toString());
        System.out.println(countDuplicatedNumbers(counts));
    }

    private int countDuplicatedNumbers(HashMap<Integer, Integer> counts) {
        int duplicatedNumbers = 0;
        for(int count: counts.values()){
            if(count > 1){
                duplicatedNumbers ++;
            }
        }
        return duplicatedNumbers;
    }

    private HashMap<Integer, Integer> mapValues(List<Integer> data) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (Integer number : data) {
            if (counts.containsKey(number)) {
                int count = counts.get(number);
                counts.put(number, ++count);
            } else {
                counts.put(number, 1);
            }
        }
        return counts;
    }

}
