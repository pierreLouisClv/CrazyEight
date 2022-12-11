package game;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;

/* cette classe représente un joueur */
public class Player {
    private String name;
    private LinkedList<Card> handPlayer = new LinkedList<>();

    protected Player(String name) {
        this.name = name;
    }

    /**
     * Représente sous forme de liste les cardes du joueur qui ont la même couleur
     * la même valeur que la dernière carte jouée
     * les HUIT peuvent être joués n'importe quand donc ils sont ajoutés dans la
     * liste
     * 
     * @param lastCardPlayed la carte jouée par le joueur précedent
     * @return la liste des cartes jouables. Renvoie une liste vide si le joueur ne
     *         peut pas jouer
     */
    protected LinkedList<Card> getPlayableCards(Card lastCardPlayed) {
        LinkedList<Card> playableCards = new LinkedList<>();

        for (Card card : handPlayer) {
            if (card.haveSameColor(lastCardPlayed) ||
                    (card.haveSameValue(lastCardPlayed)) ||
                    (card.getValue().equals(Card.getMostPowerfullValue()))) {
                playableCards.add(card);
            }
        }
        return playableCards;
    }

    /**
     * Le joueur fais le meilleur choix parmi ses cartes jouables. Priorise les
     * cartes qui sont combinables.
     * le HUIT ne doit être joué qu'en dernier recours
     * Si aucune carte n'est combinable, le joueur choisit une carte aléatoire grâce
     * à l'appel de la
     * méthode chooseRandomCardFromHandPlayerWhichIsNotEight()
     * 
     * @param playableCards  liste des cartes jouables parcourue par le joueur
     * @param lastCardPlayed la carte jouée par le joueur précédent
     * @return la carte qui sera jouée par le joueur
     */
    protected Card makeTheBestChoice(LinkedList<Card> playableCards, Card lastCardPlayed) {
        int highestCombination = 1;
        int combination;
        /* carte par défaut */
        Card bestChoice = chooseRandomCardFromHandPlayerWhichIsNotEight(playableCards);

        /*
         * parcours des cartes pour déterminer la carte qui a le plus grand nombre de
         * combinaisons
         */
        for (Card card : playableCards) {
            if ((combination = nbOfCombinationOfTheCard(card)) > highestCombination
                    /* le parcours n'inclu par les HUIT */
                    && !card.getValue().equals(Card.getMostPowerfullValue())) {
                bestChoice = card;
                highestCombination = combination;
            }
        }

        String valueOfTheLastCardPlayed = lastCardPlayed.getValue();
        /*Cas où la carte qui sera jouée possède la même valeur que la dernière carte jouée */
        if (bestChoice.getValue().equals(valueOfTheLastCardPlayed) && highestCombination > 1) {
            bestChoice = chooseBestCardWhenPlayerPlaySeveralCardsWhichHaveSameValueOfLastCardPlayed(playableCards,
                        lastCardPlayed, bestChoice);
            }
        return bestChoice;
    }

    /**
     * Cette méthode est utilisée dans le cas où le joueur doit jouer plusieurs
     * cartes combinables (de la même valeur)
     * Elle donne l'information de la carte qu'il faut jouer en dernière position.
     * Ceci permet au joueur de choisir la couleur sur laquelle le prochain joueur devra jouer
     * 
     * @param bestChoice  la première carte de la combinaison
     * @param combination le nombre de carte qui sont jouées par le joueur au total
     * @return la dernière carte à jouer
     */
    protected Card cardPlayedAtTheEndOfTheCombination(Card bestChoice, int combination) {
        /* HashMap stockant pour chaque carte le nombre de carte qui possède la meme couleur que celle-ci*/
        HashMap<Card, Integer> nbOfEachColorsInHandPlayer = new HashMap<>(combination - 1);
        for (Card card : handPlayer) {
            /* parcours des cartes combinables avec bestChoice (la première carte jouée) */
            if ((card.haveSameValue(bestChoice)) && !(card.haveSameColor(bestChoice))) {
                nbOfEachColorsInHandPlayer.put(card, nbOfCardsWithCommonColorInPlayeHand(card.getColor()));
            }
        }

        Card finalCardPlayed = bestChoice;
        int maxColor = 0;
        /* détermine la carte qui a la plus grande de carte avec la même couleur en commun */
        for (Map.Entry<Card, Integer> entry : nbOfEachColorsInHandPlayer.entrySet()) {
            if (entry.getValue() > maxColor) {
                finalCardPlayed = entry.getKey();
                maxColor = entry.getValue();
            }
        }
        return finalCardPlayed;
    }

