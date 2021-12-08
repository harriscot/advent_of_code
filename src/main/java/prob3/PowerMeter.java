package main.java.prob3;

import main.java.util.DataReader;

import java.util.List;

public class PowerMeter {
    private final String DATA_FILE = "src/main/java/resources/prob3_input.txt";

    public void calculatePowerConsumption() {
        List<String> data = DataReader.readDataFromFile(DATA_FILE);
        System.out.println("number of rows: " + data.size());
        System.out.println("length of a row: " + data.get(0).length());
        
        Integer firstEntry = Integer.parseInt(data.get(2));
        String gammaRate = calculateGammaRate(data);
    }

    private String calculateGammaRate(List<String> data) {
        return null;
    }
}
