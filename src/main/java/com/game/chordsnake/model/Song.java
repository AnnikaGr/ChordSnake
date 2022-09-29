package com.game.chordsnake.model;

public class Song {
    private final String[] chordOrder = {"C", "E", "G"}; //TODO put proper songs

    public boolean checkCorrectOrder(Snake snake) { //TODO check if it works
        return chordOrder.equals(snake.getSnake());
    }

    public String[] getChordOrder() {
        return chordOrder;
    }

}
