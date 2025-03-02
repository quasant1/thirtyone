package edu.guilford;
import java.util.*;

public class Game {
    ArrayList<Player> players;
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
    }

    public void firstRound() {
        for (int i=0;i<players.size();i++) {
            players.get(i).decision(stockPile, discardPile,1);
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
