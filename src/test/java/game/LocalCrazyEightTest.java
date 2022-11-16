package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LocalCrazyEightTest {
    @Test
    void gameContainsFourPlayers() {
        LocalCrazyEight.initialisationPlayers();

        assertEquals(3, LocalCrazyEight.getInitialPlayers().length);

    }

    @Test
    void cardsDistributionAddSevenCardsForEachPlayers(){
        LocalCrazyEight.initialisationPlayers();
        LocalCrazyEight.cardsDistribution();
        assertEquals(7, LocalCrazyEight.getInitialPlayers()[1].handPlayer.size());
        assertEquals(7, LocalCrazyEight.getInitialPlayers()[2].handPlayer.size());
        assertFalse(LocalCrazyEight.getInitialPlayers()[1].handPlayer.get(0).equals(LocalCrazyEight.getInitialPlayers()[2].handPlayer.get(0)));

      

        
    }

    @Test
    void aceTestForCardPowerDeterminer(){
        
        Card nineHeart = new Card("NINE", "HEART");

        
        Card aceHeart = new Card("ACE", "HEART");
        Card aceClubs = new Card("ACE", "CLUBS");
        Card fiveClubs = new Card("FIVE", "CLUBS");

        LocalCrazyEight.getInitialPlayers()[0].getHandPlayer().add(aceHeart);
        LocalCrazyEight.getInitialPlayers()[0].getHandPlayer().add(aceClubs);
        LocalCrazyEight.getInitialPlayers()[0].getHandPlayer().add(fiveClubs);

        
        Card twoDiamonds = new Card("TWO", "DIAMONDS");
        Card threeClubs = new Card("THREE", "CLUBS");

        LocalCrazyEight.getInitialPlayers()[1].getHandPlayer().add(twoDiamonds);
        LocalCrazyEight.getInitialPlayers()[1].getHandPlayer().add(threeClubs);
        
        Card kingHeart = new Card("KING", "HEART");
        Card twoClubs = new Card("TWO", "CLUBS");

        LocalCrazyEight.getInitialPlayers()[2].getHandPlayer().add(kingHeart);
        LocalCrazyEight.getInitialPlayers()[2].getHandPlayer().add(twoClubs);

        int realTurn = 0; //1 DONT PLAY
        LocalCrazyEight.visibleCard = nineHeart;
        LocalCrazyEight.playerTimeToPlay(realTurn);
        LocalCrazyEight.cardPowerDeterminer(LocalCrazyEight.visibleCard);

        assertEquals(1, LocalCrazyEight.getInitialPlayers()[0].getHandPlayer().size());
        /*assertEquals(1, realTurn);*/
        assertEquals(1, LocalCrazyEight.getTurn());
        assertEquals(6, LocalCrazyEight.getInitialPlayers()[1].getHandPlayer().size());



    }

    @BeforeEach
    void setUp(){
        
        LocalCrazyEight.initialisationPlayers();
        LocalCrazyEight.nbOfAcePlayed=0;
    }

    /*@Test
    void aceCanCounterAnOtherAce(){

        Card nineHeart = new Card("NINE", "HEART");

        
        Card aceHeart = new Card("ACE", "HEART");
        Card aceClubs = new Card("ACE", "CLUBS");
        Card fiveClubs = new Card("FIVE", "CLUBS");

        LocalCrazyEight.getInitialPlayers()[0].getHandPlayer().add(aceHeart);
        LocalCrazyEight.getInitialPlayers()[0].getHandPlayer().add(aceClubs);
        LocalCrazyEight.getInitialPlayers()[0].getHandPlayer().add(fiveClubs);

        
        Card aceDiamonds = new Card("ACE", "DIAMONDS");
        Card threeClubs = new Card("THREE", "CLUBS");
        Card aceSpades = new Card("ACE", "SPADES");


        LocalCrazyEight.getInitialPlayers()[1].getHandPlayer().add(aceDiamonds);
        LocalCrazyEight.getInitialPlayers()[1].getHandPlayer().add(threeClubs);
        LocalCrazyEight.getInitialPlayers()[1].getHandPlayer().add(aceSpades);
        

        Card kingHeart = new Card("KING", "HEART");
        Card twoClubs = new Card("TWO", "CLUBS");
        

        LocalCrazyEight.getInitialPlayers()[2].getHandPlayer().add(kingHeart);
        LocalCrazyEight.getInitialPlayers()[2].getHandPlayer().add(twoClubs);
        
        int realTurn = 0; //1 DONT PLAY
        LocalCrazyEight.visibleCard = nineHeart;
        LocalCrazyEight.playerTimeToPlay(realTurn);
        LocalCrazyEight.cardPowerDeterminer(LocalCrazyEight.visibleCard);

        assertEquals(1, LocalCrazyEight.getInitialPlayers()[0].getHandPlayer().size());
        /*assertEquals(1, realTurn);
        assertEquals(2, LocalCrazyEight.getTurn());
        assertEquals(1, LocalCrazyEight.getInitialPlayers()[1].getHandPlayer().size());
        assertEquals(10, LocalCrazyEight.getInitialPlayers()[2].getHandPlayer().size());
    }*/


}
