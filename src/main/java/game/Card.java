package game;

public class Card {
    private String name; //name of the card
    private String color; //color of the card
    
    protected Card(String name, String color){ //constructeur qui récupère les informations données par les paramètres
        this.name = name; 
        this.color = color;
    }

    public static void cardPrinter(Card card){
        System.out.println(getName(card)+" OF "+getColor(card));
    }

    protected static String getName(Card card){
        return card.name;
    }

    protected static String getColor(Card card){
        return card.color;
    }

    @Override
    public boolean equals(Object card){
        return Card.sameColor((Card)card, this) && Card.sameName((Card)card, this);
    }

    protected static boolean sameColor(Card card1, Card card2){
        return getColor(card1).equals(getColor(card2));
    }

    protected static boolean sameName(Card card1, Card card2){
        return getName(card1).equals(getName(card2));
    }


}