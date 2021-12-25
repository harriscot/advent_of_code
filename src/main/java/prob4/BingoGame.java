package main.java.prob4;

import java.util.ArrayList;
import java.util.List;

import main.java.util.DataReader;

public class BingoGame {
    private final String DATA_FILE = "src/main/java/resources/prob4_input.txt";
    private List<String> data;
    private List<BingoCard> cards;
    String[] calledNumbers;
    

    public BingoGame() {
        cards = new ArrayList<>();
        loadInputs();
        playGame();
    }
    
    private void playGame() {
        for(int i=0; i< calledNumbers.length; i++){
            callNumber(Integer.parseInt(calledNumbers[i]));
            if(cardHasWon()){
                break;
            }
        }
    }

    private boolean cardHasWon() {
        for(BingoCard card: cards){
            if(card.checkForWin()){
                return true;
            }
        }
        return false;
    }

    private void callNumber(Integer number){
        for(BingoCard card: cards){
            card.markNumber(number);
        }
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
            System.out.println("first line: " + card.display(card.getRow(0)));
        }
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
