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
            case "Cm" -> this.notes= new String[]{"C", "D#", "G"};
            case "E"-> this.notes= new String[]{"E", "G#", "B" };
            case "F" -> this.notes= new String[]{"F", "A", "C"};
            case "Fm" -> this.notes = new String[]{"F#", "G#", "C"};
            case "Gm" -> this.notes= new String[]{"G", "A#", "D"};
            case "Bm" -> this.notes = new String[]{"B", "D", "F#"};
            default -> System.out.println("chord name not found in chord constructor ");
        }

    }

    public boolean isSameOrder(String[] input) {
        if (input[0].equalsIgnoreCase(this.notes[0])
        && input[1].equalsIgnoreCase(this.notes[1]) &&
        input[2].equalsIgnoreCase(this.notes[2])) {
            return true;
        } else {
            System.out.println("Entered notes not the same as chord notes");
            return false;
        }
    }

    public String[] getNotes (){
        return notes;
    }
}
