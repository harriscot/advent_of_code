package main.java.prob9;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.Map.Entry;

import main.java.util.DataReader;

public class CaveFloorMapper {

    private static final int MAX_HEIGHT = 9;
    private final String DATA_FILE = "src/main/java/resources/prob9_input.txt";
    private List<List<Point>> caveFloorMap; // the inner integer is the X axis, the outer is the Y. 
    private List<Point> lowPoints;
    private Map<Point, Integer> basinList;
    private Integer CAVE_FLOOR_WIDTH;
    private Integer CAVE_FLOOR_Y_AXIS_SIZE;
    
    public CaveFloorMapper(){
        initialise();
    }

    public void part2_findLargestBasins() {
        // Build up a list of all the basins with the number of points in each one. 
        lowPoints.forEach(point -> measureBasinArea(point));

        // Find the three largest basins and multiply their sizes together.
        // Set<Entry<Point, Integer>> basinSizes = basinList.entrySet();

        List<Integer> values = new ArrayList<Integer>(basinList.values());
        values.sort((a, b) -> Integer.compare(b, a));
        // values.stream().forEach(System.out::println);
        System.out.println("sum of biggest three basins: " +values.stream().limit(3L).reduce(0, Integer::sum));
        System.out.println("product of biggest three basins: " + values.get(0) * values.get(1) * values.get(2));
    }
    
    private void measureBasinArea(Point basinCentre) {
        // A low point must be in the middle of a basin. 
        // For each low point work out left and right and gradually expand until all the points
        // in the basin are found. The edge of the basin is at level 9. 

        Set<Point> basinPoints = new HashSet<>();
        Point searchPoint = basinCentre;
        basinPoints.add(searchPoint);
        while(searchPoint != null){
            basinPoints = searchForAdjacentPoints(searchPoint, basinPoints);
            searchPoint = findUnsearchedPoint(basinPoints);
        }

        // System.out.println("basin centre: " + basinCentre.toString() + " size: " + basinPoints.size());
        basinList.put(basinCentre, basinPoints.size());
    }

    private Point findUnsearchedPoint(Set<Point> basinPoints) {
        Iterator<Point> it = basinPoints.iterator();
        Point point;
        while(it.hasNext()){
            point = it.next();
            if(!point.getSearchDone()){
                return point;
            }
        }
        return null;
    }

    private Set<Point> searchForAdjacentPoints(Point searchPoint, Set<Point> basinPoints) {
        int xPosition = searchPoint.getXPosition();
        int yPosition = searchPoint.getYPosition();
        if(xPosition > 0){
            Point pointToLeft = caveFloorMap.get(yPosition).get(xPosition - 1);
            if(pointToLeft.getHeight() < MAX_HEIGHT){
                basinPoints.add(pointToLeft);
            }
        }
        if(xPosition < CAVE_FLOOR_WIDTH - 1){
            Point pointToRight = caveFloorMap.get(yPosition).get(xPosition + 1);
            if(pointToRight.getHeight() < MAX_HEIGHT){
                basinPoints.add(pointToRight);
            }
        }
        if(yPosition > 0){
            Point pointAbove = caveFloorMap.get(yPosition - 1).get(xPosition);
            if(pointAbove.getHeight() < MAX_HEIGHT){
                basinPoints.add(pointAbove);
            }
        }
        if(yPosition < CAVE_FLOOR_Y_AXIS_SIZE - 1){
            Point pointBelow = caveFloorMap.get(yPosition + 1).get(xPosition);
            if(pointBelow.getHeight() < MAX_HEIGHT){
                basinPoints.add(pointBelow);
            }
        }
        searchPoint.setSearchDone(true);
        return basinPoints;
    }

    private void initialise() {
        List<String> data = DataReader.readDataFromFile(DATA_FILE);
        buildCaveFloorMap(data);
        // caveFloorMap.stream().forEach(System.out::println);
    }

    private void buildCaveFloorMap(List<String> data) {
        CAVE_FLOOR_Y_AXIS_SIZE = data.size();
        CAVE_FLOOR_WIDTH = data.get(0).length();
        caveFloorMap = new ArrayList<>();
        basinList = new HashMap<>();
        // The data file represents a map of the cave floor. 
        // Each number represents a height with 0 being lowest and 9 highest. 
        // Each point has a unique x and y position - we shall use negative Y numbering so the 
        // top left point of the map is 0, 0. 

        for(int yPosition = 0; yPosition < data.size(); yPosition ++){
            ArrayList<Point> xRow = new ArrayList<>();
            // transform the input file row into a list of Strings.
            String[] rowHeights = data.get(yPosition).split("(?<=.)");
            // convert the list of integers into a list of points.
            for(int xPosition = 0; xPosition < rowHeights.length; xPosition++){
                Integer height = Integer.parseInt(rowHeights[xPosition]);
                Point point = new Point(xPosition, yPosition, height);
                xRow.add(point);
            }
            caveFloorMap.add(xRow);
        }
    }

    public void part1_findLowestPoints() {
        lowPoints = new ArrayList<>();
        // Work through the entire floor map, columnn by column and row by row. 
        // For each point look up the heights of the adjacent points above and below, to left and to right. 
        // If this point is the lowest of the group set lowPoint to true.
        for(int yPosition = 0; yPosition < CAVE_FLOOR_Y_AXIS_SIZE; yPosition ++){
            for(int xPosition = 0; xPosition < CAVE_FLOOR_WIDTH; xPosition ++){
                checkIfLowestPointInBasin(yPosition, xPosition);
            }
        }
        System.out.println("number of low points: ");
        System.out.println(lowPoints.stream().count());
        // the risk level is the sum of all low points, each incremented by 1. 
        Optional<Integer> riskLevel = lowPoints.stream().
                            map(s -> {return s.getHeight() + 1;}).
                            reduce(Integer::sum);
        System.out.println("risk level :" + riskLevel);
    }

    private void checkIfLowestPointInBasin(int yPosition, int xPosition) {
        Point currentPoint = caveFloorMap.get(yPosition).get(xPosition);
        int heightToLeft = 10, 
            heightToRight = 10, 
            heightAbove = 10, 
            heightBelow = 10;
        
        if(xPosition > 0){
            heightToLeft = caveFloorMap.get(yPosition).get(xPosition - 1).getHeight();
        }
        if(xPosition < CAVE_FLOOR_WIDTH - 1){
            heightToRight = caveFloorMap.get(yPosition).get(xPosition + 1).getHeight();
        }
        if(yPosition > 0){
            heightAbove = caveFloorMap.get(yPosition - 1).get(xPosition).getHeight();
        }
        if(yPosition < CAVE_FLOOR_Y_AXIS_SIZE - 1){
            heightBelow = caveFloorMap.get(yPosition + 1).get(xPosition).getHeight();
        }
        if   ( currentPoint.getHeight() < heightToLeft
            && currentPoint.getHeight() < heightToRight
            && currentPoint.getHeight() < heightAbove
            && currentPoint.getHeight() < heightBelow ){
                currentPoint.setIsLowestPointInBasin(true);
                lowPoints.add(currentPoint);
            }
    }

    List<Point> recursionExample(List<Point> pointList){
        if(pointList.size() < 10){
            pointList.add(new Point(1,1,20));
            // System.out.println("array size: " + pointList.size());
            recursionExample(pointList);
        }
        return pointList;
    }
}
