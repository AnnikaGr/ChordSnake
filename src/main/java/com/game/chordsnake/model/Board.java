package com.game.chordsnake.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private final int height = 20;
    private final int width = 20;
    private String[][] arrangement;
    private Song currentSong;
    private Snake currentSnake;
    private String[] savedChords;
    private boolean gameWon;
    private List<String> content;

    public Board() {
        content = new ArrayList<>(height * width);


        // set border tiles
        //arrangement= new String[height+2][width+2];

        /*for (int i= height +2; i < height+2; i++){
            for (int j = width +2; j<width+2; j++){
                //set borders if in border area
                if( i==0){
                    arrangement[i][j] = "X";
                }
                else if (i==height-1){
                    arrangement[i][j] = "X";
                }
                else if (j==0){
                    arrangement[i][j] = "X";
                }
                else if (j==width-1){
                    arrangement[i][j] = "X";
                }
                else {
                    content.get(i-1+j-1);
                }
            }
        }*/

    }

    public void setBoard(Song song) {

        //add chords to collect
        content.addAll(song.getChordOrder());
        //add specified number of random chords
        content.addAll(getRandomChords((10)));
        //add trash tile
        content.add("T");
        //add instrument tile
        content.add("I");
        //add snake head
        content.add("SH");

        int remainFieldsNum = width * height - content.size();
        //fill up with empty fields
        for (int i = 0; i < remainFieldsNum; i++) {
            content.add("Z");
        }


        //randomize order
        Collections.shuffle(content);
/*
        int counter = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                arrangement[i][j] = content.get((counter * (height - 1)) + j);
                if (content.get((counter * (height - 1)) + j).equals("SH")) {
                    currentSnake = new Snake(i, j);
                }
            }
            counter = counter + 1;
        }*/
    }

    public List<String> getChords() {
        return content;
    }

    public String getOneChord(int chordIndex) {
        return content.get(chordIndex);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    //TODO updateArrangement (call ShiftSnake, check if snakeHead is on field with gameobject --> if yes calls ActOnEncouter)
    //TODO actOnEncounter (x,y)

    //TODO EventListening


    private List<String> getRandomChords(int number) {
        String[] possibleChords = {"C", "G", "Am", "F", "Bm", "B", "Cm", "Gm", "Fm"};
        List<String> selectedChords = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            selectedChords.add(possibleChords[(int) Math.floor(Math.random() * possibleChords.length)]);
        }
        return selectedChords;
    }

}
