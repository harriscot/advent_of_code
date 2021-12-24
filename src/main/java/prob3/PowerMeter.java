package main.java.prob3;

import main.java.util.DataReader;

import java.util.List;

public class PowerMeter {
    private final String DATA_FILE = "src/main/java/resources/prob3_input.txt";
    private List<String> data;
    private int lengthOfDatum;

    public PowerMeter() {
        data = DataReader.readDataFromFile(DATA_FILE);
        lengthOfDatum = data.get(0).length();
    }

    public void calculatePowerConsumption() {

        String gammaRate = calculateGammaRate();
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

    private String calculateGammaRate() {
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i < lengthOfDatum; i++){
            buffer.append(getCommonestCharAtPosition(i, data));
        }
        return buffer.toString();
    }

    private char getCommonestCharAtPosition(int pos, List<String> list){
        return getCommonestCharAtPosition(pos, list, 1);
    }

    private char getCommonestCharAtPosition(int pos, List<String> list, int determinant){
        int listSize = list.size();
        System.out.println("checking position " + pos + " for list size " + listSize);
        long numOnes =
        list.
        stream().
        map(s -> {return s.charAt(pos);}).
        filter(s -> s.equals('1')).
        count();
        double halfSize = listSize / 2;
        char commonestChar;
        if(1 == determinant){
            commonestChar = (numOnes >= halfSize) ? '1' : '0';
        } else {
            commonestChar = (numOnes > halfSize) ? '1' : '0';
        }
        System.out.println("commonest character at position " + pos + " is: " + commonestChar);
        return commonestChar;
    }
    
}
