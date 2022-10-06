package com.game.chordsnake.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Song {
    private final List<String> chordOrder = new ArrayList<>(); //TODO put proper songs

    public Song(int songId) {
        setChordOrder(songId);
    }

    public boolean checkCorrectOrder(Snake snake) { //TODO check if it works
        snake.getCollectedChords().subList(1, snake.getCollectedChords().size());
        return chordOrder.equals(snake.getCollectedChords());
    }

    public List<String> getChordOrder() {
        return chordOrder;
    }

    private void setChordOrder(int songId) {
        if (songId == 0) {
            Collections.addAll(chordOrder, "A", "B", "C");
        } else if (songId == 1) {
            Collections.addAll(chordOrder, "G", "D", "Am", "G", "D", "C", "G", "D", "Am", "G", "D", "C");
        } else if (songId == 2) {
            Collections.addAll(chordOrder, "a", "b", "c");
        }
    }

}
