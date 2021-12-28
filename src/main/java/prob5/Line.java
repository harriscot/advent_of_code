package main.java.prob5;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private Point start; 
    private Point end;
    private List<Point> points;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        points = new ArrayList<>();
        validate();
        calculatePoints();
    }

    private void calculatePoints() {
        points = new ArrayList<>();
        points.add(start);
        if(isHorizontal()){
            for(int x = start.getXCoordinate() + 1; x< end.getXCoordinate(); x++){
                points.add(new Point(x, start.getYCoordinate()));
            }
        }
        if(isVertical()){
            for(int y = start.getYCoordinate() + 1; y < end.getYCoordinate(); y++){
                points.add(new Point(start.getXCoordinate(), y));
            }
        }
        points.add(end);
    }

    public List<Point> getPoints() {
        return points;
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
