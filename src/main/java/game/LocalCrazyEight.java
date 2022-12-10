package game;

import java.util.LinkedList;
import java.util.Deque;
import java.util.Random;


public class LocalCrazyEight extends CrazyEightEngine{

    private static final String[] PLAYERS_NAMES = {"Naki", "PL", "Malik", "Ken"};
    private static final int NB_OF_PLAYERS = PLAYERS_NAMES.length;
    private Player[] initialPlayers = new Player[NB_OF_PLAYERS];
    private static final int NUMBER_OF_CARDS_FOR_EACH_PLAYERS = 7;


    protected LocalCrazyEight(){
        this.initialisationPlayers();
        initialisationGame();
    }

    public static void main(String[] args){
        CrazyEightEngine game = new LocalCrazyEight();
        game.play();
    }

    @Override
    protected void play(){
        initialisationGame();
        cardsDistribution();
        while(true){
            System.out.println("---------------------");
            System.out.print("The last card played: ");
            Card.cardPrinter(lastCardPlayed);
            letPlayerPlay();
            
            if(this.getPlayerTurn().hasWon()){
                System.out.println("THE WINNER IS " + getPlayerTurn().getName());
                break;
            }
            System.out.println("---------------------");
        }        
    }

    @Override
    protected void initialisationPlayers(){
        for(int i=0; i<NB_OF_PLAYERS; i++){
            Player p = new Player(PLAYERS_NAMES[i]);
            initialPlayers[i] = p;
        }
    }

    @Override
    protected void cardsDistribution(){
        for(Player p : initialPlayers){
            for(int i=0; i<NUMBER_OF_CARDS_FOR_EACH_PLAYERS; i++){
                p.getHandPlayer().add(gameDeck.getTopCard());
            }
        }
    }

    protected int determinationOfTurn(int turn){
        if(turn % NB_OF_PLAYERS >= 0){
            return turn % NB_OF_PLAYERS;
        }
        else{
            return turn % NB_OF_PLAYERS + NB_OF_PLAYERS;
        }
    }

    protected Player getPlayerTurn(){
        return this.initialPlayers[determinationOfTurn(turn)];
    }

    protected Player[] getInitialPlayers(){
        return this.initialPlayers;
    }

    protected static String[] getPlayersNames(){
        return PLAYERS_NAMES;
    }

    protected static int getNbOfPlayers(){
        return NB_OF_PLAYERS;
    }
}

