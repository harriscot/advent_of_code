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
        if(isDiagonal()){
            addDiagonalPoints();
        }
        points.add(end);
    }

    public List<Point> getPoints() {
        return points;
    }

    private void addDiagonalPoints(){
        if(isDiagonalAscending()){
            for(int x= start.getXCoordinate() + 1, y = start.getYCoordinate() + 1; x < end.getXCoordinate(); x++, y++){
                points.add(new Point(x, y));
            }
        } else {
            for(int x= start.getXCoordinate() + 1, y = start.getYCoordinate() - 1; x < end.getXCoordinate(); x++, y--){
                points.add(new Point(x, y));
            }
        }
    }

    // the start should always be lower than the end.
    private void validate() {
        if(isVertical()){
            if(start.getYCoordinate() > end.getYCoordinate()){
                flip();
            }
        } else {
            if(start.getXCoordinate() > end.getXCoordinate()){
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
    boolean isDiagonal(){
        return isDiagonalAscending() || isDiagonalDescending();
    }
    boolean isDiagonalAscending(){
        // horizontal distance is difference between the x coordinates start and end.
        int horizontal = this.end.getXCoordinate() - this.start.getXCoordinate();
        // vertical distance is end - start y coordinates
        int vertical = end.getYCoordinate() - start.getYCoordinate();
        if(vertical == horizontal){
            return true;
        }   return false;
    }
    boolean isDiagonalDescending(){
        int horizontal = this.end.getXCoordinate() - this.start.getXCoordinate();
        // vertical distance is end - start y coordinates
        int vertical = end.getYCoordinate() - start.getYCoordinate();
        if(vertical == -horizontal){
            return true;
        }   return false;
    }

}
