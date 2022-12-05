package game;

import java.util.LinkedList;
import java.util.Deque;
import java.util.Random;


public class LocalCrazyEight {

    private final String[] PLAYERS_NAMES = {"Naki", "PL", "Malik", "Ken", "Yessi"};
    private final int NB_OF_PLAYERS = PLAYERS_NAMES.length;
    private Player[] initialPlayers = new Player[NB_OF_PLAYERS];
    private final int NUMBER_OF_CARDS_FOR_EACH_PLAYERS = 7;
     
    
    private Deque<Card> allCardsPlayed = new LinkedList<Card>(); 
    private Card lastCardPlayed;
    
    private int turn; 
    private int index; //jack power
    private int nbOfAcePlayed; //ace power
    private int nbOfTurnToPass; //seven power
    private String choosenColor; //eight power
    private boolean replay; //ten power

    protected LocalCrazyEight(){
        allCardsPlayed.add(Deck.getTopCard());
        lastCardPlayed = allCardsPlayed.getLast();

        choosenColor = allCardsPlayed.getLast().getColor();
        index = 1;
        nbOfAcePlayed = 0;
        nbOfTurnToPass = 0;
        replay = false;
    }

    
    protected void playGameWithAllActions(){
        initialisationPlayers();
        System.out.println(Deck.deck.size());
        cardsDistribution();
        System.out.println(Deck.deck.size());
        int realTurn;
        while(true){
            
            
            /*System.out.print("Visible Card: ");*/
            Card.cardPrinter(lastCardPlayed);
            realTurn = determinationOfRealTurn(turn);
            playerTimeToPlay(realTurn);
            
            if(initialPlayers[realTurn].hasWon()){
                System.out.println(initialPlayers[realTurn].getName()+" KAZANDI");
                break;
            }
             System.out.println("Number of cards remains in deck: "+ Deck.deck.size());
            System.out.println("---------------------");
        }        
    }

    protected String playGameQuickly(){
        initialisationPlayers();
        cardsDistribution();
        while(true){
            if(this.getPlayerTurn().hasWon()){
                return this.getPlayerTurn().getName() ;
            }
            playerPlayOrPass();            
        }
    }

    protected void playerPlayOrPass(){
        Player playerTurn = this.getPlayerTurn();
        
        if(this.nbOfTurnToPass != 0){ //Si un 7 a été posé le joueur passe son tour
            this.nbOfTurnToPass --;
        }

        else if(isTheCardPowerfull(this.lastCardPlayed)){
        }

        else{
            LinkedList<Card> playableCards = playerTurn.getPlayableCards(this.lastCardPlayed);
            if(playableCards.isEmpty()){
                takeCard(1);
            }
            else{
                playSeveralCardsOrOnlyOneCard(playableCards);
                //appeler le power -> visible card 
            }            
        }        
        if(this.replay){
            this.replay = false;
        }
        else{
            this.nextPlayer();
        }
    }

    protected void playerTimeToPlay(int realTurn){
        Player playerTurn = initialPlayers[realTurn];
        System.out.println("Next player: " + playerTurn.getName());
        playerTurn.handPrinter();
        playerPlayOrPass();
        System.out.print("Number of " + playerTurn.getName() + "'s remaining cards: ");
        System.out.println(playerTurn.getHandPlayer().size());
 
    }

    protected boolean isTheCardPowerfull(Card lastCardPlayed){
        Player playerTurn = this.getPlayerTurn();

        if(lastCardPlayed.getValue().equals("ACE") && this.nbOfAcePlayed>0){
            LinkedList<Card> allAceOfThePlayer = new LinkedList<Card>();
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

        else if(lastCardPlayed.getValue().equals("EIGHT")){
            Card virtualNewEight = new Card("EIGHT", this.choosenColor);
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
            if((card = Deck.getTopCard()) == null){
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
            if((combination == 1) || cardPlayed.getValue().equals("EIGHT")){
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
    
    private int determinationOfRealTurn(int turn){
        if(turn % NB_OF_PLAYERS >= 0){
            return turn % NB_OF_PLAYERS;
        }
        else{
            return turn % NB_OF_PLAYERS + NB_OF_PLAYERS;
        }

    }
    
    protected void cardsDistribution(){
        for(Player p : initialPlayers){
            for(int i=0; i<NUMBER_OF_CARDS_FOR_EACH_PLAYERS; i++){
                p.getHandPlayer().add(Deck.getTopCard());
            }
        }
    }
    public static void main(String[] args){
        LocalCrazyEight game = new LocalCrazyEight();
        game.playGameWithAllActions();


    }
    
    protected void initialisationPlayers(){
        for(int i=0; i<NB_OF_PLAYERS; i++){
            Player p = new Player(PLAYERS_NAMES[i]);
            initialPlayers[i] = p;
        }
    }

    protected void deckReshuffler(){
        Random random = new Random();
        LinkedList<Card> cards = (LinkedList<Card>)allCardsPlayed;
        while(!cards.isEmpty()){
            int randomNumber = random.nextInt(cards.size());
            Deck.deck.add(cards.get(randomNumber));
            cards.remove(randomNumber);
        } 
    }

    protected Player getPlayerTurn(){
        return this.initialPlayers[determinationOfRealTurn(turn)];
    }

    protected void reverse(){
        index = index * (-1);
    }

    protected void nextPlayer(){
        turn += getIndex();
    }

    protected Deque<Card> getAllCardsPlayed(){
        return allCardsPlayed;
    }

    protected Player[] getInitialPlayers(){
        return initialPlayers;
    }

    protected String[] getPlayersNames(){
        return PLAYERS_NAMES;
    }

    protected int getIndex(){
        return index;
    }

    protected int getTurn(){
        return turn;
    }
}