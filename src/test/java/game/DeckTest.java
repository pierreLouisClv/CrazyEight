package game;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DeckTest {
    @Test
    void isHeartSevenExist(){
        Card HeartSeven = new Card("SEVEN", "HEARTS");
        assertTrue(Deck.deckCreation().contains(HeartSeven));
    }

    @Test
    void cardsAreUnique(){
        LinkedList<Card> newDeck = Deck.deckCreation();
        Set<Card> s =new HashSet<Card>(newDeck);

        assertTrue(s.size()== newDeck.size());
    }

    @Test
    void deckHas52Cards(){
        Deck deck1 = new Deck();
        assertEquals(18, deck1.deck.size());
    }

    @Test
    void getTopCardAddOneCard(){
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(Deck.getTopCard());
        
        assertEquals(1, hand.size());
    } 

    @Test
    void deckShouldLoseTwoCardsAfterPickingTwoCards(){
        Deck deck = new Deck();
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(deck.getTopCard());
        hand.add(deck.getTopCard());

        assertEquals(18, deck.deck.size());

    }
    
}