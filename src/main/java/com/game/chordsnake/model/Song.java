package com.game.chordsnake.model;

public class Song {
    private String [] chordOrder = {"C", "E", "G"}; //TODO put proper songs

    public boolean checkCorrectOrder(Snake snake){ //TODO check if it works
        if (chordOrder.equals(snake.getSnake())){
            return true;
        }
        else return false;
    }

    public String[] getChordOrder() {
        return chordOrder;
    }

}
