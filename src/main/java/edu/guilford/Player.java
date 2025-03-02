package edu.guilford;

import java.util.*;

public class Player {
    private int lives;
    private Hand hand;

    public Player() {
        lives = 3;
        hand = new Hand();
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
    private Card card1() {
        return hand.getCard(0);
    }
    private Card card2() {
        return hand.getCard(1);
    }
    private Card card3() {
        return hand.getCard(2);
    }
        */

    // decision making -- we do greedy for taking cards
    public void decision(Queue<Card> stockPile, Stack<Card> discardPile, int round) {
        if (round == 1) {
            int maxScore = score();
            Card maxCard = hand.getCard(0); // index to remove to maxmimize score
            // take from discard pile if the increase is enough

            // if not, take from stock pile. we will just use a greedy algorithm
            drawCard(stockPile.poll());
            Hand temp = new Hand();
            temp.addCard(hand.getCard(0));
            temp.addCard(hand.getCard(1));
            temp.addCard(hand.getCard(3));

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
            
            removeCard(maxCard);
        }

    }

    // toString
    public String toString() {
        return "Player: " + lives + " lives\n" + "Score: " + score() +
        "\n" + hand.toString();
    }
    


    

    
}
