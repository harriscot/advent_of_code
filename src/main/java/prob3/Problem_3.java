package main.java.prob3;

public class Problem_3 {

    public static void main(String... args){
        PowerMeter powerMeter = new PowerMeter();
        powerMeter.calculatePowerConsumption(); //4147524
        LifeSupportMeter lifeSupportMeter = new LifeSupportMeter();
        lifeSupportMeter.getLifeSupportRating(); // o2 rating 1427, co2 rating 2502, life support rating 3570354
    }
}
