package main.java.prob9;

public class Point {
    private int xPosition;
    private int yPosition;
    private int height;
    private boolean isLowestAdjacent;

    public Point(int xPosition, int yPosition, Integer height) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.height = height;
        isLowestAdjacent = false;
    }

    public String toString(){
        return "" + xPosition + ":" + yPosition + ":" + height;
    }

    boolean isLowestAdjacent(){
        return this.isLowestAdjacent;
    }

    void setIsLowestAdjacent(boolean isLowest){
        this.isLowestAdjacent = isLowest;
    }

    int getHeight() {
        return height;
    }
}
