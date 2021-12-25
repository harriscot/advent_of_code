package main.java.prob4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.java.util.DataReader;

public class BingoGame {
    private final String DATA_FILE = "src/main/java/resources/prob4_input.txt";
    private List<String> data;
    // private List<String> calledNumbers;
    private List<BingoCard> cards;
    String[] calledNumbers;
    

    public BingoGame() {
        cards = new ArrayList<>();
        loadInputs();
    }
    
    public void loadInputs() {
        data = DataReader.readDataFromFile(DATA_FILE);
        calledNumbers = data.get(0).split(",");
        for(int i = 0; i < calledNumbers.length; i++){
            System.out.println(calledNumbers[i]);
        }
        setUpBingoCards();
        checkBingoCards();
    }

    private void checkBingoCards(){
        System.out.println("number of cards is: " + cards.size());
        for(BingoCard card: cards){
            System.out.println("first line: " + display(card.getRow(0)));
        }
    }

    private String display(List<BingoNumber> row) {
        List<String> values = row.stream().
        map(s -> {return String.valueOf(s.getNumber());}).
        collect(Collectors.toList());
        String output = "";
        for(String value: values){
            output.concat(value);
        }
        return output;
    }

    private void setUpBingoCards() {
        BingoCard card = null;
        for(String line: data){
            if("".equals(line)){
                card = new BingoCard();
                cards.add(card);
            }
            if(!line.contains(",") && line.length() > 0){
                card.addLine(line);
            }
        }
    }

}
