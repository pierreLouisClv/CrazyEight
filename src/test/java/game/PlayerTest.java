package game;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PlayerTest {
    protected LocalCrazyEight game;

    @BeforeEach
    void setUp(){
        game = new LocalCrazyEight();
    }

    @Test
    void getPlayableCardsTest(){
        Player p = new Player("Naki");
        Card fiveHeart = new Card("FIVE", "HEARTS");
        game.lastCardPlayed = fiveHeart;

        Card TwoHeart = new Card("TWO", "HEARTS");
        Card FourSpades = new Card("FOUR", "SPADES");
        Card ThreeHeart = new Card("THREE", "HEARTS");

        p.getHandPlayer().add(TwoHeart);
        p.getHandPlayer().add(FourSpades);
        p.getHandPlayer().add(ThreeHeart);

        LinkedList<Card> hand = new LinkedList<>();
        hand.add(TwoHeart);
        hand.add(ThreeHeart);

        assertTrue(hand.get(0).equals(p.getPlayableCards(game.lastCardPlayed).get(0)));
        assertEquals(hand.size(), p.getPlayableCards(game.lastCardPlayed).size());

    }

    @Test
    void nbOfCombinationOfTheCardTest(){

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

        assertEquals(2, p.nbOfCombinationOfTheCard(TwoHeart));
        assertEquals(1, p.nbOfCombinationOfTheCard(ValetHeart));
        assertEquals(3, p.nbOfCombinationOfTheCard(ThreeHeart));
        
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

        assertEquals(ThreeHeart, p.makeTheBestChoice(hand, game.lastCardPlayed));
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

        assertTrue(FourHeart.equals(p.makeTheBestChoice(hand, game.lastCardPlayed)));
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
        
        p.getHandPlayer().clear();
        p.getHandPlayer().add(FiveHeart);
        p.getHandPlayer().add(FourHeart);
        p.getHandPlayer().add(FiveDiamonds);
        p.getHandPlayer().add(FiveSpades);
        p.getHandPlayer().add(FourSpades);
        p.getHandPlayer().add(TwoHeart);
        game.lastCardPlayed = FiveClubs;

        assertEquals(FiveSpades,p.makeTheBestChoice(p.getPlayableCards(game.lastCardPlayed), game.lastCardPlayed));
        assertTrue(FiveHeart.equals(p.cardPlayedAtTheEndOfTheCombination(p.makeTheBestChoice(p.getPlayableCards(game.lastCardPlayed), game.lastCardPlayed), 3)));
    }


}