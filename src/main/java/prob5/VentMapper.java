package main.java.prob5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import main.java.util.DataReader;

public class VentMapper {

    private final String DATA_FILE = "src/main/java/resources/prob5_input.txt";
    private List<String> data;
    private List<Line> lines;
    private List<Map<Point, Integer>> seaFloorMap;
    
    public void mapVents() {
        readInputData();
        createMap();
        // plotDataOnMap();
        // calculateNumberOfPointsWithMoreThanOneVent();
    }

    private void createMap() {
        // lines.stream().map(s -> {
        //     s.getEnd().
        // });
        // lines.stream().max(Comparator.comparing(s -> {s.getEnd().get}));
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
        seaFloorMap = new ArrayList<>(largestX);
        for(int i=0; i< largestX; i++){
            LinkedHashMap xRow = new LinkedHashMap(largestY);
            for(int j=0; j<largestY; j++){
                xRow.put(new Point(i,j),0);
            }
            seaFloorMap.add(xRow);
        }
        
    }

    public void readInputData() {
        data = DataReader.readDataFromFile(DATA_FILE);
        data.stream().forEach(s -> {
            lines.add(lineFor(s));
        });
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
        return new Line(start, end);
    }
}
