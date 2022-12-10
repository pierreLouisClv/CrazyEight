package game;

import java.util.Set;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;
import java.util.Random;

public class CrazyEightNetworkEngine extends CrazyEightEngine{
    private static final int PLAYER_COUNT = 3;

    private final HostFacade hostFacade;
    private final Set<String> players;
    private Player[] initialPlayers = new Player[PLAYER_COUNT];
    private final Game crazyEight;
    private Random random = new Random();

    public CrazyEightNetworkEngine(HostFacade hostFacade, Set<String> players, fr.pantheonsorbonne.miage.model.Game crazyEight) {
        this.hostFacade = hostFacade;
        this.players = players;
        this.crazyEight = crazyEight;
    }

    public static void main(String[] args) {
        //create the host facade
        HostFacade hostFacade = Facade.getFacade();
        hostFacade.waitReady();

        //set the name of the player
        hostFacade.createNewPlayer("Host");

        //create a new game of war
        fr.pantheonsorbonne.miage.model.Game war = hostFacade.createNewGame("CRAZYEIGHT");

        //wait for enough players to join
        hostFacade.waitForExtraPlayerCount(PLAYER_COUNT);

        CrazyEightNetworkEngine host = new CrazyEightNetworkEngine(hostFacade, war.getPlayers(), war);
        host.play();


    }

    @Override
    protected void play(){
        
    }

    protected Set<String> getPlayers() {
        return players;
    }

    @Override
    protected int determinationOfTurn(int turn){
        if(turn % PLAYER_COUNT >= 0){
            return turn % PLAYER_COUNT;
        }
        else{
            return turn % PLAYER_COUNT + PLAYER_COUNT;
        }
    }

    @Override
    protected void cardsDistribution(){
        for (String playerName : getPlayers()) {
            //get random cards
            Card[] cards = new Card[NUMBER_OF_CARDS_FOR_EACH_PLAYERS];
            
            for(int i=0; i<NUMBER_OF_CARDS_FOR_EACH_PLAYERS; i++){
                cards[i]=gameDeck.getTopCard();
            }
            String hand = Card.cardsToString(cards);
            giveCardsToPlayer(playerName, hand);
        }
    }

    protected void giveCardsToPlayer(String playerName, String hand) {
        hostFacade.sendGameCommandToPlayer(crazyEight, playerName, new GameCommand("cardsForYou", hand));
    }

    @Override
    protected void initialisationPlayers(){
        for(int i=1; i<=PLAYER_COUNT; i++){
            Player p = new Player("player-" + i);
            initialPlayers[i] = p;
        }
    }

    protected String getNameOfThePlayer(Player p){
        return p.getName();
    }

    protected Player getPlayerFromName(String str){
        Player playerStr = new Player("");
        for(Player p : getInitialPlayers()){
            if (p.getName().equals(str)){
                playerStr = p;
            }
        }
        return playerStr;
    }

    @Override
    protected Player[] getInitialPlayers(){
        return initialPlayers;
    }

    
    
}
