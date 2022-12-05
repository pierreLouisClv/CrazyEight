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
        assertTrue(newDeck.cardDeck.contains(HeartSeven));
    }

    @Test
    void cardsAreUnique(){
        Set<Card> deckSet =new HashSet<Card>(newDeck.cardDeck);

        assertTrue(deckSet.size()== newDeck.cardDeck.size());
    }

    @Test
    void deckHas52Cards(){
        assertEquals(52, newDeck.cardDeck.size());
    }

    @Test
    void getTopCardAddOneCard(){
        newDeck.cardDeck = newDeck.deckCreation();
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(newDeck.getTopCard());
        
        assertEquals(1, hand.size());
    } 

    @Test
    void deckShouldLoseTwoCardsAfterPickingTwoCards(){
        
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(newDeck.getTopCard());
        hand.add(newDeck.getTopCard());

        assertEquals(50, newDeck.cardDeck.size());

    }
    
}