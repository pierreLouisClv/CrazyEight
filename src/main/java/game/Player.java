package game;

import java.util.HashMap;
import java.util.LinkedList;

public class Player {  
    private String name;
    private LinkedList<Card> handPlayer = new LinkedList<>();

    protected Player(String name){
        this.name = name;
    }

    
    protected int numberOfColorsInPlayerHand(String color){
        int number = 0;
        for(Card card: handPlayer){
            if(card.getColor().equals(color)){
                number++;
            }
        }
        return number;
    }
    
    protected Card cardPlayedAtTheEndOfTheCombination(Card bestChoice, int combination){
        HashMap<Card, Integer> nbOfEachColorsInHandPlayer = new HashMap<>(combination - 1);
        for(Card card : handPlayer){
            if((card.haveSameValue(bestChoice)) && !(card.haveSameColor(bestChoice))){
                nbOfEachColorsInHandPlayer.put(card, numberOfColorsInPlayerHand(card.getColor()));
            }
        }
        Card finalCardPlayed = bestChoice;
        int maxColor = 0;
        for(Card card1 : nbOfEachColorsInHandPlayer.keySet()){
            if(nbOfEachColorsInHandPlayer.get(card1) > maxColor){
                finalCardPlayed = card1;
                maxColor = nbOfEachColorsInHandPlayer.get(card1);
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
        while(playableCards.get(i).getValue().equals("EIGHT") && i<size){
            if(i == size - 1){
                break;
            }
            i++;
        }
        Card bestChoice = playableCards.get(i);
        for(Card card : playableCards){
            if((combination = nbOfCombinationOfTheCard(card)) > highestCombination && !card.getValue().equals("EIGHT")){
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
            Card finalCard = cardPlayedAtTheEndOfTheCombination(visibleCard, nbOfCombinationOfTheCard(bestChoice));
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
            HashMap<String, Integer> nbOfEachColorsInHandPlayer = new HashMap<String,Integer>();
            
            for(int i=0; i<handPlayer.size() ; i++){
                if(handPlayer.get(i).getValue() != "EIGHT"){
                    String currentColor = handPlayer.get(i).getColor();
                    if(nbOfEachColorsInHandPlayer.containsKey(currentColor)){
                        nbOfEachColorsInHandPlayer.put(currentColor, nbOfEachColorsInHandPlayer.get(currentColor) + 1);
                    }
                    else{
                        nbOfEachColorsInHandPlayer.put(currentColor, 1);
                    }
                }
            }

            String colorMostRepresentedInPlayerHand = "End";
            int maxColors = 0;
            int countColor = 0;
            for(String color : nbOfEachColorsInHandPlayer.keySet()){
                if((countColor = nbOfEachColorsInHandPlayer.get(color)) > maxColors){
                    maxColors = countColor;
                    colorMostRepresentedInPlayerHand = color;
                }
            }

            return colorMostRepresentedInPlayerHand;
    }

    protected static int numberOfColorsInPlayerHand(Player p, String color){
        int count = 0;
        for(Card card: p.handPlayer){
            if(card.getColor().equals(color)){
                count++;
            }
        }
        return count;
    }

    protected int nbOfCombinationOfTheCard(Card handCard){
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
            System.out.print(card.getValue() + " OF " + card.getColor() + " | ");
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