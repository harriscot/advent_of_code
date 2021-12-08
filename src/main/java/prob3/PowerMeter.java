package main.java.prob3;

import main.java.util.DataReader;

import java.util.List;

public class PowerMeter {
    private final String DATA_FILE = "src/main/java/resources/prob3_input.txt";

    public void calculatePowerConsumption() {
        List<String> data = DataReader.readDataFromFile(DATA_FILE);
        System.out.println("number of rows: " + data.size());
        System.out.println("length of a row: " + data.get(0).length());
        System.out.println("first entry: " + data.get(0));

//        Integer firstEntry = Integer.parseInt(data.get(2));
        Integer secondEntry = Integer.parseUnsignedInt(data.get(0), 2); // convert a binary string into an integer.
        String number = Integer.toBinaryString(secondEntry);
//        String gammaRate = calculateGammaRate(data);
        System.out.println("first entry is: " + secondEntry);
        System.out.println("first entry as string: " + number);
    }

    private String calculateGammaRate(List<String> data) {
        return null;
    }
}
