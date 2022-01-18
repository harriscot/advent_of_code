package main.java.prob9;

public class Point {
    private int xPosition;
    private int yPosition;
    private int height;
    private boolean isLowestPointInBasin;
    private boolean searchDone;

    public Point(int xPosition, int yPosition, Integer height) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.height = height;
        isLowestPointInBasin = false;
        searchDone = false;
    }

    public String toString(){
        return "" + xPosition + ":" + yPosition + ":" + height;
    }

    boolean isLowestPointInBasin(){
        return this.isLowestPointInBasin;
    }

    void setIsLowestPointInBasin(boolean isLowest){
        this.isLowestPointInBasin = isLowest;
    }

    int getHeight() {
        return height;
    }

    int getXPosition() {
        return xPosition;
    }

    int getYPosition() {
        return yPosition;
    }

    public void setSearchDone(boolean b) {
        this.searchDone = b;
    }

    boolean getSearchDone() {
        return this.searchDone;
    }
}
