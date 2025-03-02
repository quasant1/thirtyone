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
        Queue<Card> stockPile = new LinkedList<>();
        for (int i = 0; i < deck.size(); i++) {
            stockPile.add(deck.deal());
        }
        Stack<Card> discardPile = new Stack<>();
        discardPile.push(stockPile.poll());
        currentPlayer = 0;
    }

    public void firstRound() {
        for (int i=0;i<players.size();i++) {
            players.get(i).decision(stockPile, discardPile,1);
        }
    }

    public void restOfGame() {
        Player oneWhoKnocks;
        while (true) {
            players.get(currentPlayer).decision(stockPile, discardPile,2);
            if (players.get(currentPlayer).getKnocked()) {
                oneWhoKnocks = players.get(currentPlayer);
                for (int j=currentPlayer;j<players.size();j++) {
                    players.get(j).decision(stockPile, discardPile, 1); // 1 because they can't knock
                }
                break;
            }
        }
        // find the loser(s)
        // does someone have 31? 

        Set<Player> thirtyones = new HashSet<Player>(); // up to 4 people can have 31
        for (int i=0;i<players.size();i++) {
            if (players.get(i).score() == 31) thirtyones.add(players.get(i));
        }
        if (thirtyones.size() > 0) {
            for (int i=0;i<players.size();i++) {
                if (!thirtyones.contains(players.get(i))) {
                    players.get(i).loseLife();
                    if (players.get(i).getLives() == 0) {
                        players.remove(i);
                        i--;
                    }
                }
            }
        }
        else { // players with lowest score lose a life
            int minScore = players.get(0).score(); // initialize to first player
            for (int i=0;i<players.size();i++) {
                minScore = Math.min(minScore,players.get(i).score());
            }
            for (int i=0;i<players.size();i++) {
                if (players.get(i).score() == minScore) {
                    players.get(i).loseLife();
                    if (players.get(i) == oneWhoKnocks) { // if you knocked and you have the lowest score, you lose 2 lives
                        players.get(i).loseLife();
                    }

                    if (players.get(i).getLives() == 0) {
                        players.remove(i);
                        i--;
                    }
                }
            }
        }
    }

    public void resetRound() { // start a new round
        deck = new Deck();
        deck.shuffle();
        for (int i=0;i<players.size();i++) {
            players.get(i).reset();
        }
        for (int i=0;i<players.size();i++) {
            for (int j=0;j<3;j++) {
                players.get(i).drawCard(deck.deal());
            }
        }
        Queue<Card> stockPile = new LinkedList<>();
        for (int i=0;i<deck.size();i++) {
            stockPile.add(deck.deal());
        }
        Stack<Card> discardPile = new Stack<>();
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
        }
        else {
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
        str = "Game: " + players.size() + " players" + "\n";
        for (int i=0; i<players.size(); i++) {
            str += players.get(i).toString() + "\n";
        }
        return str;
    }

}
