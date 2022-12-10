package game;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public abstract class CrazyEightEngine {
    protected static final int NUMBER_OF_CARDS_FOR_EACH_PLAYERS = 7;

    protected Deck gameDeck;
    protected Deque<Card> allCardsPlayed = new LinkedList<>(); 
    protected Card lastCardPlayed;
    
    protected int turn; 
    protected int index; //jack power
    protected int nbOfAcePlayed; //ace power
    protected int nbOfTurnToPass; //seven power
    protected String choosenColor; //eight power
    protected boolean replay; //ten power

    protected abstract void play();

    /*protected void play(){
        initialisationGame();
        cardsDistribution();
        while(true){
            if(this.getPlayerTurn().hasWon()){
                return this.getPlayerTurn().getName() ;
            }
            playerPlayOrPass();            
        }
    }*/

    protected void playerPlayOrPass(String playerId){
        Player playerTurn = getPlayerFromName(playerId);
        
        if(this.nbOfTurnToPass != 0){ //Si un 7 a été posé le joueur passe son tour
            this.nbOfTurnToPass --;
        }

        else if(isTheLastCardPlayedPowerfull(this.lastCardPlayed)){
        }

        else{
            LinkedList<Card> playableCards = playerTurn.getPlayableCards(this.lastCardPlayed);
            if(playableCards.isEmpty()){
                System.out.println(playerTurn.getName() + " takes a card ...");
                takeCard(1);
            }
            else{
                playSeveralCardsOrOnlyOneCard(playableCards);
            }            
        }        
        if(this.replay){
            this.replay = false;
        }
        else{
            this.nextPlayer();
        }
    }

    protected void letPlayerPlay(){
        Player playerTurn = this.getPlayerTurn();
        System.out.println("Next player: " + playerTurn.getName());
        playerTurn.handPrinter();
        playerPlayOrPass();
        System.out.print("Number of " + playerTurn.getName() + "'s remaining cards: ");
        System.out.println(playerTurn.getHandPlayer().size());
 
    }

    protected boolean isTheLastCardPlayedPowerfull(Card lastCardPlayed){
        Player playerTurn = this.getPlayerTurn();

        if(lastCardPlayed.getValue().equals("ACE") && this.nbOfAcePlayed>0){
            LinkedList<Card> allAceOfThePlayer = new LinkedList<>();
            for(Card card : playerTurn.getHandPlayer()){
                if(card.getValue().equals("ACE")){
                    allAceOfThePlayer.add(card);
                }
            }
            if(allAceOfThePlayer.isEmpty()){
                takeCard(this.nbOfAcePlayed*2);
                this.nbOfAcePlayed = 0;
            }
            else{
                playSeveralCardsOrOnlyOneCard(allAceOfThePlayer);
            }

            return true;
            
        }

        else if(lastCardPlayed.getValue().equals(Card.getMostPowerfullValue())){
            Card virtualNewEight = new Card(Card.getMostPowerfullValue(), this.choosenColor);
            LinkedList<Card> playableCard = playerTurn.getPlayableCards(virtualNewEight);
            if(playableCard.isEmpty()){
                takeCard(1);
            }
            else{
                playSeveralCardsOrOnlyOneCard(playableCard);
            }
            
            return true;
        }

        return false;
    }

    protected void getCardPower(Card card){
        switch(card.getValue()){
            case "ACE" :
                this.nbOfAcePlayed++; //itération du nombre d'ace en jeu
                break;
        
            case "EIGHT" :
                this.choosenColor = this.getPlayerTurn().determinColorAfterAnEight(); //définit la couleur choisie par le joueur
                break;

            case "TEN" :
                this.replay = true;
                break;

            case "JACK" :
                this.reverse();
                break;

            case "SEVEN" :
                this.nbOfTurnToPass +=1;
                break;

            default :
                break;
        }
    }

    public void takeCard(int nbOfCardsPlayerMustToTake){
        Player playerTurn = this.getPlayerTurn();
        Card card;

        for(int i=0; i < nbOfCardsPlayerMustToTake; i++){
            if((card = gameDeck.getTopCard()) == null){
                deckReshuffler();
            }
            else{
                playerTurn.getHandPlayer().add(card);
            }
        }
    } 
    
    protected void playSeveralCardsOrOnlyOneCard(LinkedList<Card> playableCards){
        Player playerTurn = this.getPlayerTurn();

        int size = playableCards.size();
        Card cardPlayed = playableCards.get(0);
        int combination = playerTurn.nbOfCombinationOfTheCard(cardPlayed);
        if(size > 1){
            cardPlayed = playerTurn.makeTheBestChoice(playableCards, this.lastCardPlayed);
        }
        else{
            if(combination == 1){
                oneCardIsPlayed(cardPlayed);
                return ;
            }
        }            
        if((combination == 1) || cardPlayed.getValue().equals(Card.getMostPowerfullValue())){
            oneCardIsPlayed(cardPlayed);
        }
        else if(combination == 2){                
            playTwoCards(playerTurn, cardPlayed, playableCards);
        }
        else{                
            playThreeOrFourCards(playerTurn, cardPlayed, combination);
        }
            
    }
        
    protected void playTwoCards(Player playerTurn, Card cardPlayed, LinkedList<Card> playableCards){
            oneCardIsPlayed(cardPlayed);
            Card theSecondCardToPlay = playableCards.get(0);
                for(Card card : playerTurn.getHandPlayer()){
                    if(card.haveSameValue(cardPlayed) && !(card.haveSameColor(cardPlayed))){
                        theSecondCardToPlay = card;
                        break;
                    }
                }                
                oneCardIsPlayed(theSecondCardToPlay);
        }

        protected void playThreeOrFourCards(Player playerTurn, Card cardPlayed, int combination){
            Card finalCardToPlay = playerTurn.cardPlayedAtTheEndOfTheCombination(cardPlayed, combination);
                LinkedList<Card> otherCardsPlayed = new LinkedList<>();            
                for(Card card : playerTurn.getHandPlayer()){
                    if(card.haveSameValue(finalCardToPlay) && !(card.haveSameColor(finalCardToPlay))){
                        otherCardsPlayed.add(card); //secondCard = card
                        if(combination==3){
                            break;
                        }
                    }
                }
                for(Card card : otherCardsPlayed){
                    oneCardIsPlayed(card);
                }
                oneCardIsPlayed(finalCardToPlay);
        }

        protected void oneCardIsPlayed(Card cardPlayed){
            Player playerTurn = this.getPlayerTurn();
        this.allCardsPlayed.add(cardPlayed);        
        System.out.println(cardPlayed.getValue() + " OF " + cardPlayed.getColor());
        playerTurn.getHandPlayer().remove(cardPlayed);
        getCardPower(cardPlayed);
        this.lastCardPlayed = this.allCardsPlayed.getLast();
    }
    
    protected abstract int determinationOfTurn(int turn);
    public static void main(String[] args){
        LocalCrazyEight game = new LocalCrazyEight();
        game.play();


    }

    protected void deckReshuffler(){
        Random random = new Random();
        LinkedList<Card> cards = (LinkedList<Card>)allCardsPlayed;
        while(!cards.isEmpty()){
            int randomNumber = random.nextInt(cards.size());
            gameDeck.getDeck().add(cards.get(randomNumber));
            cards.remove(randomNumber);
        } 
    }

    protected abstract void cardsDistribution();

    protected void initialisationGame(){
        gameDeck = new Deck();
        allCardsPlayed.add(gameDeck.getTopCard());
        lastCardPlayed = allCardsPlayed.getLast();
        choosenColor = allCardsPlayed.getLast().getColor();
        index = 1;
        nbOfAcePlayed = 0;
        nbOfTurnToPass = 0;
        replay = false;
    }

    protected abstract void initialisationPlayers();

    protected Player getPlayerFromName(String playerId){
        Player playerTurn = new Player("");
        for(Player p : getInitialPlayers()){
            if(p.getName().equals(playerId)){
                playerTurn = 
            }
        }
    }

    protected Deck getGameDeck(){
        return this.gameDeck;
    }

    protected void reverse(){
        this.index = this.index * (-1);
    }

    protected void nextPlayer(){
        this.turn += this.getIndex();
    }

    protected Deque<Card> getAllCardsPlayed(){
        return this.allCardsPlayed;
    }

    protected abstract Player[] getInitialPlayers();
    
    protected Player getPlayerTurn(){
        return this.getInitialPlayers()[determinationOfTurn(turn)];
    }

    protected int getIndex(){
        return this.index;
    }
    
    protected int getTurn(){
        return this.turn;
    }
    
    protected String getChoosenColor(){
        return this.choosenColor;
    }

    protected int getNbOfAcePlayed(){
        return this.nbOfAcePlayed;
    }
}
