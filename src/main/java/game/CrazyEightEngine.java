package game;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class CrazyEightEngine {
    private static final int NUMBER_OF_CARDS_FOR_EACH_PLAYERS = 8;
    //you could have made the code easier by using a java collection class
    private Player[] initialPlayers = new Player[getNbOfPlayers()];
    private Deck gameDeck;
    private Deque<Card> allCardsPlayed = new LinkedList<>();
    protected Card lastCardPlayed;

    private int turn;
    private int index; // jack power
    private int nbOfAcePlayed; // ace power
    private int nbOfTurnToPass; // seven power
    private String choosenColor; // eight power
    private boolean replay; // ten power

    protected Random random = new Random();

    protected void play() {
        cardsDistribution();
        while (true) {

            Player playerTurn = getPlayerTurn();

            System.out.println("---------------------");
            System.out.print("The last card played: ");
            Card.cardPrinter(lastCardPlayed);

            System.out.println("Next player: " + playerTurn.getName());
            playerPlayOrPass();

            if (playerTurn.hasWon()) {
                System.out.println("THE WINNER IS " + playerTurn.getName());
                break;
            }
        }
    }

    protected void playerPlayOrPass() {
        Player playerTurn = this.getPlayerTurn();

        /* Si un SEPT a été posé le joueur passe son tour */
        if (this.nbOfTurnToPass != 0) {
            this.nbOfTurnToPass--;
        }

        /* si la carte jouée juste avant est un AS ou un HUIT */
        else if (isTheLastCardPlayedPowerfull(this.lastCardPlayed)) {
        } else {
            List<Card> playableCards = playerTurn.getPlayableCards(this.lastCardPlayed);
            if (playableCards.isEmpty()) {
                takeCard(1);
            } else {
                playSeveralCardsOrOnlyOneCard(playableCards);
            }
        }
        /* Si le joueur vient de poser un DIX, il peut rejouer */
        if (this.replay) {
            this.replay = false;
        } else {
            /* passe au joueur suivant */
            this.nextPlayer();
        }
    }

    //a function returning a boolean should not modify the object attributes.
    //this one does a lot of things and is very complex for obscure reasons
    protected boolean isTheLastCardPlayedPowerfull(Card lastCardPlayed) {
        Player playerTurn = this.getPlayerTurn();
//should have used enum to each equals procedures
        if (lastCardPlayed.getValue().equals("ACE") && this.nbOfAcePlayed > 0) {
            List<Card> allAceOfThePlayer = new LinkedList<>();
            for (Card card : playerTurn.getHandPlayer()) {
                if (card.getValue().equals("ACE")) {
                    allAceOfThePlayer.add(card);
                }
            }
            /* le joueur n'a pas d'AS pour contrer le pouvoir */
            if (allAceOfThePlayer.isEmpty()) {
                takeCard(this.nbOfAcePlayed * 2);
                this.nbOfAcePlayed = 0;
            } else {
                playSeveralCardsOrOnlyOneCard(allAceOfThePlayer);
            }

            /* renvoie true pour signaler que le joueur a du s'adapter à la carte précédente */
            return true;

        } else if (lastCardPlayed.getValue().equals(Card.getMostPowerfullValue())) {
            /* créer une carte qui possède la couleur choisie par le joueur qui a posé le HUIT */
            Card virtualNewEight = new Card(Card.getMostPowerfullValue(), this.choosenColor);
            /* cela impact les cartes jouables du joueur en cours */
            List<Card> playableCard = playerTurn.getPlayableCards(virtualNewEight);
            if (playableCard.isEmpty()) {
                takeCard(1);
            } else {
                playSeveralCardsOrOnlyOneCard(playableCard);
            }

            return true;
        }

        return false;
    }

    protected void getCardPower(Card card) {
        switch (card.getValue()) {
            case "ACE": /*itération du nombre d'ace en jeu*/
                this.nbOfAcePlayed++;
                break;

            case "EIGHT": // définit la couleur choisie par le joueur
                this.choosenColor = this.getPlayerTurn().determinColorAfterAnEight();
                break;

            case "TEN":
                this.replay = true;
                break;

            case "JACK":
                this.reverse();
                break;

            case "SEVEN":
                this.nbOfTurnToPass += 1;
                break;

            default://why?
                break;
        }
    }

    public void takeCard(int nbOfCardsPlayerMustToTake) {
        Player playerTurn = this.getPlayerTurn();
        Card card;
        System.out.println(playerTurn.getName() + " takes " + nbOfCardsPlayerMustToTake + " card ...");

        for (int i = 0; i < nbOfCardsPlayerMustToTake; i++) {
            /* renouvèle le deck si le Deck n'a plus de carte */
            //use .size instead
            if ((card = gameDeck.getDeckTopCard()) == null) {
                deckReshuffler();
            } else {
                playerTurn.getHandPlayer().add(card);
            }
        }
    }

    protected void playSeveralCardsOrOnlyOneCard(List<Card> playableCards) {
        Player playerTurn = this.getPlayerTurn();

        int size = playableCards.size();
        /* définit une carte par défaut */
        //again a queue would have been preferred
        Card cardPlayed = playableCards.get(0);
        int combination = playerTurn.nbOfCombinationOfTheCard(cardPlayed);
        if (size > 1) {
            cardPlayed = playerTurn.makeTheBestChoice(playableCards, this.lastCardPlayed);
        } else {
            if (combination == 1) {
                System.out.println(playerTurn.getName() + " plays 1 card...");
                oneCardIsPlayed(cardPlayed);
                return;
            }
        }
        if ((combination == 1) || cardPlayed.getValue().equals(Card.getMostPowerfullValue())) {
            System.out.println(playerTurn.getName() + " plays 1 card...");
            oneCardIsPlayed(cardPlayed);
        } else if (combination == 2) {
            System.out.println(playerTurn.getName() + " plays 2 cards...");
            playTwoCards(playerTurn, cardPlayed, playableCards);
        } else {
            playThreeOrFourCards(playerTurn, cardPlayed, combination);
        }

    }

    protected void playTwoCards(Player playerTurn, Card cardPlayed, List<Card> playableCards) {

        oneCardIsPlayed(cardPlayed);
        //playableCards should have been a queue if you are just using the tip
        Card theSecondCardToPlay = playableCards.get(0);
        for (Card card : playerTurn.getHandPlayer()) {
            if (card.haveSameValue(cardPlayed) && !(card.haveSameColor(cardPlayed))) {
                theSecondCardToPlay = card;
                break;
            }
        }
        oneCardIsPlayed(theSecondCardToPlay);
    }

    protected void playThreeOrFourCards(Player playerTurn, Card cardPlayed, int combination) {
        Card finalCardToPlay = playerTurn.cardPlayedAtTheEndOfTheCombination(cardPlayed, combination);
        List<Card> otherCardsPlayed = new LinkedList<>();
        for (Card card : playerTurn.getHandPlayer()) {
            if (card.haveSameValue(finalCardToPlay) && !(card.haveSameColor(finalCardToPlay))) {
                otherCardsPlayed.add(card); // secondCard = card
                if (combination == 3) { //you should have used a constant here
                    System.out.println(playerTurn.getName() + " plays 3 cards...");
                    break;
                } else {
                    System.out.println(playerTurn.getName() + " plays 4 cards...");
                    combination--;
                }
            }
        }
        for (Card card : otherCardsPlayed) {
            oneCardIsPlayed(card);
        }
        oneCardIsPlayed(finalCardToPlay);
    }

    protected void oneCardIsPlayed(Card cardPlayed) {
        Player playerTurn = this.getPlayerTurn();
        this.allCardsPlayed.add(cardPlayed);
        Card.cardPrinter(cardPlayed);
        playerTurn.getHandPlayer().remove(cardPlayed);

        /* Appel du pouvoir de la carte quand elle est jouée */
        getCardPower(cardPlayed);
        this.lastCardPlayed = this.allCardsPlayed.getLast();
    }

    protected void gameInitialisation() {
        gameDeck = new Deck();
        allCardsPlayed.add(gameDeck.getDeckTopCard());
        lastCardPlayed = allCardsPlayed.getLast();

        choosenColor = allCardsPlayed.getLast().getColor();
        turn = random.nextInt(getNbOfPlayers() - 1);
        index = 1;
        nbOfAcePlayed = 0;
        nbOfTurnToPass = 0;
        replay = false;
    }

    protected void cardsDistribution() {
        for (Player p : initialPlayers) {
            for (int i = 0; i < NUMBER_OF_CARDS_FOR_EACH_PLAYERS; i++) {
                p.getHandPlayer().add(gameDeck.getDeckTopCard());
            }
        }
    }

    protected void deckReshuffler() {
        Random random = new Random();
        LinkedList<Card> cards = (LinkedList<Card>) allCardsPlayed;
        while (!cards.isEmpty()) {
            int randomNumber = random.nextInt(cards.size());
            gameDeck.getDeck().add(cards.get(randomNumber));
            cards.remove(randomNumber);
        }
    }

    private int determinationOfRealTurn(int turn) {
        int nbOfPlayers = getNbOfPlayers();
        if (turn % nbOfPlayers >= 0) {
            return turn % nbOfPlayers;
        } else {
            return turn % nbOfPlayers + nbOfPlayers;
        }

    }

    protected Player getPlayerTurn() {
        return this.initialPlayers[determinationOfRealTurn(turn)];
    }

    protected Deck getGameDeck() {
        return this.gameDeck;
    }

    //this index is mysterious to me, i don't get why it has negative values
    protected void reverse() {
        this.index = - this.index;
    }

    protected void nextPlayer() {
        this.turn += this.getIndex();
    }

    protected Deque<Card> getAllCardsPlayed() {
        return this.allCardsPlayed;
    }

    protected Player[] getInitialPlayers() {
        return this.initialPlayers;
    }

    protected abstract int getNbOfPlayers();

    protected int getIndex() {
        return this.index;
    }

    protected int getTurn() {
        return this.turn;
    }

    protected String getChoosenColor() {
        return this.choosenColor;
    }

    protected int getNbOfAcePlayed() {
        return this.nbOfAcePlayed;
    }
}
