package game;

public class LocalCrazyEight extends CrazyEightEngine{

    private static final String[] PLAYERS_NAMES = {"Naki", "PL", "Malik", "Ken"};
    private static final int NB_OF_PLAYERS = PLAYERS_NAMES.length;

    protected LocalCrazyEight(){
        playersInitialisation();
        gameInitialisation();     
    }

    public static void main(String[] args){
        CrazyEightEngine game = new LocalCrazyEight();
        game.play();
    }
    
    
    private void playersInitialisation(){
        //this would have been simpler if player were stored in a java.collection
        for(int i=0; i<getNbOfPlayers(); i++){
            Player p = new Player(PLAYERS_NAMES[i]);
            getInitialPlayers()[i] = p;
        }
    }
    //never used?
    protected static String[] getPlayersNames(){
        return PLAYERS_NAMES;
    }
    
    @Override
    protected int getNbOfPlayers(){
        return NB_OF_PLAYERS;
    }
}

