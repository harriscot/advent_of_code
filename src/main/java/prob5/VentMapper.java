package main.java.prob5;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.util.DataReader;

public class VentMapper {

    private final String DATA_FILE = "src/main/java/resources/prob5_input.txt";
    private List<String> data;
    private List<Line> lines;
    private List<Map<Point, Integer>> seaFloorMap;

    public VentMapper() {
        lines = new ArrayList<>();
    }
    
    public void mapVents() {
        readInputData();
        createMap();
        plotDataOnMap();
        calculateNumberOfPointsWithMoreThanOneVent();
    }
    
    public void readInputData() {
        data = DataReader.readDataFromFile(DATA_FILE);
        data.stream().forEach(s -> {
            Line line = lineFor(s);
            if(line.isVertical() || line.isHorizontal()){
                lines.add(lineFor(s));
            }
        });
    }
   
    private void createMap() {
        int largestX = 0;
        int largestY = 0;
        for(Line line: lines){
            Point end = line.getEnd();
            if(end.getXCoordinate() > largestX){
                largestX = end.getXCoordinate();
            }
            if(end.getYCoordinate() > largestY){
                largestY = end.getYCoordinate();
            }
        }
        System.out.println("largest x is " + largestX + ", largest y is " + largestY);
        seaFloorMap = new ArrayList<>(largestY + 1);
        for(int y=0; y<= largestY; y++){
            LinkedHashMap<Point, Integer> row = new LinkedHashMap<>(largestX +1);
            for(int x=0; x<=largestX; x++){
                row.put(new Point(x,y),0);
            }
            seaFloorMap.add(row);
        }
        System.out.println("sea floor map has " + seaFloorMap.size() + " rows");
    }

    private void plotDataOnMap() {
        for(Line line: lines){
            for(Point point: line.getPoints()){
                markPointOnMap(point);
            }
        }
    }

    private void calculateNumberOfPointsWithMoreThanOneVent() {
        int totalCrossPoints = 0;
        for(Map<Point,Integer> row: seaFloorMap){
            for(Entry<Point, Integer> coordinate: row.entrySet()){
                if(coordinate.getValue() >= 2){
                    totalCrossPoints ++;
                }
            }
        }
        System.out.println("total cross points = " + totalCrossPoints);
    }

    private void markPointOnMap(Point point) {
       Map<Point, Integer> row = seaFloorMap.get(point.getYCoordinate());
        for(Entry<Point, Integer> coordinate: row.entrySet()){
            if(coordinate.getKey().getXCoordinate() == point.getXCoordinate() 
            && coordinate.getKey().getYCoordinate() == point.getYCoordinate()){
                coordinate.setValue(coordinate.getValue() + 1);
            }
        }
    }

    private Line lineFor(String coordinateString) {
        Point start;
        Point end; 
        // substitute " -> " for ","
        coordinateString = coordinateString.replace(" -> ", ",");
        coordinateString = coordinateString.trim();
        String[] lineCoordinates = coordinateString.split(",");
        start = new Point(Integer.parseInt(lineCoordinates[0]), Integer.parseInt(lineCoordinates[1]));
        end = new Point(Integer.parseInt(lineCoordinates[2]), Integer.parseInt(lineCoordinates[3]));
        Line line = new Line(start, end);
        return line;
    }
}
