package main.java.prob8;

public enum Number {
    
    ONE(2, "1"),
    TWO(5, "2"),
    THREE(5, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6,"6"),
    SEVEN(3,"7"),
    EIGHT(7,"8"),
    NINE(6,"9"),
    ZERO(6,"0");

    private final int numberOfSegments;
    private final String value;

    public int getNumberOfSegments(){
        return numberOfSegments;
    }

    public String getValue(){
        return value;
    }

    Number(int numberOfSegments, String value){
        this.numberOfSegments = numberOfSegments;
        this.value = value;
    }
}
