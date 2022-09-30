package com.game.chordsnake.model;

import java.util.Arrays;

public class Chord {

    private String [] notes;

    public Chord (String chordName){
    if (chordName.equals("C")){
        this.notes= new String[]{"C", "E", "G"};
    }
    else if(chordName.equals("G")){
        this.notes= new String[]{"G", "B", "D"};
        }
    else if(chordName.equals("F")){
        this.notes= new String[]{"F", "A", "C"};
    }
    else{
        System.out.println("chord name not found in chord constructor ");
    }

    }

    public boolean isSameOrder (String[] input){
        if (Arrays.equals(input, this.notes)){
            return true;
        }
        else{
            System.out.println("Entered notes not the same as chord notes");
            return false;
        }
    }
}
