package main.java.hackerRank;

import java.util.ArrayList;
import java.util.List;

public class Divider {
    /*
     * HackerRank problem - 
     * For an input series of numbers, check if each is divisible by three or five. 
     * If divisible by 3, output "foo", if divisible by 5 output "bar". 
     * If divisible by both, output "foobar".
     * If divisible by neither, output the number itself.
     */

     public void checkNumbers(String numString) {
        List<Integer> ints = stringToIntList(numString);
        
        ints.stream().forEach(s -> { 
            if(!divisibleBy(3,s) && !divisibleBy(5,s)){
                System.out.print(s);
            } else {
                if(divisibleBy(3,s)){
                    System.out.print("foo");
                } 
                if(divisibleBy(5,s)) {
                    System.out.print("bar");
                } 
            }
            System.out.println("");
        });
     }

     private List<Integer> stringToIntList(String numString) {
        List<Integer> ints = new ArrayList<>();
        for(int i = 0; i < numString.length(); i++ ){
            Integer val = Character.getNumericValue(numString.charAt(i));
            ints.add(val);
        }
        return ints;
    }

    private boolean divisibleBy(int divisor, int number){
        return (number % divisor == 0);
    }

    public static void main(String... args){
        final String NUMBERS = "123456789";
        Divider divider = new Divider();
        divider.checkNumbers(NUMBERS);
    }
}
