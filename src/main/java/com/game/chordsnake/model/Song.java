package com.game.chordsnake.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Song {
    private final List<String> chordOrder = new ArrayList<>();

    public Song(int songId) {
        setChordOrder(songId);
        // 1 = Seven Nation Army (easy)
        // 2 = Knocking on heaven´s door (easy)
        // 3= Nothing Else matters (advanced)
    }

    public boolean checkCorrectOrder(List<String> collectedChords) {
        return chordOrder.equals(collectedChords);
    }

    public List<String> getChordOrder() {
        return chordOrder;
    }

    private void setChordOrder(int songId) {
        if (songId == 0) {
            Collections.addAll(chordOrder, "Em", "G", "C", "B"); //Seven Nation Army
        } else if (songId == 1) {
            Collections.addAll(chordOrder, "G", "D", "Am", "G", "D", "C", "G", "D", "Am", "G", "D", "C"); // Knocking on heaven´s door
        } else if (songId == 2) {
            Collections.addAll(chordOrder, "Em", "D", "C", "Em", "D", "C", "Em", "D", "C", "G", "B", "Em", "D", "C", "A", "D", "C", "A", "D", "Em"); //Nothing else matters
        }
    }

}
