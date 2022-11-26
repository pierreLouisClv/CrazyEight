package game;

import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.cglib.core.Local;

public class LocalCrazyEight {

    protected LocalCrazyEight(){

    }


    private static final String[] PLAYERS_NAMES = {"Naki", "PL", "Malik"};
    private static final int NUM_OF_PLAYERS = PLAYERS_NAMES.length;
    private static Player[] initialPlayers = new Player[NUM_OF_PLAYERS];
     
    
    protected static LinkedList<Card> allCardsPlayed = new LinkedList<>();    
    private static int index = 1; //jack power
    protected static int turn = 0; 
    protected static int nbOfAcePlayed = 0; //ace power
    protected static int nbOfTurnToPass = 0; //seven power
    protected static String choosenColor; //eight power
    protected static boolean replay = false; //ten power
    
    
    static {
        allCardsPlayed.add(Deck.getTopCard());
        choosenColor = Card.getColor(allCardsPlayed.get(0));                
    }

    protected static int determinationOfRealTurn(int turn){
        if(turn % NUM_OF_PLAYERS >= 0){
            return turn % NUM_OF_PLAYERS;
        }
        else{
            return turn % NUM_OF_PLAYERS + NUM_OF_PLAYERS;
        }

    }

    
    protected static Card visibleCard = allCardsPlayed.get(allCardsPlayed.size()-1);

    protected static void newPlayGame(){
        initialisationPlayers();
        System.out.println(Deck.deck.size());
        cardsDistribution();
        System.out.println(Deck.deck.size());
        int realTurn;
        while(true){
            /*System.out.println(turn);*/
            realTurn = turn % NUM_OF_PLAYERS;
            /*System.out.print("Visible Card: ");*/
            Card.cardPrinter(visibleCard);
            if(realTurn >= 0){
                playerTimeToPlayV2(realTurn);
            }
            else {
                realTurn = realTurn + NUM_OF_PLAYERS;
                playerTimeToPlayV2(realTurn);
            }
            if(initialPlayers[realTurn].hasWon()){
                System.out.println(initialPlayers[realTurn].getName()+" KAZANDI");
                break;
                }
             System.out.println("Number of cards remains in deck: "+ Deck.deck.size());
            System.out.println("---------------------");
        }
        
    }

    protected static void playGame(){
        initialisationPlayers();
        System.out.println(Deck.deck.size());
        cardsDistribution();
        System.out.println(Deck.deck.size());
        int realTurn;
        while(true){
            System.out.println(turn);
            realTurn = turn % NUM_OF_PLAYERS;
            System.out.print("Visible Card: ");
            Card.cardPrinter(visibleCard);
            if(realTurn >= 0){
                playerTimeToPlay(realTurn);
            }
            else {
                realTurn = realTurn + NUM_OF_PLAYERS;
                playerTimeToPlay(realTurn);
            }
            if(initialPlayers[realTurn].hasWon()){
                System.out.println(initialPlayers[realTurn].getName()+" KAZANDI");
                break;
                }

            cardPowerDeterminer(visibleCard);
            turn = turn + index;
            System.out.println("Number of cards remains in deck: "+ Deck.deck.size());
            System.out.println("---------------------");
        }
        //distribution des cartes
        //joueur qui commence
        //chacun joue
        //si player has no card -> he win

    }

    protected static void playerTimeToPlay(int realTurn){
        Player actualPlayer = initialPlayers[realTurn];
        System.out.println("Next player: " + actualPlayer.getName());
        actualPlayer.handPrinter();
        actualPlayer.playerTourToPlayCard();
        System.out.print("Number of " + actualPlayer.getName() + "'s remaining cards: ");
        System.out.println(actualPlayer.getHandPlayer().size());
 
    }

    protected static void playerTimeToPlayV2(int realTurn){
        Player actualPlayer = initialPlayers[realTurn];
        System.out.println("Next player: " + actualPlayer.getName());
        actualPlayer.handPrinter();
        actualPlayer.playerTurnToPlay();
        System.out.print("Number of " + actualPlayer.getName() + "'s remaining cards: ");
        System.out.println(actualPlayer.getHandPlayer().size());
 
    }
    
    
    protected static void cardPowerDeterminer(Card c){
        if(Card.getName(c).equals("ACE")){// Next player takes 2 cards and not play
            int realTurn = determinationOfRealTurn(turn + index);
            int hasAce = 0;
            for(Card card : initialPlayers[realTurn].getHandPlayer()){
                if(Card.getName(card)=="ACE"){
                    initialPlayers[realTurn].playOnlyOneCard(card);
                    hasAce = index;
                }
            }

            realTurn = determinationOfRealTurn(realTurn + hasAce);
            
            initialPlayers[realTurn].takeCard(nbOfAcePlayed * 2);
            nbOfAcePlayed = 0;

            turn = turn + hasAce + index;
        }
        if(Card.getName(c).equals("SEVEN")){// Make next player not to play
            turn = turn + index;
        }
        if(Card.getName(c).equals("EIGHT")){//Choose of color for the next player to play
            
        }
        if(Card.getName(c).equals("TEN")){// Player replays
            turn = turn - index;
        }
        if(Card.getName(c).equals("JACK")){// Change of game direktion
            index = -index;
        }
    }
    
    
    protected static void cardsDistribution(){
        for(Player p : initialPlayers){
            p.takeCard(7);
        }
    }
    public static void main(String[] args){
        newPlayGame();
        /*initialisationPlayers();
        System.out.println(Deck.deck.size());
        cardsDistribution();
        System.out.println(Deck.deck.size());
        int realTurn;
        while(true){
            System.out.println(turn);
            System.out.print("Visible Card: ");
            Card.cardPrinter(visibleCard);
            realTurn = determinationOfRealTurn(turn);
                System.out.println("Next player: "+initialPlayers[realTurn].getName());
                initialPlayers[realTurn].handPrinter();
                initialPlayers[realTurn].playerTourToPlayCard();
                System.out.print("Number of "+initialPlayers[realTurn].getName()+"'s remaining cards: ");
                System.out.println(initialPlayers[realTurn].getHandPlayer().size());
                if(initialPlayers[realTurn].getHandPlayer().size() == 0){
                    System.out.println(initialPlayers[realTurn].getName()+" KAZANDI");
                    break;
                }
            
            cardPowerDeterminer(visibleCard);
            turn = turn + index;
            System.out.println("Number of cards remains in deck: "+ Deck.deck.size());
            System.out.println("---------------------");
        }*/

    }
    





    protected static void initialisationPlayers(){
        for(int i=0; i<NUM_OF_PLAYERS; i++){
            Player p = new Player(PLAYERS_NAMES[i]);
            initialPlayers[i] = p;
        }
    }

    protected static void reverse(){
        index = index * (-1);
    }

    protected static void nextPlayer(){
        LocalCrazyEight.turn += LocalCrazyEight.getIndex();
    }

    protected static Player[] getInitialPlayers(){
        return initialPlayers;
    }

    protected static String[] getPlayersNames(){
        return PLAYERS_NAMES;
    }

    protected static int getIndex(){
        return index;
    }

    protected static int getTurn(){
        return turn;
    }
}