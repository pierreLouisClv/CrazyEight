package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CardTest {
    @Test
    void sevenOfHeartsShouldEqualSevenOfHearts() {
        Card HeartSeven = new Card("HEART", "SEVEN");
        assertTrue(HeartSeven.equals(new Card("HEART", "SEVEN")));
    }

    @Test
    void aceOfDiamondsShouldBeDiamonds() {
        Card aceOfDiamonds = new Card("ACE", "DIAMONDS");
        assertTrue(aceOfDiamonds.getColor()=="DIAMONDS");
    }

    @Test
    void twoHeartAndFourHeartHaveSameColors(){
        Card twoHeart = new Card("TWO", "HEARTS");
        Card fourHeart = new Card("FOUR", "HEARTS");
        assertTrue(twoHeart.haveSameColor(fourHeart));
    }

    @Test
    void twoHeartAndTwoClubsHaveSameNames(){
        Card twoHeart = new Card("TWO", "HEARTS");
        Card twoClubs = new Card("TWO", "CLUBS");
        assertTrue(twoHeart.haveSameValue(twoClubs));
    }

    @Test
    void conversionStringToCardTest(){
        String str = "JACK,HEARTS";
        Card jackHeart = Card.valueOf(str);
        assertEquals(new Card("JACK","HEARTS"), jackHeart);

    }

    @Test
    void conversionCardToStringTest(){
        Card jackHeart = new Card("JACK", "HEARTS");
        String str = jackHeart.toString();
        assertEquals("JACK,HEARTS", str);
    }

    @Test
    void conversionStringToCardTableTest(){
        boolean tableHaveTheSameCards = false;
        String str = "JACK,HEARTS;TWO,DIAMONDS;THREE,CLUBS";
        Card[] cards = Card.stringToCards(str);

        Card[] cardsTable = {new Card("JACK", "HEARTS"), new Card("TWO", "DIAMONDS"), new Card("THREE", "CLUBS")};
        for(int i=0; i<3;i++){
            if(cards[i].equals(cardsTable[i])){
                tableHaveTheSameCards = true;
            }
            else{
                tableHaveTheSameCards = false;
                break;
            }
        }
        assertTrue(tableHaveTheSameCards);
    }

    @Test
    void conversionTableCardsToString(){
        
        Card[] cards = {new Card("JACK", "HEARTS"), new Card("TWO", "DIAMONDS"), new Card("THREE", "CLUBS")};
        String strCards = Card.cardsToString(cards);

        assertEquals("JACK,HEARTS;TWO,DIAMONDS;THREE,CLUBS", strCards);

    }

}