package game;

import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.cglib.core.Local;

public class Player {  
    protected String name;
    protected LinkedList<Card> handPlayer = new LinkedList<Card>();

    protected Player(String name){
        this.name = name;
    }

    protected void playerTurnToPlay(){
        
        if(LocalCrazyEight.nbOfTurnToPass != 0){ //Si un 7 a été posé le joueur passe son tour
            LocalCrazyEight.nbOfTurnToPass --;
        }


        else if(isTheCardPowerfull(LocalCrazyEight.visibleCard)){
        }

        else{
            LinkedList<Card> playableCards = getPlayableCards(LocalCrazyEight.visibleCard);
            //int = hasCombination(bestChoice) //=3
            if(playableCards.size()==0){
                takeCard(1);
            }
            else{
                playCard(playableCards);
                //appeler le power -> visible card 
            }
            
        }

        if(LocalCrazyEight.replay){
            LocalCrazyEight.replay = false;
        }

        else{
            LocalCrazyEight.nextPlayer();
        }
    }

    protected void playerTourToPlayCard(){
        //Controle visible card 
        /*controlVisibleCard(LocalCrazyEight.visibleCard);
        if(LocalCrazyEight.nbOfTurnToPass != 0){
            LocalCrazyEight.nbOfTurnToPass --;
            LocalCrazyEight.nextPlayer();
        }

        else{*/
        LinkedList<Card> playableCards = getPlayableCards(LocalCrazyEight.visibleCard);
        //int = hasCombination(bestChoice) //=3
        if(playableCards.size()==0){
            takeCard(1);
        }
        else{
            playCard(playableCards);
            //appeler le power -> visible card 
        }
        LocalCrazyEight.nextPlayer();
    
        
    }

    protected boolean isTheCardPowerfull(Card visibleCard){
        if(Card.getName(visibleCard).equals("ACE") && LocalCrazyEight.nbOfAcePlayed>0){
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
                playCard(allAceOfThePlayer);
            }

            return true;
            
        }

        else if(Card.getName(visibleCard).equals("EIGHT")){
            Card virtualVisibleCard = new Card("EIGHT", LocalCrazyEight.choosenColor);
            LinkedList<Card> playableCard = getPlayableCards(virtualVisibleCard);
            if(playableCard.isEmpty()){
                takeCard(1);
            }
            else{
                playCard(playableCard);
            }
            
            return true;
        }

        return false;
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

            if((combination == 1) || Card.getName(cardPlayed).equals("EIGHT")){
                return ;
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
        /*if(Card.getName(cardPlayed) == "ACE"){
            LocalCrazyEight.nbOfAcePlayed += 1;
        }*/
        LocalCrazyEight.allCardsPlayed.add(cardPlayed);        
        System.out.println(Card.getName(cardPlayed) + " OF " + Card.getColor(cardPlayed));
        handPlayer.remove(cardPlayed);
        getPower(cardPlayed);
        //if card = ACE
        //NBOFACE += 1
        LocalCrazyEight.visibleCard = LocalCrazyEight.allCardsPlayed.get(LocalCrazyEight.allCardsPlayed.size()-1);
    }

    protected void getPower(Card card){
        if(Card.getName(card).equals("ACE")){
            LocalCrazyEight.nbOfAcePlayed++; //itération du nombre d'ace en jeu
        }

        else if(Card.getName(card).equals("EIGHT")){
            LocalCrazyEight.choosenColor = determinColorAfterAnEight(); //définit la couleur choisie par le joueur
        }

        else if(Card.getName(card).equals("TEN")){
            LocalCrazyEight.replay = true;
        }

        else if(Card.getName(card).equals("JACK")){
            LocalCrazyEight.reverse();
        }

        else if(Card.getName(card).equals("SEVEN")){
            LocalCrazyEight.nbOfTurnToPass +=1;
        }

    }

    protected LinkedList<Card> getPlayableCards(Card visibleCard){
        LinkedList<Card> playableCards = new LinkedList<Card>();
        
        for(Card card : handPlayer){
            if((Card.sameColor(card, visibleCard))||(Card.sameName(card, visibleCard))||(Card.getName(card)=="EIGHT")){
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

    protected String determinColorAfterAnEight(){
            HashMap<String, Integer> numberOfEachColor = new HashMap<String,Integer>();
            
            for(int i=0; i<handPlayer.size() ; i++){
                if(Card.getName(handPlayer.get(i)) != "EIGHT"){
                    String currentColor = Card.getColor(handPlayer.get(i));
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