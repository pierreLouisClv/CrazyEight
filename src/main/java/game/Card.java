package game;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Card {
    private static final String EIGHT = "EIGHT";
    private static final String [] VALUES = {"ACE", "KING", "QUEEN", "JACK", "TEN", "NINE", EIGHT, "SEVEN","SIX", "FIVE", "FOUR", "THREE", "TWO"}; // les 8 valeurs constantes
    private static final String [] COLORS = {"HEARTS", "DIAMONDS", "CLUBS", "SPADES"}; // les 4 couleurs constantes
    
    private String value; //value of the card
    private String color; //color of the card
    
    protected Card(String value, String color){ //constructeur qui récupère les informations données par les paramètres
        this.value = value; 
        this.color = color;
    }

    public static void cardPrinter(Card card){
        System.out.println(card.getValue() +" OF "+ card.getColor());
    }

    protected String getValue(){
        return this.value;
    }

    protected String getColor(){
        return this.color;
    }

    @Override
    public boolean equals(Object card){
        return this.haveSameColor((Card)card) && this.haveSameValue((Card)card);
    }

    protected boolean haveSameColor(Card card){
        return this.getColor().equals(card.getColor());
    }

    protected boolean haveSameValue(Card card){
        return this.getValue().equals(card.getValue());
    }

    public static Card valueOf(String str) {
        String[] card = str.split(",");
        return new Card(card[0], card[1]);

    }

    @Override
    public String toString(){
        return "" + this.getValue() + "," + this.getColor();
    }

    public static String cardsToString(Card[] cards) {
        return Arrays.stream(cards).map(Card::toString).collect(Collectors.joining(";"));
    }

    public static Card[] stringToCards(String cards) {
        if (cards.isEmpty()) {
            return new Card[0];
        }
        return (Card[]) Arrays.stream(cards.split(";")).map(Card::valueOf).toArray(Card[]::new);
    }

    protected static String[] getAllCardsValues(){
        return VALUES;
    }

    protected static String[] getAllCardsColors(){
        return COLORS;
    }

    protected static String getMostPowerfullValue(){
        return EIGHT;
    }
}