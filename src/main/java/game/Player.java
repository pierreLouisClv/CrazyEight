package game;

import java.util.HashMap;
import java.util.LinkedList;

public class Player {  
    protected String name;
    protected LinkedList<Card> handPlayer = new LinkedList<Card>();

    protected Player(String name){
        this.name = name;
    }

    protected void playerTourToPlayCard(){
        //Controle visible card 
        controlVisibleCard(LocalCrazyEight.visibleCard);
        LinkedList<Card> playableCards = getPlayableCards();
        //int = hasCombination(bestChoice) //=3
        if(playableCards.size()==0){
            takeCard(1);
        }
        else{
            playCard(playableCards);
            //appeler le power -> visible card 
        }
        
    }

    protected void controlVisibleCard(Card visibleCard){
        if(Card.getName(visibleCard).equals("ACE")){
            LinkedList<Card> allAceOfThePlayer = new LinkedList<Card>();
            for(Card card : handPlayer){
                if(Card.getName(card).equals("ACE")){
                    allAceOfThePlayer.add(card);
                }
            }
            if(allAceOfThePlayer.isEmpty()){
                takeCard(LocalCrazyEight.nbOfAcePlayed*2);
                LocalCrazyEight.nbOfAcePlayed = 0;
            }
            else{
                int size = allAceOfThePlayer.size();
                for(int i=0; i<size; i++){
                    playCard(allAceOfThePlayer);
                }
            }
        }
    }

    protected void playCard(LinkedList<Card> playableCards){
        int size = playableCards.size();
        Card cardPlayed = playableCards.get(0);
        int combination = hasCombination(cardPlayed);
        if(size > 1){
            cardPlayed = getBestChoice(playableCards);
        }
        else{
            if(combination == 1){
                playOnlyOneCard(cardPlayed);
                return ;
            }
        }
            playOnlyOneCard(cardPlayed);
            if(combination == 1){
                //return ;
            }
            else if(combination == 2){
                
                Card theSecondCardToPlay = playableCards.get(0);
                for(Card card : handPlayer){
                    if(Card.sameName(card, cardPlayed) && !(Card.sameColor(card, cardPlayed))){
                        theSecondCardToPlay = card;
                        break;
                    }
                }                
                playOnlyOneCard(theSecondCardToPlay);
            }
            else {                
                Card finalCardToPlay = finalCardToPlay(cardPlayed, combination);
                LinkedList<Card> otherCardsPlayed = new LinkedList<Card>();            
                for(Card card : handPlayer){
                    if(Card.sameName(card, finalCardToPlay) && !(Card.sameColor(card, finalCardToPlay))){
                        otherCardsPlayed.add(card); //secondCard = card
                        if(combination==3){
                            break;
                        }
                    }
                }
                for(Card card : otherCardsPlayed){
                    playOnlyOneCard(card);
                }
                playOnlyOneCard(finalCardToPlay);
            }
            
        }
    
    protected int numberOfColors(String color){
        int number = 0;
        for(Card card: handPlayer){
            if(Card.getColor(card).equals(color)){
                number++;
            }
        }
        return number;
    }
    
    protected Card finalCardToPlay(Card bestChoice, int combination){
        HashMap<Card, Integer> numberOfEachColor = new HashMap<Card,Integer>(combination - 1);
        for(Card card : handPlayer){
            if((Card.sameName(card, bestChoice)) && !(Card.sameColor(card, bestChoice))){
                numberOfEachColor.put(card, numberOfColors(Card.getColor(card)));
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
          
    


    protected void playOnlyOneCard(Card cardPlayed){
        if(Card.getName(cardPlayed) == "ACE"){
            LocalCrazyEight.nbOfAcePlayed += 1;
        }
        LocalCrazyEight.allCardsPlayed.add(cardPlayed);
        System.out.println(Card.getName(cardPlayed) + " OF " + Card.getColor(cardPlayed));
        handPlayer.remove(cardPlayed);
        //if card = ACE
        //NBOFACE += 1
        LocalCrazyEight.visibleCard = LocalCrazyEight.allCardsPlayed.get(LocalCrazyEight.allCardsPlayed.size()-1);
    }

    protected LinkedList<Card> getPlayableCards(){
        LinkedList<Card> playableCards = new LinkedList<Card>();
        
        for(Card card : handPlayer){
            if((Card.sameColor(card, LocalCrazyEight.visibleCard))||(Card.sameName(card, LocalCrazyEight.visibleCard))||(Card.getName(card)=="EIGHT")){
                playableCards.add(card);
            }            
        }
        return playableCards;
    }
    
    protected Card getBestChoice(LinkedList<Card> playableCards){
        int highestCombination = 1;
        int i = 0;
        int combination;
        int size = playableCards.size();
        while(Card.getName(playableCards.get(i)) == "EIGHT" && i<size){
            if(i == size - 1){
                break;
            }
            i++;
        }
        Card bestChoice = playableCards.get(i);
        for(Card card : playableCards){
            if((combination = hasCombination(card)) > highestCombination && Card.getName(card) != "EIGHT"){ //if (hasCombination(card)>highestCombination)
                bestChoice = card; 
                highestCombination = combination;
            }
        }
        String nameOfBestChoice = Card.getName(bestChoice);
        int numberOfCardsOfSameName = 1;
        for(Card card: playableCards){
            if(Card.getName(card).equals(nameOfBestChoice)){
                numberOfCardsOfSameName++;
            }
        }
        if(numberOfCardsOfSameName>1){
            Card finalCard = finalCardToPlay(LocalCrazyEight.visibleCard,hasCombination(bestChoice));
            if(bestChoice.equals(finalCard)){
                for(Card c: playableCards){
                    if(Card.sameName(c, finalCard) && !(Card.sameColor(c, finalCard))){
                        bestChoice = c;
                        break;
                    }
                }
            }
        }
        return bestChoice;
    }

    protected static int numberOfColors(Player p, String color){
        int count = 0;
        for(Card card: p.handPlayer){
            if(Card.getColor(card).equals(color)){
                count++;
            }
        }
        return count;
    }

    protected int hasCombination(Card handCard){
        int highestCombination = 1; //int -> return 1,2,3 ou 4
        for(Card card : handPlayer){
            if((Card.sameName(card, handCard))&& !(Card.sameColor(card, handCard))){
                highestCombination++;
            }
        }
        return highestCombination;
    }

    public void takeCard(int NB_CARD_DISTRIBUED){
        for(int i=0; i < NB_CARD_DISTRIBUED; i++){ 
            handPlayer.add(Deck.getTopCard());
        }
    } 

    protected void handPrinter(){
        for(Card card : this.handPlayer){
            System.out.print(Card.getName(card)+" OF "+Card.getColor(card)+" | ");
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