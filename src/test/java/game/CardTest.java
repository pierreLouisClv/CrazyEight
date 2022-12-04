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
}