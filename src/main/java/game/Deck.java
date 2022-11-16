package game;
import java.util.LinkedList;
import java.util.Random;

public class Deck {

    protected Deck(){

    }

    protected static LinkedList<Card> deck;
    private static final String [] NAMES = {"ACE", "KING", "QUEEN", "JACK", "TEN", "NINE", "EIGHT", "SEVEN","SIX", "FIVE", "FOUR", "THREE", "TWO"}; // les 8 valeurs constantes
    private static final String [] COLORS = {"HEARTS", "DIAMONDS", "CLUBS", "SPADES"}; // les 4 couleurs constantes
    private static Random random = new Random();

    static{
        deck = deckCreation();
    }
    public static LinkedList<Card> deckCreation(){
        LinkedList<Card> deck = new LinkedList<>();
        for(String name : NAMES){ // parcours des valeurs constantes
            for(String color : COLORS){ 
                Card carte = new Card(name, color); // appel à la classe Card pour créer un objet Carte
                deck.add(carte); // et l'ajouter au deck
            }
        }
        LinkedList<Card> finalDeck = new LinkedList<>(); //empty
        while(!deck.isEmpty()){
            int index = random.nextInt(deck.size());
            finalDeck.add(deck.get(index));
            deck.remove(index);
        } 
        return finalDeck;
    }

    protected static Card getTopCard(){
        if(deck.isEmpty()){
            deckReshuffler();
        }
        Card topCard = deck.get(deck.size()-1);
        deck.remove(deck.size()-1);

        return topCard;
    }

    protected static void deckReshuffler(){
        /*System.out.println("---------------------");
        System.out.println("The deck is reshuffling...");*/
        Random random = new Random();
        while(!LocalCrazyEight.allCardsPlayed.isEmpty()){
            int index = random.nextInt(LocalCrazyEight.allCardsPlayed.size());
            Deck.deck.add(LocalCrazyEight.allCardsPlayed.get(index));
            LocalCrazyEight.allCardsPlayed.remove(index);
        } 
    }
   
}