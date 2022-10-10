package com.game.chordsnake.model;

import java.util.Arrays;

public class Chord {

    private String[] notes;

    public Chord(String chordName) {
        switch (chordName) {
            case "C" -> this.notes = new String[]{"C", "E", "G"};
            case "G" -> this.notes = new String[]{"G", "B", "D"};
            case "Em" -> this.notes = new String[]{"E", "G", "B"};
            case "B" -> this.notes = new String[]{"B", "D#", "F#"};
            case "D" -> this.notes = new String[]{"D", "F#", "A"};
            case "Am" -> this.notes = new String[]{"A", "C", "E"};
            case "A" -> this.notes = new String[]{"A", "C#", "E"};
            default -> System.out.println("chord name not found in chord constructor ");
        }

    }

    public boolean isSameOrder(String[] input) {
        if (Arrays.equals(input, this.notes)) {
            return true;
        } else {
            System.out.println("Entered notes not the same as chord notes");
            return false;
        }
    }
}
