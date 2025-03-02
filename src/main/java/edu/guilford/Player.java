package edu.guilford;

import java.util.*;

public class Player {
    private int lives;
    private Hand hand;
    private boolean knocked;
    private boolean thirtyone;
    private int playerID;

    public Player(int playerID) {
        lives = 3;
        hand = new Hand();
        knocked = false;
        thirtyone = false;
        this.playerID = playerID;
    }

    public void loseLife() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    public Hand getHand() {
        return hand;
    }

    public void drawCard(Card card) {
        hand.addCard(card);
    }

    public void removeCard(Card card) {
        hand.removeCard(card);
    }

    // reset
    public void reset() {
        hand.reset();
    }

    // calculate score
    public int score() {
        return hand.getTotalValue();
    }
    /*
     * private Card card1() {
     * return hand.getCard(0);
     * }
     * private Card card2() {
     * return hand.getCard(1);
     * }
     * private Card card3() {
     * return hand.getCard(2);
     * }
     */

    // decision making -- we do greedy for taking cards
    public void decision(Queue<Card> stockPile, Stack<Card> discardPile, int r) {
        boolean turnIsDone = false;
        //if (score() == 31) thirtyone = true;
        if (r != 1 && score() > 20)
            knocked = true;
        else {
            // see if taking from discard pile increases the score more than 8 points
            if (discardPile.size() != 0) {
                Card potCard = discardPile.peek(); // potential card
                int maxScore = score();
                Card maxCard = hand.getCard(0); // index to remove to maxmimize score

                Hand temp = new Hand();
                temp.addCard(hand.getCard(0));
                temp.addCard(hand.getCard(1));
                temp.addCard(potCard);
                if (temp.getTotalValue() > maxScore) {
                    maxScore = temp.getTotalValue();
                    maxCard = hand.getCard(2);
                }

                temp.removeCard(hand.getCard(0));
                temp.addCard(hand.getCard(2));
                if (temp.getTotalValue() > maxScore) {
                    maxScore = temp.getTotalValue();
                    maxCard = hand.getCard(2);
                }

                temp.removeCard(hand.getCard(1));
                temp.addCard(hand.getCard(0));
                if (temp.getTotalValue() > maxScore) {
                    maxScore = temp.getTotalValue();
                    maxCard = hand.getCard(2);
                }
                if (maxScore > score() + 8 || stockPile.size() == 0) { // draw from discard pile
                    drawCard(potCard);
                    removeCard(maxCard);
                    discardPile.pop();
                    if (maxCard.getRank().ordinal() > 6) stockPile.add(maxCard); // if the card has a large rank, add to stock pile so other people can't use it (not immediately, at least)
                    else discardPile.add(maxCard);
                    turnIsDone = true;
                }
            }
            // if not, take from stock pile. we will just use a greedy algorithm, i.e. just
            // get rid of the card so the score is maximized
            if (!turnIsDone) {
                if (stockPile.size() != 0) {
                    for (int i = 0; i < discardPile.size(); i++) {
                        stockPile.add(discardPile.pop());
                    }
                    discardPile.push(stockPile.poll());
                }
                Card newCard = stockPile.poll();
                drawCard(newCard);
                Card toRemove = greedy(newCard);
                removeCard(toRemove);
                if (toRemove.getRank().ordinal() > 6) stockPile.add(toRemove); // if the card has a large rank, add to stock pile
                else discardPile.add(toRemove);

            }
        }
    }

    public boolean getKnocked() {
        return knocked;
    }
    public boolean getThirtyone() {
        return thirtyone;
    }
    public int getPlayerID() {
        return playerID;
    }

    public Card greedy(Card card) {
        int maxScore = score();
        Card maxCard = hand.getCard(0); // index to remove to maxmimize score

        Hand temp = new Hand();
        temp.addCard(hand.getCard(0));
        temp.addCard(hand.getCard(1));
        temp.addCard(card);

        if (temp.getTotalValue() > maxScore) {
            maxScore = temp.getTotalValue();
            maxCard = hand.getCard(2);
        }

        temp.removeCard(hand.getCard(0));
        temp.addCard(hand.getCard(2));
        if (temp.getTotalValue() > maxScore) {
            maxScore = temp.getTotalValue();
            maxCard = hand.getCard(0);
        }

        temp.removeCard(hand.getCard(1));
        temp.addCard(hand.getCard(0));
        if (temp.getTotalValue() > maxScore) {
            maxScore = temp.getTotalValue();
            maxCard = hand.getCard(1);
        }
        return maxCard;
    }
    

    // toString
    public String toString() {
        return "Player " + playerID + ": " + lives + " lives\n" + "Score: " + score() +
                "\n" + hand.toString();
    }

}
