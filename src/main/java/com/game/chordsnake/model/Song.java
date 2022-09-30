package com.game.chordsnake.model;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private final List<String> chordOrder = new ArrayList<>(); //TODO put proper songs

    public Song(){
        chordOrder.add("C");
        chordOrder.add("E");
        chordOrder.add("G");
    }

    public boolean checkCorrectOrder(Snake snake) { //TODO check if it works
        snake.getSnake().subList(1, snake.getSnake().size());
        return chordOrder.equals(snake.getSnake());
    }

    public List<String> getChordOrder() {
        return chordOrder;
    }

}
