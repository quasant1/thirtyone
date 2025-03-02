package edu.guilford;

import java.util.*;

public class Player {
    private int lives;
    private Hand hand;
    private boolean knocked;

    public Player() {
        lives = 3;
        hand = new Hand();
        knocked = false;
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
        if (r != 1 && score() > 25)
            knocked = true;
        else {
            // see if taking from discard pile increases the score more than 8 points
            if (discardPile != null) {
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
                if (maxScore > score() + 8) { // draw from discard pile
                    drawCard(potCard);
                    removeCard(maxCard);
                    discardPile.pop();
                    discardPile.add(maxCard);
                    turnIsDone = true;
                }
            }
            // if not, take from stock pile. we will just use a greedy algorithm, i.e. just
            // get rid of the card so the score is maximized
            if (!turnIsDone) {
                if (stockPile == null) {
                    for (int i = 0; i < discardPile.size(); i++) {
                        stockPile.add(discardPile.pop());
                    }
                    discardPile.push(stockPile.poll());
                }
                Card newCard = stockPile.poll();
                drawCard(newCard);
                Card toRemove = greedy(newCard);
                removeCard(toRemove);
                discardPile.add(toRemove);

            }
        }
    }

    public boolean getKnocked() {
        return knocked;
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
        return "Player: " + lives + " lives\n" + "Score: " + score() +
                "\n" + hand.toString();
    }

}
