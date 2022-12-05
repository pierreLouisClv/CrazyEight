package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LocalCrazyEightTest {
    protected LocalCrazyEight game;

    @BeforeEach
    void setUp(){
        Deck.deck.clear();
        Deck.deck = Deck.deckCreation();
        game = new LocalCrazyEight();
        game.initialisationPlayers();
        /*game.nbOfAcePlayed=0;*/
    }

    @Test
    void takeCardAddTheAmountOfCards(){

        game.takeCard(5);
            assertEquals(5, game.getPlayerTurn().getHandPlayer().size());
        }

    @Test
    void gameReshufflerResetAllCardsPlayed(){
        
        for(int i=0; i<51; i++){
            game.getAllCardsPlayed().add(Deck.getTopCard());
        }
        game.deckReshuffler();
        assertEquals(0, game.getAllCardsPlayed().size());
        assertEquals(52, Deck.deck.size());
    }
     
    
   @Test
    void gameContainsTheTrueAmountOfPlayers() {

        game.initialisationPlayers();

        assertEquals(game.getNbOfPlayers(), game.getInitialPlayers().length);

    }

    @Test
    void cardsDistributionAddSevenCardsForEachPlayers(){
        game.cardsDistribution();
        assertEquals(7, game.getInitialPlayers()[1].getHandPlayer().size());
        assertEquals(7, game.getInitialPlayers()[2].getHandPlayer().size());
        assertFalse(game.getInitialPlayers()[1].getHandPlayer().get(0).equals(game.getInitialPlayers()[2].getHandPlayer().get(0)));        
    }

    @Test
    void itShouldPlayFiveDiamondsAndFiveSpades(){
        Player p = game.getPlayerTurn();

        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card FourSpades = new Card("FOUR", "SPADES");
        Card TwoHeart = new Card("TWO", "HEART");
                
        p.getHandPlayer().clear();
        p.getHandPlayer().add(FiveSpades);
        p.getHandPlayer().add(FiveDiamonds);       
        p.getHandPlayer().add(FourSpades);
        p.getHandPlayer().add(TwoHeart);

        game.lastCardPlayed = FiveHeart;
        game.playSeveralCardsOrOnlyOneCard(p.getPlayableCards(game.lastCardPlayed));
        assertEquals(FiveSpades, game.lastCardPlayed);
        assertEquals(3, game.getAllCardsPlayed().size());
    }

    @Test
    void itShouldPlayThreeFive(){
        Player p = game.getPlayerTurn();

        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card FourHeart = new Card("FOUR", "HEART");
        Card FourSpades = new Card("FOUR", "SPADES");
        Card TwoHeart = new Card("TWO", "HEART");
        Card threeSpades = new Card("THREE", "SPADES");
        
        p.getHandPlayer().clear();
        p.getHandPlayer().add(FiveHeart);
        p.getHandPlayer().add(FourHeart);
        p.getHandPlayer().add(FiveDiamonds);
        p.getHandPlayer().add(FiveSpades);
        p.getHandPlayer().add(FourSpades);
        p.getHandPlayer().add(TwoHeart);

        game.lastCardPlayed = threeSpades;
        game.playSeveralCardsOrOnlyOneCard(p.getPlayableCards(game.lastCardPlayed));
        assertEquals(FiveHeart, game.lastCardPlayed);
        assertEquals(3, game.getAllCardsPlayed().size());
    }

    @Test
    void itShouldPlayFourFive(){
        Player p = game.getPlayerTurn();

        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card FourHeart = new Card("FOUR", "HEART");
        Card FourSpades = new Card("FOUR", "SPADES");
        Card TwoHeart = new Card("TWO", "HEART");
        Card threeSpades = new Card("THREE", "SPADES");
        Card fiveClubs = new Card("FIVE", "CLUBS");

        p.getHandPlayer().clear();
        p.getHandPlayer().add(FiveHeart);
        p.getHandPlayer().add(FourHeart);
        p.getHandPlayer().add(FiveDiamonds);
        p.getHandPlayer().add(FiveSpades);
        p.getHandPlayer().add(FourSpades);
        p.getHandPlayer().add(TwoHeart);
        p.getHandPlayer().add(fiveClubs);


        game.lastCardPlayed = threeSpades;
        game.playSeveralCardsOrOnlyOneCard(p.getPlayableCards(game.lastCardPlayed));
        assertEquals(FiveHeart, game.lastCardPlayed);
        assertEquals(5, game.getAllCardsPlayed().size());
    }

    @Test
    void itShouldPlayOnlyOneCard(){
        Player p = new Player("Pierre");

        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card FourClubs = new Card("FOUR", "HEART");
        Card FourSpades = new Card("FOUR", "CLUBS");
        Card TwoHeart = new Card("TWO", "HEART");
        Card threeSpades = new Card("THREE", "SPADES");
        Card fiveClubs = new Card("FIVE", "CLUBS");

        p.getHandPlayer().clear();
        p.getHandPlayer().add(FourClubs);
        p.getHandPlayer().add(FiveSpades);
        p.getHandPlayer().add(FourSpades);
        p.getHandPlayer().add(TwoHeart);

        game.lastCardPlayed = threeSpades;
        game.playSeveralCardsOrOnlyOneCard(p.getPlayableCards(game.lastCardPlayed));
        assertEquals(FiveSpades, game.lastCardPlayed);
        assertEquals(2, game.getAllCardsPlayed().size());

    }

    @Test
    void eightDeterminTheOptimalColor(){
        Player p = game.getPlayerTurn();

        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card FourClubs = new Card("FOUR", "HEART");
        Card FourSpades = new Card("FOUR", "CLUBS");
        Card TwoHeart = new Card("TWO", "HEART");
        Card threeSpades = new Card("THREE", "SPADES");
        Card fiveClubs = new Card("FIVE", "CLUBS");
        Card eightClubs = new Card("EIGHT", "CLUBS");

        p.getHandPlayer().clear();
        p.getHandPlayer().add(FourClubs);
        p.getHandPlayer().add(FiveSpades);
        p.getHandPlayer().add(FourSpades);
        p.getHandPlayer().add(TwoHeart);
        p.getHandPlayer().add(FiveHeart);
        p.getHandPlayer().add(FiveDiamonds);
        p.getHandPlayer().add(threeSpades);

        game.getCardPower(eightClubs);


        assertEquals("HEART", game.getChoosenColor());

    }

    @Test
    void eightScenarioWithOneCardForEachColor(){
        Player p = game.getPlayerTurn();

        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card eightClubs = new Card("EIGHT", "CLUBS");

        p.getHandPlayer().clear();
        p.getHandPlayer().add(FiveHeart);
        p.getHandPlayer().add(FiveSpades);
        p.getHandPlayer().add(FiveDiamonds);

        game.getCardPower(eightClubs);


        assertEquals("HEART", game.getChoosenColor());
}

    @Test
    void itShouldPlayTheTwoHeart(){
        Player p = game.getPlayerTurn();

        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card FourHeart = new Card("FOUR", "HEART");
        Card FourSpades = new Card("FOUR", "SPADES");
        Card TwoHeart = new Card("TWO", "HEART");
        Card twoClubs = new Card("TWO", "CLUBS");
        
        p.getHandPlayer().clear();
        p.getHandPlayer().add(FiveHeart);
        p.getHandPlayer().add(FourHeart);
        p.getHandPlayer().add(FiveDiamonds);
        p.getHandPlayer().add(FiveSpades);
        p.getHandPlayer().add(FourSpades);
        p.getHandPlayer().add(TwoHeart);

        game.lastCardPlayed = twoClubs;
        game.playSeveralCardsOrOnlyOneCard(p.getPlayableCards(game.lastCardPlayed));
        assertEquals(TwoHeart, game.lastCardPlayed);
        assertEquals(2, game.getAllCardsPlayed().size());
    }

    /*@Test
    void twoAcesMakeTheNextPlayerDrawFourCards(){
        
        Card nineHeart = new Card("NINE", "HEART");       
        Card aceHeart = new Card("ACE", "HEART");
        Card aceClubs = new Card("ACE", "CLUBS");
        Card fiveClubs = new Card("FIVE", "CLUBS");

        game.getInitialPlayers()[0].getHandPlayer().add(aceHeart);
        game.getInitialPlayers()[0].getHandPlayer().add(aceClubs);
        game.getInitialPlayers()[0].getHandPlayer().add(fiveClubs);

        
        Card twoDiamonds = new Card("TWO", "DIAMONDS");
        Card threeClubs = new Card("THREE", "CLUBS");

        game.getInitialPlayers()[1].getHandPlayer().add(twoDiamonds);
        game.getInitialPlayers()[1].getHandPlayer().add(threeClubs);
        
        Card kingHeart = new Card("KING", "HEART");
        Card twoClubs = new Card("TWO", "CLUBS");

        game.getInitialPlayers()[2].getHandPlayer().add(kingHeart);
        game.getInitialPlayers()[2].getHandPlayer().add(twoClubs);

        int realTurn = 0; //1 DONT PLAY
        game.playSeveralCardsOrOnlyOneCard(game.getInitialPlayers()[1].getPlayableCards(nineHeart));

        assertEquals(1, game.getInitialPlayers()[0].getHandPlayer().size());        
        assertEquals(1, game.getTurn());
        assertEquals(6, game.getInitialPlayers()[1].getHandPlayer().size());



    }*/

    /*@Test
    void aceCanCounterAnOtherAce(){

        Card nineHeart = new Card("NINE", "HEART");

        
        Card aceHeart = new Card("ACE", "HEART");
        Card aceClubs = new Card("ACE", "CLUBS");
        Card fiveClubs = new Card("FIVE", "CLUBS");

        game.getInitialPlayers()[0].getHandPlayer().add(aceHeart);
        game.getInitialPlayers()[0].getHandPlayer().add(aceClubs);
        game.getInitialPlayers()[0].getHandPlayer().add(fiveClubs);

        
        Card aceDiamonds = new Card("ACE", "DIAMONDS");
        Card threeClubs = new Card("THREE", "CLUBS");
        Card aceSpades = new Card("ACE", "SPADES");


        game.getInitialPlayers()[1].getHandPlayer().add(aceDiamonds);
        game.getInitialPlayers()[1].getHandPlayer().add(threeClubs);
        game.getInitialPlayers()[1].getHandPlayer().add(aceSpades);
        

        Card kingHeart = new Card("KING", "HEART");
        Card twoClubs = new Card("TWO", "CLUBS");
        

        game.getInitialPlayers()[2].getHandPlayer().add(kingHeart);
        game.getInitialPlayers()[2].getHandPlayer().add(twoClubs);
        
        int realTurn = 0; //1 DONT PLAY
        LocalCrazyEight.visibleCard = nineHeart;
        LocalCrazyEight.playerTimeToPlay(realTurn);
        LocalCrazyEight.cardPowerDeterminer(LocalCrazyEight.visibleCard);

        assertEquals(1, game.getInitialPlayers()[0].getHandPlayer().size());

        assertEquals(2, LocalCrazyEight.getTurn());
        assertEquals(1, game.getInitialPlayers()[1].getHandPlayer().size());
        assertEquals(10, game.getInitialPlayers()[2].getHandPlayer().size());
    }*/

}
