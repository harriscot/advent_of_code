package main.java.prob8;

public enum Number {
    
    ONE(2),
    TWO(5),
    THREE(5),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(3),
    EIGHT(7),
    NINE(6),
    ZERO(6);

    private final int numberOfSegments;

    public int getNumberOfSegments(){
        return numberOfSegments;
    }

    Number(int numberOfSegments){
        this.numberOfSegments = numberOfSegments;
    }
}
