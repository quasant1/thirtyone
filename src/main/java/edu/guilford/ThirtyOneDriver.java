package edu.guilford;

public class ThirtyOneDriver {
    public static void main(String[] args) {
        Game game = new Game(5);
        System.out.println("(Intial draw) " + game.toString());
        game.fullGame();
    }
}