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
        for(int i=0; i<getNbOfPlayers(); i++){
            Player p = new Player(PLAYERS_NAMES[i]);
            getInitialPlayers()[i] = p;
        }
    }
    
    protected static String[] getPlayersNames(){
        return PLAYERS_NAMES;
    }
    
    @Override
    protected int getNbOfPlayers(){
        return NB_OF_PLAYERS;
    }
}

