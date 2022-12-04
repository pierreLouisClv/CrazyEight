package game;

import java.util.HashMap;
import java.util.LinkedList;

public class Player {  
    private String name;
    private LinkedList<Card> handPlayer = new LinkedList<>();

    protected Player(String name){
        this.name = name;
    }

    
    protected int numberOfColors(String color){
        int number = 0;
        for(Card card: handPlayer){
            if(card.getColor().equals(color)){
                number++;
            }
        }
        return number;
    }
    
    protected Card finalCardToPlay(Card bestChoice, int combination){
        HashMap<Card, Integer> numberOfEachColor = new HashMap<Card,Integer>(combination - 1);
        for(Card card : handPlayer){
            if((card.haveSameValue(bestChoice)) && !(card.haveSameColor(bestChoice))){
                numberOfEachColor.put(card, numberOfColors(card.getColor()));
            }
        }
        Card finalCardPlayed = bestChoice;
        int maxColor = 0;
        for(Card card1 : numberOfEachColor.keySet()){
            if(numberOfEachColor.get(card1) > maxColor){
                finalCardPlayed = card1;
                maxColor = numberOfEachColor.get(card1);
            }    
        }
        return finalCardPlayed;
    }
        

    protected LinkedList<Card> getPlayableCards(Card visibleCard){
        LinkedList<Card> playableCards = new LinkedList<Card>();
        
        for(Card card : handPlayer){
            if(card.haveSameColor(visibleCard) || 
            (card.haveSameValue(visibleCard)) ||
            (card.getValue() == "EIGHT"))
            {
                playableCards.add(card);
            }            
        }
        return playableCards;
    }
    
    protected Card makeTheBestChoice(LinkedList<Card> playableCards, Card visibleCard){
        int highestCombination = 1;
        int i = 0;
        int combination;
        int size = playableCards.size();
        while(playableCards.get(i).getValue() == "EIGHT" && i<size){
            if(i == size - 1){
                break;
            }
            i++;
        }
        Card bestChoice = playableCards.get(i);
        for(Card card : playableCards){
            if((combination = hasCombination(card)) > highestCombination && card.getValue() != "EIGHT"){ //if (hasCombination(card)>highestCombination)
                bestChoice = card; 
                highestCombination = combination;
            }
        }
        String valueOfTheBestChoice = bestChoice.getValue();
        int nbOfCardsWithSameValue = 1;
        for(Card card: playableCards){
            if(card.getValue().equals(valueOfTheBestChoice)){
                nbOfCardsWithSameValue++;
            }
        }
        if(nbOfCardsWithSameValue>1){
            Card finalCard = finalCardToPlay(visibleCard, hasCombination(bestChoice));
            if(bestChoice.equals(finalCard)){
                for(Card card : playableCards){
                    if(card.haveSameValue(finalCard) && !(card.haveSameColor(finalCard))){
                        bestChoice = card;
                        break;
                    }
                }
            }
        }
        return bestChoice;
    }

    protected String determinColorAfterAnEight(){
            HashMap<String, Integer> numberOfEachColor = new HashMap<String,Integer>();
            
            for(int i=0; i<handPlayer.size() ; i++){
                if(handPlayer.get(i).getValue() != "EIGHT"){
                    String currentColor = handPlayer.get(i).getColor();
                    if(numberOfEachColor.containsKey(currentColor)){
                        numberOfEachColor.put(currentColor, numberOfEachColor.get(currentColor) + 1);
                    }
                    else{
                        numberOfEachColor.put(currentColor, 1);
                    }
                }
            }

            String colorMostRepresentedInPlayerHand = "End";
            int maxColors = 0;
            int countColor = 0;
            for(String color : numberOfEachColor.keySet()){
                if((countColor = numberOfEachColor.get(color)) > maxColors){
                    maxColors = countColor;
                    colorMostRepresentedInPlayerHand = color;
                }
            }

            return colorMostRepresentedInPlayerHand;
    }

    protected static int numberOfColors(Player p, String color){
        int count = 0;
        for(Card card: p.handPlayer){
            if(card.getColor().equals(color)){
                count++;
            }
        }
        return count;
    }

    protected int hasCombination(Card handCard){
        int highestCombination = 1; //int -> return 1,2,3 ou 4
        for(Card card : handPlayer){
            if(card.haveSameValue(handCard) && !(card.haveSameColor(handCard))){
                highestCombination++;
            }
        }
        return highestCombination;
    }   

    protected void handPrinter(){
        for(Card card : this.handPlayer){
            System.out.print(card.getValue()+" OF "+card.getColor()+" | ");
        }
        System.out.println();
    }

    protected boolean hasWon(){
        return this.handPlayer.isEmpty();
    }

    protected LinkedList<Card> getHandPlayer(){
        return this.handPlayer;
    }

    protected String getName(){
        return this.name;
    }
}