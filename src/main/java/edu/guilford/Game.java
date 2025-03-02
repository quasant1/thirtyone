package edu.guilford;

import java.util.*;

public class Game {
    ArrayList<Player> players;
    int currentPlayer;
    int numPlayers; // og #
    Deck deck;
    Stack<Card> discardPile;
    Queue<Card> stockPile;

    Random rand = new Random();

    public Game(int numPlayers) {
        this.numPlayers = numPlayers;
        deck = new Deck();
        deck.shuffle();
        players = new ArrayList<Player>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player());
        }

        for (int i = 0; i < numPlayers; i++) {
            for (int j = 0; j < 3; j++) {
                (players.get(i)).drawCard(deck.deal());
            }
        }
        stockPile = new LinkedList<>();
        for (int i = 0; i < deck.size(); i++) {
            stockPile.add(deck.deal());
        }
        discardPile = new Stack<>();
        discardPile.push(stockPile.poll());
        // System.out.println(stockPile.size());
        // System.out.println(discardPile.size());
        currentPlayer = 0;
    }

    public void firstRound() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).decision(stockPile, discardPile, 1);
        }
        // System.out.println(discardPile.size());
    }

    public void restOfGame() {
        Player oneWhoKnocks;
        while (true) {
            players.get(currentPlayer).decision(stockPile, discardPile, 2);
            if (players.get(currentPlayer).score() == 31) {
                break;
            }
            if (players.get(currentPlayer).getKnocked()) {
                oneWhoKnocks = players.get(currentPlayer);
                for (int j = currentPlayer; j < players.size(); j++) {
                    players.get(j).decision(stockPile, discardPile, 1); // 1 because they can't knock
                }
                break;
            }
            currentPlayer = (currentPlayer + 1) % players.size();
        }
        // find the loser(s)
        // does someone have 31?
//System.out.println(this.toString() + "hi");
/* 
        Set<Player> thirtyones = new HashSet<Player>(); 
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).score() == 31)
                thirtyones.add(players.get(i));
        }
                */
        if (players.get(currentPlayer).score() == 31) {
            for (int i = 0; i < players.size(); i++) {
                if (i!= currentPlayer) {
                    players.get(i).loseLife();
                }
            }
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getLives() <= 0) {
                    players.remove(i);
                    i--;
                }
            }
        } else if (players.get(currentPlayer).getKnocked()) { // players with lowest score lose a life
            oneWhoKnocks = players.get(currentPlayer);
            int minScore = players.get(0).score(); // initialize to first player
            for (int i = 0; i < players.size(); i++) {
                minScore = Math.min(minScore, players.get(i).score());
            }
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).score() == minScore) {
                    System.out.println(players.get(i).score());
                    players.get(i).loseLife();
                    if (players.get(i) == oneWhoKnocks) { // if you knocked and you have the lowest score, you lose 2
                                                          // lives
                        players.get(i).loseLife();
                    }
                }
            }
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getLives() <= 0) {
                    players.remove(i);
                    i--;
                }
            }
        }
    }

    public void resetRound() { // start a new round
        deck = new Deck();
        deck.shuffle();
        for (int i = 0; i < players.size(); i++) {
            players.get(i).reset();
        }
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < 3; j++) {
                players.get(i).drawCard(deck.deal());
            }
        }
        stockPile = new LinkedList<>();
        for (int i = 0; i < deck.size(); i++) {
            stockPile.add(deck.deal());
        }
        discardPile = new Stack<>();
        discardPile.push(stockPile.poll());
        currentPlayer = 0;
    }

    public void fullGame() {
        while (players.size() > 1) {
            firstRound();
            restOfGame();
            System.out.println(this.toString());
            resetRound();
        }
        if (players.size() == 1) {
            System.out.println("The winner is " + players.get(0).toString());
        } else {
            System.out.println("Bad luck. No one wins.");
        }
    }

    // get current number of players
    public int getNumPlayers() {
        return players.size();
    }

    // toString
    public String toString() {
        String str;
        str = "Game Round: " + players.size() + " players" + "\n";
        for (int i = 0; i < players.size(); i++) {
            str += (i + 1) + " " + players.get(i).toString() + "\n";
        }
        // System.out.println("Stock Pile: " + stockPile.size());
        // str += "Stock Pile: " + stockPile.size() + "\n";
        // str += "Discard Pile: " + discardPile.size() + "\n";
        return str;
    }

}
