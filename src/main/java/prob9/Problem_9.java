package main.java.prob9;

import java.util.ArrayList;

public class Problem_9 {

    public static void main (String[] arguments){
        CaveFloorMapper mapper = new CaveFloorMapper();

        mapper.part1_findLowestPoints();
        mapper.part2_findLargestBasins();
        mapper.recursionExample(new ArrayList<Point>());
    }
    
}
