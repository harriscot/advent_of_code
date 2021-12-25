package main.java.prob4;

import java.util.ArrayList;
import java.util.List;

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

}
