package main.java.prob4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import main.java.util.DataReader;

public class BingoGame {
    private final String DATA_FILE = "src/main/java/resources/prob4_input.txt";
    private List<String> data;
    private List<BingoCard> cards;
    String[] calledNumbers;
    

    public BingoGame() {
        cards = new ArrayList<>();
        loadInputs();
    }
    
    void playGame() {
        for(int i=0; i< calledNumbers.length; i++){
            int calledNumber = Integer.parseInt(calledNumbers[i]);
            callNumber(calledNumber);
            checkCardForWin(calledNumber);
        }
    }

    private boolean checkCardForWin(int calledNumber) {
        for(BingoCard card: cards){
            if(card.checkForWin() && !card.hasWon()){
                card.calculateScore(calledNumber);
                card.setWon(true);
                if(lastCard()){
                    System.out.println("last card found");
                    return true;
                }
                
            }
        }
        return false;
    }

    private boolean lastCard() {
        int numberOfCards = cards.size();
        int numberOfCardsThatHaveWon = 0;
        
        for(BingoCard card: cards){
            if(card.hasWon()){
                numberOfCardsThatHaveWon ++;
            }
        }
        return numberOfCardsThatHaveWon == numberOfCards - 1;
    }

    private void callNumber(Integer number){
        cards.stream().forEach(s -> s.markNumber(number));
    }

    public void loadInputs() {
        data = DataReader.readDataFromFile(DATA_FILE);
        calledNumbers = data.get(0).split(",");
        // Stream.of(calledNumbers).forEach(System.out::println);
        setUpBingoCards();
        // checkBingoCards();
    }

    private void checkBingoCards(){
        System.out.println("number of cards is: " + cards.size());
        cards.stream().forEach(s -> { 
            System.out.println("first line: " + s.display(s.getRow(0)));
        });
    }

    private void setUpBingoCards() {
        BingoCard card = null;
        int cardNumber = 1;
        for(String line: data){
            if("".equals(line)){
                card = new BingoCard();
                card.setCardNumber(cardNumber);
                cards.add(card);
                cardNumber ++;
            }
            if(!line.contains(",") && line.length() > 0){
                card.addLine(line);
            }
        }
    }

}
