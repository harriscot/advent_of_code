package main.java.hackerRank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BracketMatcher {
    
    enum BracketType {
        SQUARE('[',']'),
        PARENS('(',')'),
        CURLY('{','}');

        private final Character open; 
        private final Character close;

        Character open() {
            return this.open;
        }
        Character close() {
            return this.close;
        }
        
        BracketType(Character open, Character close){
            this.open = open;
            this.close = close;
        }
    }

    /*
     * Analyse the input string. The brackets must match according to these criteria:
     * For any given bracket type the first must not be a closing bracket. 
     * Each opening bracket must be matched by a closing bracket. 
     * The number of opening and closing brackets for a given type must be equal. 
     * Orphan closing brackets are not permitted anywhere in the string, 
     * i.e. each closing bracket must be preceded by an opening bracket of the same type. 
     * 
     */
    private void matchBrackets(String input) {
        System.out.println("input: " + input);
        List<Character> charList = convertStringToCharacterList(input);
        System.out.println(charList);
        checkFirstBracketIsOpen(charList);
        checkNumbersOfBracketsMatch(charList);
        checkBracketSequence(charList);
        mapBrackets(charList);
    }
    
    private void checkFirstBracketIsOpen(List<Character> charList) {
        boolean square = isFirstBracketOpenForType(BracketType.SQUARE, charList);
        boolean parens = isFirstBracketOpenForType(BracketType.PARENS, charList);
        boolean curly = isFirstBracketOpenForType(BracketType.CURLY, charList);

        System.out.println("Is first bracket opening for type square? " + square + " parentheses? " + parens + " curly? " + curly);
    }

    private boolean checkNumbersOfBracketsMatch(List<Character> chars){
        System.out.println("for type square, number of brackets match: " + checkBracketCountsMatch(chars, BracketType.SQUARE));
        System.out.println("for type parens, number of brackets match: " + checkBracketCountsMatch(chars, BracketType.PARENS));
        System.out.println("for type curly, number of brackets match: " + checkBracketCountsMatch(chars, BracketType.CURLY));
        return    checkBracketCountsMatch(chars, BracketType.SQUARE)
               && checkBracketCountsMatch(chars, BracketType.PARENS)
               && checkBracketCountsMatch(chars, BracketType.CURLY);
    }

    private boolean checkBracketCountsMatch(List<Character> chars, BracketType type){
        int openCount= 0;
        int closeCount = 0;
        for(Character c: chars){
            if(isSameTypeOfBracket(type, c)){
                if(c.equals(type.open())){
                    openCount ++;
                }
                if(c.equals(type.close())){
                    closeCount ++;
                }
            }
        }
        return openCount == closeCount;
    }

    private boolean isFirstBracketOpenForType(BracketType type, List<Character> charList) {
        for(Character c: charList){
            if(isBracket(c) && isSameTypeOfBracket(type, c)) {
                if(isOpeningBracket(c) ){
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /*
     * Check that the brackets are paired up properly. At any point in the sequence the number of 
     * closing brackets for a given type must not be > the number of opening brackets. 
     * 
     */
    private void checkBracketSequence(List<Character> chars) {
        boolean squareBracketSequenceOk = checkBracketSequence(chars, BracketType.SQUARE);
        boolean parenthesesSequenceOk = checkBracketSequence(chars, BracketType.PARENS);
        boolean curlyBracketSequenceOk = checkBracketSequence(chars, BracketType.CURLY);
        
        System.out.println("square bracket sequence is ok: " + squareBracketSequenceOk);
        System.out.println("parentheses sequence is ok: " + parenthesesSequenceOk);
        System.out.println("curly bracket sequence is ok: " + curlyBracketSequenceOk);
    }
        
    private boolean checkBracketSequence(List<Character> chars, BracketType type) {
        int openCount = 0;
        int closeCount = 0;
        for(Character c: chars){
            if(isSameTypeOfBracket(type, c)){
                if(c.equals(type.open())){
                    openCount ++;
                } else {
                    closeCount ++;
                }
            }
            if(closeCount > openCount){
                return false;
            }
        }
        return true;
    }

    private void mapBrackets(List<Character> chars){
        Map<Character, List<Integer>> bracketMap = calculateBracketMap(chars);

        List<Integer> squareBracketOpenPositions = bracketMap.get(BracketType.SQUARE.open());
        List<Integer> squareBracketClosePositions = bracketMap.get(BracketType.SQUARE.close());
        
        System.out.println("sq bracket open positions: " + squareBracketOpenPositions);
        System.out.println("sq bracket close positions: " + squareBracketClosePositions);
        System.out.println("bracket map: " + bracketMap);
    }

    private Map<Character, List<Integer>> calculateBracketMap(List<Character> chars) {
        Map<Character, List<Integer>> bracketMap = new HashMap<>();
        for(int i = 0; i < chars.size(); i++){
            switch (chars.get(i)) {
                case '[':
                    mapCharacter(i, BracketType.SQUARE.open(), bracketMap);
                    break;
                case ']':
                    mapCharacter(i, BracketType.SQUARE.close(), bracketMap);
                    break;
                case '(':
                    mapCharacter(i, BracketType.PARENS.open(), bracketMap);
                    break;
                case ')':
                    mapCharacter(i, BracketType.PARENS.close(), bracketMap);
                    break;
                case '{':
                    mapCharacter(i, BracketType.CURLY.open(), bracketMap);
                    break;
                case '}':
                    mapCharacter(i, BracketType.CURLY.close(), bracketMap);
                    break;
                default:
                    break;
            }
        }
        return bracketMap;
    }

    private void mapCharacter(Integer position, Character key,
        Map<Character, List<Integer>> bracketMap) {
        List<Integer> positions = bracketMap.get(key);
        if(positions == null){
            positions = new ArrayList<>();
            bracketMap.put(key, positions);   
        }
        positions.add(position);
    }

    private List<Character> convertStringToCharacterList(String input) {
        List<Character> chars;
        chars = input.
                chars().
                mapToObj(s -> (char) s).
                collect(Collectors.toList());
        return chars;
    }
 

    private boolean isSameTypeOfBracket(BracketType type, Character c){
        return (c.equals(type.open()) || c.equals(type.close()));
    }

    private boolean isOpeningBracket(Character c){
        return (c.equals(BracketType.SQUARE.open())
             || c.equals(BracketType.PARENS.open())
             || c.equals(BracketType.CURLY.open())
             );
    }

    private boolean isBracket(Character c){
        return (c.equals(BracketType.SQUARE.open())
             || c.equals(BracketType.SQUARE.close())
             || c.equals(BracketType.PARENS.open())
             || c.equals(BracketType.PARENS.close())
             || c.equals(BracketType.CURLY.open())
             || c.equals(BracketType.CURLY.close())
             );
    }


    public static void main(String... args){
        BracketMatcher matcher = new BracketMatcher();
        String TEST_STRING = "{1]2[b)la((fi)]test}  jklj";
        matcher.matchBrackets(TEST_STRING);
    }
}
