package game;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Deck {

    protected Deck(){
        cardDeck = deckCreation();
    }

    protected Queue<Card> cardDeck;
    private Random random = new Random();

    public Queue<Card> deckCreation(){
        LinkedList<Card> initialDeck = new LinkedList<>();
        for(String value : Card.getAllCardsValues()){ // parcours des valeurs constantes
            for(String color : Card.getAllCardsColors()){ 
                Card carte = new Card(value, color); // appel à la classe Card pour créer un objet Carte
                initialDeck.add(carte); // et l'ajouter au deck
            }
        }
        Queue<Card> shuffledDeck = new LinkedList<>(); //empty
        while(!initialDeck.isEmpty()){
            int index = random.nextInt(initialDeck.size());
            shuffledDeck.add(initialDeck.get(index));
            initialDeck.remove(index);
        } 
        return shuffledDeck;
    }

    protected Card getTopCard(){
        return this.cardDeck.poll();
    }

    protected void clearDeck(){
        int size = this.cardDeck.size();
        for(int i=0; i<size; i++){
            this.cardDeck.remove();
        }
    }

    protected int deckSize(){
        return this.cardDeck.size();
    }

    protected Queue<Card> getDeck(){
        return cardDeck;
    }
}