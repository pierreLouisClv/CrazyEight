package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CardTest {
    @Test
    void sevenOfHeartsShouldEqualSevenOfHearts() {
        Card HeartSeven = new Card("HEARTS", "SEVEN");
        assertEquals(HeartSeven, new Card("HEARTS", "SEVEN"));
    }

    @Test
    void aceOfDiamondsShouldBeDiamonds() {
        Card aceOfDiamonds = new Card("ACE", "DIAMONDS");
        assertEquals("DIAMONDS", aceOfDiamonds.getColor());
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
}