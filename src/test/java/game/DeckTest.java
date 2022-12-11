package game;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeckTest {

    Deck newDeck;

    @BeforeEach
    void setUp(){
        newDeck = new Deck();
    }
    
    
    @Test
    void isHeartSevenExist(){
        Card HeartSeven = new Card("SEVEN", "HEARTS");
        assertTrue(newDeck.getDeck().contains(HeartSeven));
    }

    @Test
    void cardsAreUnique(){
        Set<Card> deckSet =new HashSet<Card>(newDeck.getDeck());

        assertEquals(deckSet.size(), newDeck.getDeck().size());
    }

    @Test
    void deckHas52Cards(){
        assertEquals(52, newDeck.getDeck().size());
    }

    @Test
    void getTopCardAddOneCard(){
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(newDeck.getDeckTopCard());
        
        assertEquals(1, hand.size());
    } 

    @Test
    void deckShouldLoseTwoCardsAfterPickingTwoCards(){
        
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(newDeck.getDeckTopCard());
        hand.add(newDeck.getDeckTopCard());

        assertEquals(50, newDeck.getDeck().size());

    }

    @Test
    void clearDeckTest(){
        newDeck.deckCreation();
        newDeck.clearDeck();
        assertEquals(0, newDeck.deckSize());
    }
    
}