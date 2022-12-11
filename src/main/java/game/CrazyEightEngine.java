package game;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public abstract class CrazyEightEngine {
    private static final int NUMBER_OF_CARDS_FOR_EACH_PLAYERS = 8;

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

        if (this.nbOfTurnToPass != 0) { // Si un 7 a été posé le joueur passe son tour
            this.nbOfTurnToPass--;
        }

        else if (isTheLastCardPlayedPowerfull(this.lastCardPlayed)) {
        }

        else {
            LinkedList<Card> playableCards = playerTurn.getPlayableCards(this.lastCardPlayed);
            if (playableCards.isEmpty()) {
                takeCard(1);
            } else {
                playSeveralCardsOrOnlyOneCard(playableCards);
            }
        }
        if (this.replay) {
            this.replay = false;
        } else {
            this.nextPlayer();
        }
    }

    protected boolean isTheLastCardPlayedPowerfull(Card lastCardPlayed) {
        Player playerTurn = this.getPlayerTurn();

        if (lastCardPlayed.getValue().equals("ACE") && this.nbOfAcePlayed > 0) {
            LinkedList<Card> allAceOfThePlayer = new LinkedList<>();
            for (Card card : playerTurn.getHandPlayer()) {
                if (card.getValue().equals("ACE")) {
                    allAceOfThePlayer.add(card);
                }
            }
            if (allAceOfThePlayer.isEmpty()) {
                takeCard(this.nbOfAcePlayed * 2);
                this.nbOfAcePlayed = 0;
            } else {
                playSeveralCardsOrOnlyOneCard(allAceOfThePlayer);
            }

            return true;

        }

        else if (lastCardPlayed.getValue().equals(Card.getMostPowerfullValue())) {
            Card virtualNewEight = new Card(Card.getMostPowerfullValue(), this.choosenColor);
            LinkedList<Card> playableCard = playerTurn.getPlayableCards(virtualNewEight);
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
            case "ACE":
                this.nbOfAcePlayed++; // itération du nombre d'ace en jeu
                break;

            case "EIGHT":
                this.choosenColor = this.getPlayerTurn().determinColorAfterAnEight(); // définit la couleur choisie par
                                                                                      // le joueur
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

            default:
                break;
        }
    }

    public void takeCard(int nbOfCardsPlayerMustToTake) {
        Player playerTurn = this.getPlayerTurn();
        Card card;
        System.out.println(playerTurn.getName() + " takes " + nbOfCardsPlayerMustToTake + " card ...");

        for (int i = 0; i < nbOfCardsPlayerMustToTake; i++) {
            if ((card = gameDeck.getDeckTopCard()) == null) {
                deckReshuffler();
            } else {
                playerTurn.getHandPlayer().add(card);
            }
        }
    }

    protected void playSeveralCardsOrOnlyOneCard(LinkedList<Card> playableCards) {
        Player playerTurn = this.getPlayerTurn();

        int size = playableCards.size();
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

    protected void playTwoCards(Player playerTurn, Card cardPlayed, LinkedList<Card> playableCards) {

        oneCardIsPlayed(cardPlayed);
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
        LinkedList<Card> otherCardsPlayed = new LinkedList<>();
        for (Card card : playerTurn.getHandPlayer()) {
            if (card.haveSameValue(finalCardToPlay) && !(card.haveSameColor(finalCardToPlay))) {
                otherCardsPlayed.add(card); // secondCard = card
                if (combination == 3) {
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
        getCardPower(cardPlayed);
        this.lastCardPlayed = this.allCardsPlayed.getLast();
    }

    private int determinationOfRealTurn(int turn) {
        int nbOfPlayers = getNbOfPlayers();
        if (turn % nbOfPlayers >= 0) {
            return turn % nbOfPlayers;
        } else {
            return turn % nbOfPlayers + nbOfPlayers;
        }

    }

    protected void cardsDistribution() {
        for (Player p : initialPlayers) {
            for (int i = 0; i < NUMBER_OF_CARDS_FOR_EACH_PLAYERS; i++) {
                p.getHandPlayer().add(gameDeck.getDeckTopCard());
            }
        }
    }

    protected void gameInitialisation() {
        gameDeck = new Deck();
        allCardsPlayed.add(gameDeck.getDeckTopCard());
        lastCardPlayed = allCardsPlayed.getLast();

        choosenColor = allCardsPlayed.getLast().getColor();
        index = 1;
        nbOfAcePlayed = 0;
        nbOfTurnToPass = 0;
        replay = false;
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

    protected Player getPlayerTurn() {
        return this.initialPlayers[determinationOfRealTurn(turn)];
    }

    protected Deck getGameDeck() {
        return this.gameDeck;
    }

    protected void reverse() {
        this.index = this.index * (-1);
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
