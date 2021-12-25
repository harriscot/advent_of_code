package main.java.prob4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BingoCard {
    
    List<List<BingoNumber>> card;

    public BingoCard() {
        card = new ArrayList<>();
    }

    public void addLine(String line) {
        ArrayList<BingoNumber> row = new ArrayList<>();
        String[] numbers = line.trim().split("\\s+");
        for(int i=0; i< numbers.length; i++){
            try{
            BingoNumber bingoNumber = createNumber(numbers[i]);
            row.add(bingoNumber);
            } catch (NumberFormatException e){
                System.out.println("exception in number " + numbers[i] + " " + e);
            }
        }
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
        List<BingoNumber> column = new ArrayList<>();
        for(List<BingoNumber> row: card){
            column.add(row.get(columnNumber));
        }
        return column;
    }

    public void markNumber(Integer number){
        for(List<BingoNumber> row: card){
            for(BingoNumber num: row){
                if(number == num.getNumber()){
                    num.setCalled(true);
                }
            }
        }
    }

    public boolean checkForWin() {
        if(checkRows()){
            return true;
        } else {
            return checkColumns();
        }
    }

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
        for(List<BingoNumber> row: card){
            if(allNumbersCalled(row)){
                System.out.println("We have a winning row!!! " + display(row));
                return true;
            }
        }
        return false;
    }

    private boolean allNumbersCalled(List<BingoNumber> numbers) {
        for(BingoNumber number: numbers){
            if(!number.isCalled()){
                return false;
            }
        } 
        return true;
    }

    public String display(List<BingoNumber> row) {
        List<String> values = row.stream().
        map(s -> {return String.valueOf(s.getNumber());}).
        collect(Collectors.toList());
        String output = "";
        for(String value: values){
            output = output.concat(value).concat(" ");
        }
        return output;
    }

}
