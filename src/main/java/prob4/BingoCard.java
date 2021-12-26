package main.java.prob4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BingoCard {
    
    List<List<BingoNumber>> card;

    public BingoCard() {
        card = new ArrayList<>();
    }

    void addLine(String line) {
        ArrayList<BingoNumber> row = new ArrayList<>();
        String[] numbers = line.trim().split("\\s+");

        Stream.of(numbers).forEach(number -> {
            BingoNumber bingoNumber = createNumber(number);
            row.add(bingoNumber);
        });

        card.add(row);
    }

    private BingoNumber createNumber(String string) {
        String numberString = string.trim();
        return new BingoNumber(Integer.parseInt(numberString));
    }

    public List<BingoNumber> getRow(int rowNumber){
        return card.get(rowNumber);
    }
  
    public List<BingoNumber> getColumn(int columnNumber){
        return card.stream().map(row -> row.get(columnNumber)).toList();
    }

    public void markNumber(Integer number){
        card.stream().forEach(row -> {
            row.stream().forEach(num -> {
                if(number == num.getNumber()){
                    num.setCalled(true);
                }
            });
        });
    }

    public boolean checkForWin() {
        if(checkRows()){
            return true;
        } else {
            return checkColumns();
        }
    }

    //TODO
    private boolean checkColumns() {
        int rowLength = getRow(0).size();
        for(int i=0; i< rowLength; i++){
            List<BingoNumber> column = getColumn(i);
            if(allNumbersCalled(column)){
                System.out.println("We have a winning column!!! " + display(column));
                return true;
            }
        }
        return false;
    }

    private boolean checkRows() {
        return card.stream().anyMatch(row -> {
            System.out.println("We have a winning row!!! " + display(row));
            return allNumbersCalled(row);
        });
    }

    private boolean allNumbersCalled(List<BingoNumber> numbers) {
        return numbers.stream().allMatch(s -> s.isCalled());
    }

    public String display(List<BingoNumber> row) {
        // build a string by concatenating all the numbers in a row.
        return row.stream().
            map(s -> { 
                return String.valueOf(s.getNumber());
            }).
            collect(Collectors.joining(" "));
    }

}
