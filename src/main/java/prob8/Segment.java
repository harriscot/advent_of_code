package main.java.prob8;

public enum Segment {
    TOP(true), 
    UPPER_LEFT(false), 
    UPPER_RIGHT(false),
    MIDDLE(true),
    LOWER_LEFT(false),
    LOWER_RIGHT(false),
    BOTTOM(true);

    private final boolean isHorizontal;
    
    public boolean isHorizontal(){
        return this.isHorizontal;
    }

    Segment(boolean isHorizontal){
        this.isHorizontal = isHorizontal;
    }

}
