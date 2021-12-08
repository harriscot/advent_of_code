package main.java.prob3;

import main.java.util.DataReader;

import java.util.List;

public class PowerMeter {
    private final String DATA_FILE = "src/main/java/resources/prob3_input.txt";

    public void calculatePowerConsumption() {
        List<String> data = DataReader.readDataFromFile(DATA_FILE);

        String gammaRate = calculateGammaRate(data);
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

    private String calculateGammaRate(List<String> data) {
        StringBuffer buffer = new StringBuffer();
        int rowLength = data.get(0).length();
        for(int i=0; i < rowLength; i++){
            buffer.append(getCharAtPosition(i, data));
        }
        return buffer.toString();
    }

    private char getCharAtPosition(int pos, List<String> data){
        long numOnes =
        data.
        stream().
        map(s -> {return s.charAt(pos);}).
        filter(s -> s.equals('1')).
        count();
        System.out.println("number of ones at position " + pos + " is: " + numOnes);
        return(numOnes > (data.size() / 2) ? '1' : '0');
    }
}
