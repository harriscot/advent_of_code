package main.java.prob2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Navigator {

    public void calculatePosition() {
        LinkedHashMap<String, Integer> movements = readPositionData();
        for(Map.Entry<String, Integer> entry: movements.entrySet()){
            System.out.println(entry.getKey() + " / " + entry.getValue());
        }
    }

    void calculatePositionWithAim() {
        ArrayList<String> data = readData();
        int aim = 0;
        int distance = 0;
        int depth = 0;

        for(String line: data){
            String[] tokens = line.split(" ");
            int value = Integer.parseInt(tokens[1]);
            switch(tokens[0]){
                case "down":
                    aim += value;
                    break;
                case "up":
                    aim -= value;
                    break;
                case "forward":
                    distance += value;
                    depth += value * aim;
            }
        }
        System.out.println("aim = " + aim);
        System.out.println("depth = " + depth);
        System.out.println("distance = " + distance);
        System.out.println("final location = " + depth * distance);
    }

    void calculatePositionWithStreams() {
        ArrayList<String> data = readData();
        int distance = sumMovements(data, "forward");
        int depthIncrease = sumMovements(data, "down");
        int depthDecrease = sumMovements(data, "up");
        int depth = depthIncrease - depthDecrease;
        int totalMovement = distance * depth;
        System.out.println(distance);
        System.out.println(depth);
        System.out.println(totalMovement);
    }

    private int sumMovements(ArrayList<String> data, String direction) {
        return data.
                stream().
                filter(s -> s.startsWith(direction)).
                map((s) -> {
                    String[] parts = s.split(" ");
                    return Integer.parseInt(parts[1]);
                }).
                reduce(0, Integer::sum);
    }

    private ArrayList<String> readData(){
        Path path = Paths.get("src/main/java/resources/prob2/prob2_input.txt");
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader;
        String line = "";
        try {
            reader = Files.newBufferedReader(path);
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return lines;
    }

    private LinkedHashMap<String, Integer> readPositionData() {
        Path path = Paths.get("src/main/java/resources/prob2/prob2_input.txt");
        LinkedHashMap<String, Integer> movements = new LinkedHashMap<>();
        BufferedReader reader;
        String line = "";
        int depth = 0;
        int distance = 0;
        try {
            reader = Files.newBufferedReader(path);
            while((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                switch(tokens[0]){
                    case "down":
                        depth += Integer.parseInt(tokens[1]);
                        break;
                    case "up":
                        depth -= Integer.parseInt(tokens[1]);
                        break;
                    case "forward":
                        distance += Integer.parseInt(tokens[1]);
                }
                movements.put("depth", depth);
                movements.put("distance", distance);
                movements.put("product", depth * distance);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return movements;
    }
}
