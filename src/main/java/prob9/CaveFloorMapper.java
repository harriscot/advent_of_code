package main.java.prob9;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.util.DataReader;

public class CaveFloorMapper {

    private final String DATA_FILE = "src/main/java/resources/prob9_input.txt";
    private List<List<Point>> caveFloorMap; // the inner integer is the X axis, the outer is the Y. 
    private Integer CAVE_FLOOR_WIDTH;
    private Integer CAVE_FLOOR_Y_AXIS_SIZE;
    
    public CaveFloorMapper(){
        initialise();
    }

    private void initialise() {
        List<String> data = DataReader.readDataFromFile(DATA_FILE);
        CAVE_FLOOR_Y_AXIS_SIZE = data.size();
        CAVE_FLOOR_WIDTH = data.get(0).length();
        caveFloorMap = new ArrayList<>();
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
        caveFloorMap.stream().forEach(System.out::println);
    }

    public void part1_findLowestPoints() {
        List<Point> lowPoints = new ArrayList<>();
        // Work through the entire floor map, columnn by column and row by row. 
        // For each point look up the heights of the adjacent points above and below, to left and to right. 
        // If this point is the lowest of the group set lowPoint to true.
        for(int yPosition = 0; yPosition < CAVE_FLOOR_Y_AXIS_SIZE; yPosition ++){
            for(int xPosition = 0; xPosition < CAVE_FLOOR_WIDTH; xPosition ++){
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
                        currentPoint.setIsLowestAdjacent(true);
                        lowPoints.add(currentPoint);
                    }
            }
        }
        System.out.println("low points: ");
        lowPoints.stream().forEach(System.out::println);
        System.out.println("number of low points: ");
        System.out.println(lowPoints.stream().count());
        // the risk level is the sum of all low points, each incremented by 1. 
        Optional<Integer> riskLevel = lowPoints.stream().
                            map(s -> {return s.getHeight() + 1;}).
                            reduce(Integer::sum);
        System.out.println("risk level :" + riskLevel);
    }

}
