package main.java.prob5;

public class Line {
    private Point start; 
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        validate();
    }

    // the start should always be lower than the end.
    private void validate() {
        if(isHorizontal()){
            if(start.getXCoordinate() > end.getXCoordinate()){
                flip();
            }
        }
        if(isVertical()){
            if(start.getYCoordinate() > end.getYCoordinate()){
                flip();
            }
        }
    }

    public Point getEnd(){
        return this.end;
    }
    // swap start and end points
    private void flip() {
        Point temp = start;
        start = end;
        end = temp;
    }

    boolean isVertical(){
        return this.start.getXCoordinate() == this.end.getXCoordinate();
    }
    boolean isHorizontal() {
        return this.start.getYCoordinate() == this.end.getYCoordinate();
    }

}
