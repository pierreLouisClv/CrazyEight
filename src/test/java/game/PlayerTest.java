package game;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class PlayerTest {
    
    @Test
    void takeCardAddTheAmountOfCards(){
        Player p = new Player("Naki");
        
            p.takeCard(5);
            assertEquals(5, p.getHandPlayer().size());
        }

    @Test
    void getPlayableCardsTest(){
        Player p = new Player("Naki");
        p.handPlayer.clear();
        Card fiveHeart = new Card("FIVE", "HEARTS");
        LocalCrazyEight.allCardsPlayed.add(fiveHeart);
        LocalCrazyEight.visibleCard = fiveHeart;

        Card TwoHeart = new Card("TWO", "HEARTS");
        Card FourSpades = new Card("FOUR", "SPADES");
        Card ThreeHeart = new Card("THREE", "HEARTS");

        p.getHandPlayer().add(TwoHeart);
        p.getHandPlayer().add(FourSpades);
        p.getHandPlayer().add(ThreeHeart);

        LinkedList<Card> hand = new LinkedList<>();
        hand.add(TwoHeart);
        hand.add(ThreeHeart);

        assertTrue(hand.get(0).equals(p.getPlayableCards().get(0)));
        assertEquals(hand.size(), p.getPlayableCards().size());

    }

    @Test
    void hasCombinationTest(){

        Player p = new Player("Naki");
        LocalCrazyEight.allCardsPlayed.clear();
        p.handPlayer.clear();
        
        Card TwoHeart = new Card("TWO", "HEART");
        Card ThreeHeart = new Card("THREE", "HEART");
        Card ValetHeart = new Card("VALET", "HEART");
        p.getHandPlayer().add(ThreeHeart);
        p.getHandPlayer().add(TwoHeart);
        p.getHandPlayer().add(ValetHeart);
        
        p.getHandPlayer().add(new Card("SEVEN", "SPADES"));
        p.getHandPlayer().add(new Card("TWO", "SPADES"));
        p.getHandPlayer().add(new Card("THREE", "SPADES"));
        p.getHandPlayer().add(new Card("THREE", "DIAMONDS"));

        LinkedList<Card> hand = new LinkedList<Card>();

        hand.add(TwoHeart);
        hand.add(ValetHeart);
        hand.add(ThreeHeart);

        assertEquals(2, p.hasCombination(TwoHeart));
        assertEquals(1, p.hasCombination(ValetHeart));
        assertEquals(3, p.hasCombination(ThreeHeart));
        
    }

    @Test
    void bestChoiceShouldBeThree(){
        Player p = new Player("Naki");
        
        Card TwoHeart = new Card("TWO", "HEART");
        Card ThreeHeart = new Card("THREE", "HEART");
        Card ValetHeart = new Card("VALET", "HEART");
        p.getHandPlayer().add(ThreeHeart);
        p.getHandPlayer().add(TwoHeart);
        p.getHandPlayer().add(ValetHeart);
        
        p.getHandPlayer().add(new Card("SEVEN", "SPADES"));
        p.getHandPlayer().add(new Card("TWO", "SPADES"));
        p.getHandPlayer().add(new Card("THREE", "SPADES"));
        p.getHandPlayer().add(new Card("THREE", "DIAMONDS"));

        LinkedList<Card> hand = new LinkedList<Card>();

        hand.add(TwoHeart);
        hand.add(ValetHeart);
        hand.add(ThreeHeart);

        assertEquals(ThreeHeart, p.getBestChoice(hand));
    }


    @Test
    void bestChoiceDoesNotChooseEight(){
        Player p = new Player("Naki");

        Card EightHeart = new Card("EIGHT", "HEART");
        Card EightDiamonds = new Card("EIGHT", "DIAMONDS");
        Card EightSpades = new Card("EIGHT", "SPADES");
        Card FourHeart = new Card("FOUR", "HEART");
        Card FourSpades = new Card("FOUR", "SPADES");
        Card TwoHeart = new Card("TWO", "HEART");

        p.getHandPlayer().add(EightHeart);
        p.getHandPlayer().add(EightDiamonds);
        p.getHandPlayer().add(EightSpades);
        p.getHandPlayer().add(FourHeart);
        p.getHandPlayer().add(FourSpades);
        p.getHandPlayer().add(TwoHeart);
        p.getHandPlayer().add(new Card("THREE", "DIAMONDS"));

        LinkedList<Card> hand = new LinkedList<Card>();

        hand.add(TwoHeart);
        hand.add(EightDiamonds);
        hand.add(EightSpades);
        hand.add(EightHeart);
        hand.add(FourHeart);

        assertTrue(FourHeart.equals(p.getBestChoice(hand)));
    }

    @Test
    void itShouldChooseFiveOfHearts(){
        Player p = new Player("Pierre");
        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card FourHeart = new Card("FOUR", "HEART");
        Card FourSpades = new Card("FOUR", "SPADES");
        Card TwoHeart = new Card("TWO", "HEART");
        Card FiveClubs = new Card("FIVE", "CLUBS");
        
        p.handPlayer.clear();
        p.handPlayer.add(FiveHeart);
        p.handPlayer.add(FourHeart);
        p.handPlayer.add(FiveDiamonds);
        p.handPlayer.add(FiveSpades);
        p.handPlayer.add(FourSpades);
        p.handPlayer.add(TwoHeart);
        LocalCrazyEight.visibleCard = FiveClubs;

        assertTrue(FiveDiamonds.equals(p.getBestChoice(p.getPlayableCards())));
        assertTrue(FiveHeart.equals(p.finalCardToPlay(p.getBestChoice(p.getPlayableCards()), 3)));
    }

    @Test
    void itShouldPlayTheTwoHeart(){
        Player p = new Player("Pierre");
        LocalCrazyEight.allCardsPlayed.clear();

        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card FourHeart = new Card("FOUR", "HEART");
        Card FourSpades = new Card("FOUR", "SPADES");
        Card TwoHeart = new Card("TWO", "HEART");
        Card twoClubs = new Card("TWO", "CLUBS");
        
        p.handPlayer.clear();
        p.handPlayer.add(FiveHeart);
        p.handPlayer.add(FourHeart);
        p.handPlayer.add(FiveDiamonds);
        p.handPlayer.add(FiveSpades);
        p.handPlayer.add(FourSpades);
        p.handPlayer.add(TwoHeart);

        LocalCrazyEight.visibleCard = twoClubs;
        p.playCard(p.getPlayableCards());
        assertEquals(TwoHeart, LocalCrazyEight.visibleCard);
        assertEquals(1, LocalCrazyEight.allCardsPlayed.size());
    }

    @Test
    void itShouldPlayFiveDiamondsAndFiveSpades(){
        Player p = new Player("Pierre");
        LocalCrazyEight.allCardsPlayed.clear();

        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card FourHeart = new Card("FOUR", "HEART");
        Card FourSpades = new Card("FOUR", "SPADES");
        Card TwoHeart = new Card("TWO", "HEART");
        Card twoClubs = new Card("TWO", "CLUBS");
        
        p.handPlayer.clear();
        p.handPlayer.add(FiveSpades);
        p.handPlayer.add(FiveDiamonds);       
        p.handPlayer.add(FourSpades);
        p.handPlayer.add(TwoHeart);

        LocalCrazyEight.visibleCard = FiveHeart;
        p.playCard(p.getPlayableCards());
        assertEquals(FiveSpades, LocalCrazyEight.visibleCard);
        assertEquals(2, LocalCrazyEight.allCardsPlayed.size());
    }

    @Test
    void itShouldPlayThreeFive(){
        Player p = new Player("Pierre");
        LocalCrazyEight.allCardsPlayed.clear();

        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card FourHeart = new Card("FOUR", "HEART");
        Card FourSpades = new Card("FOUR", "SPADES");
        Card TwoHeart = new Card("TWO", "HEART");
        Card threeSpades = new Card("THREE", "SPADES");
        
        p.handPlayer.clear();
        p.handPlayer.add(FiveHeart);
        p.handPlayer.add(FourHeart);
        p.handPlayer.add(FiveDiamonds);
        p.handPlayer.add(FiveSpades);
        p.handPlayer.add(FourSpades);
        p.handPlayer.add(TwoHeart);

        LocalCrazyEight.visibleCard = threeSpades;
        p.playCard(p.getPlayableCards());
        assertEquals(FiveHeart, LocalCrazyEight.visibleCard);
        assertEquals(3, LocalCrazyEight.allCardsPlayed.size());
    }

    @Test
    void itShouldPlayFourFive(){
        Player p = new Player("Pierre");
        LocalCrazyEight.allCardsPlayed.clear();

        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card FourHeart = new Card("FOUR", "HEART");
        Card FourSpades = new Card("FOUR", "SPADES");
        Card TwoHeart = new Card("TWO", "HEART");
        Card threeSpades = new Card("THREE", "SPADES");
        Card fiveClubs = new Card("FIVE", "CLUBS");

        p.handPlayer.clear();
        p.handPlayer.add(FiveHeart);
        p.handPlayer.add(FourHeart);
        p.handPlayer.add(FiveDiamonds);
        p.handPlayer.add(FiveSpades);
        p.handPlayer.add(FourSpades);
        p.handPlayer.add(TwoHeart);
        p.handPlayer.add(fiveClubs);


        LocalCrazyEight.visibleCard = threeSpades;
        p.playCard(p.getPlayableCards());
        assertEquals(FiveHeart, LocalCrazyEight.visibleCard);
        assertEquals(4, LocalCrazyEight.allCardsPlayed.size());
    }

    @Test
    void itShouldPlayOnlyOneCard(){
        Player p = new Player("Pierre");
        LocalCrazyEight.allCardsPlayed.clear();

        Card FiveHeart = new Card("FIVE", "HEART");
        Card FiveDiamonds = new Card("FIVE", "DIAMONDS");
        Card FiveSpades = new Card("FIVE", "SPADES");
        Card FourClubs = new Card("FOUR", "HEART");
        Card FourSpades = new Card("FOUR", "CLUBS");
        Card TwoHeart = new Card("TWO", "HEART");
        Card threeSpades = new Card("THREE", "SPADES");
        Card fiveClubs = new Card("FIVE", "CLUBS");

        p.handPlayer.clear();
        p.handPlayer.add(FourClubs);
        p.handPlayer.add(FiveSpades);
        p.handPlayer.add(FourSpades);
        p.handPlayer.add(TwoHeart);

        LocalCrazyEight.visibleCard = threeSpades;
        p.playCard(p.getPlayableCards());
        assertEquals(FiveSpades, LocalCrazyEight.visibleCard);
        assertEquals(1, LocalCrazyEight.allCardsPlayed.size());

    }
}