    /**
     * cette méthode permet au joueur qui a posé un HUIT de déterminer la couleur
     * qui l'arrange le plus pour la suite de la partie.
     * 
     * @return la couleur qui a le plus grand nombre de cartes dans la main du joueur
     */
    protected String determinColorAfterAnEight() {
        /* HashMap stockant pour chaque couleur son nombre de carte associé*/
        Map<String, Integer> nbOfEachColorsInHandPlayer = new HashMap<>();

        for (int i = 0; i < handPlayer.size(); i++) {
            /* parcours la main du joueur sans inclure les HUIT */
            if (!handPlayer.get(i).getValue().equals(Card.getMostPowerfullValue())) {
                String currentColor = handPlayer.get(i).getColor();
                /* Si on a déjà parcouru une carte de la couleur currentColor */
                if (nbOfEachColorsInHandPlayer.containsKey(currentColor)) {
                    /* la valeur associée à cette couleur est incrémentée de 1 */
                    nbOfEachColorsInHandPlayer.put(currentColor, nbOfEachColorsInHandPlayer.get(currentColor) + 1);
                } else {
                    /* Sinon on crée une entrée dans le HashMap avec la valeur initiale de 1 */
                    nbOfEachColorsInHandPlayer.put(currentColor, 1);
                }
            }
        }

        String colorMostRepresentedInPlayerHand = "";
        int maxColors = 0;
        int countColor = 0;
        /* détermine la couleur la plus présente dans la main du joueur en comparant les valeurs pour chaque couleur */
        for (Map.Entry<String, Integer> color : nbOfEachColorsInHandPlayer.entrySet()) {
            if ((countColor = color.getValue()) > maxColors) {
                maxColors = countColor;
                colorMostRepresentedInPlayerHand = color.getKey();
            }
        }

        return colorMostRepresentedInPlayerHand;
    }

    /**
     * cette méthode détermine, pour une couleur donnée, le nombre de carte qui
     * possèdent cette même couleur
     * dans la main du joueur
     * 
     * @param color la couleur concernée
     * @return un nombre entier, 0 par défaut
     */
    private int nbOfCardsWithCommonColorInPlayeHand(String color) {
        int number = 0;
        for (Card card : this.handPlayer) {
            if (card.getColor().equals(color)) {
                number++;
            }
        }
        return number;
    }

    /**
     * cette méthode est utilisée pour connaître le nombre de carte qui ont la même
     * valeur qu'une carte donnée
     * dans la main du joueur. Cette carte est inclue dans la valeur de retour.
     * 
     * @param handCare la carte qui sera comparée avec les autres cartes de la main
     *                 du joueur
     * @return le nombre de carte combinable, 1 par défaut.
     */
    protected int nbOfCombinationOfTheCard(Card handCard) {
        int combination = 1;
        for (Card card : handPlayer) {
            if (card.haveSameValue(handCard) && !(card.haveSameColor(handCard))) {
                combination++;
            }
        }
        return combination;
    }

    /**
     * cette méthode permet de choisir une carte par défaut dans la main du joueur
     * au début de la méthode
     * makeTheBestChoice. Elle choisit la carte de manière aléatoire en parcourant
     * simplement les cartes
     * jouables
     * 
     * @param playableCards l'ensemble des cartes jouées. Ne peut pas être vide.
     * @return une carte
     */
    private Card chooseRandomCardFromHandPlayerWhichIsNotEight(LinkedList<Card> playableCards) {
        int i = 0;
        int size = playableCards.size();
        while (playableCards.get(i).getValue().equals(Card.getMostPowerfullValue()) && i < size) {
            if (i == size - 1) {
                break;
            }
            i++;
        }
        return playableCards.get(i);
    }

    /**
     * cette méthode correspond au cas où le joueur joue plusieurs cartes
     * combinables ET qui ont la même valeur
     * que la dernière carte jouée. Cette méthode évite de jouer en premier la carte
     * qui devrait être jouée
     * à la fin de la combinaison.
     * 
     * @param playableCards  la liste des cartes jouables qui sera parcourue par le
     *                       joueur
     * @param lastCardPlayed la dernière carte jouée, appelée par la méthode
     *                       cardPlayedAtTheEndOfTheCombination()
     * @param bestChoice     la carte qui a été définie en tant que première carte à
     *                       jouer avant l'appel de cette méthode
     * @return la première carte qui doit être jouée, par défaut bestChoice
     */
    private Card chooseBestCardWhenPlayerPlaySeveralCardsWhichHaveSameValueOfLastCardPlayed(
            LinkedList<Card> playableCards, Card lastCardPlayed, Card bestChoice) {
        Card firstCardToPlay = bestChoice;
        /*définit dans un premier temps la carte joué à la fin */
        Card finalCard = cardPlayedAtTheEndOfTheCombination(lastCardPlayed, nbOfCombinationOfTheCard(bestChoice));
        /* si la carte définie est égal à celle qui devait être jouée en premier */
        if (bestChoice.equals(finalCard)) {
            for (Card card : playableCards) {
                if (card.haveSameValue(finalCard) && !(card.haveSameColor(finalCard))) {
                    /* on définit une autre carte à jouer en premier qui n'est pas égal à finalCard */
                    firstCardToPlay = card;
                }
            }
        }
        return firstCardToPlay;
    }

    /**
     * cette méthode détermine si le joueur n'a plus de carte dans sa main et s'il a
     * remporté la partie
     * 
     * @return true si le joueur n'a plus de carte, sinon false
     */
    protected boolean hasWon() {
        return this.handPlayer.isEmpty();
    }

    protected LinkedList<Card> getHandPlayer() {
        return this.handPlayer;
    }

    protected String getName() {
        return this.name;
    }

}