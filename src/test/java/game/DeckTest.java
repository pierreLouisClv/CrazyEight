package game;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeckTest {

    
    
    @Test
    void isHeartSevenExist(){
        Card HeartSeven = new Card("SEVEN", "HEARTS");
        assertTrue(Deck.deckCreation().contains(HeartSeven));
    }

    @BeforeEach
    void setUp(){
        Deck.deck.clear();
    }

    @Test
    void cardsAreUnique(){
        Set<Card> s =new HashSet<Card>(Deck.deck);

        assertTrue(s.size()== Deck.deck.size());
    }

    @Test
    void deckHas52Cards(){
        assertEquals(52, Deck.deckCreation().size());
    }

    @Test
    void getTopCardAddOneCard(){
        Deck.deck = Deck.deckCreation();
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(Deck.getTopCard());
        
        assertEquals(1, hand.size());
    } 

    @Test
    void deckShouldLoseTwoCardsAfterPickingTwoCards(){
        Deck.deck = Deck.deckCreation();
        
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(Deck.getTopCard());
        hand.add(Deck.getTopCard());

        assertEquals(50, Deck.deck.size());

    }
    
}