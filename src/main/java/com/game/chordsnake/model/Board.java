package com.game.chordsnake.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private final int height = 15;
    private final int width = 15;
    private String[][] arrangement;
    private Song currentSong;
    private Snake currentSnake;
    private String[] savedChords;
    private boolean gameWon;
    private List<String> initialContent;

    public Board() {
        initialContent = new ArrayList<>(height * width);


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
        initialContent.addAll(song.getChordOrder());
        //add specified number of random chords
        initialContent.addAll(getRandomChords((10)));
        //add trash tile
        initialContent.add("T");
        //add instrument tile
        initialContent.add("I");
        //add snake head
        initialContent.add("SH");

        int remainFieldsNum = width * height - initialContent.size();
        //fill up with empty fields
        for (int i = 0; i < remainFieldsNum; i++) {
            initialContent.add("Z");
        }


        //randomize order
        Collections.shuffle(initialContent);

        arrangement= new String[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                arrangement[i][j] = initialContent.get(width * i + j);
            }
        }
/*
        int counter = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                arrangement[i][j] = initialContent.get((counter * (height - 1)) + j);
                if (content.get((counter * (height - 1)) + j).equals("SH")) {
                    currentSnake = new Snake(i, j);
                }
            }
            counter = counter + 1;
        }*/
    }

    public List<String> getChords() {
        return initialContent;
    }

    public String getOneChord(int chordIndex) {
        return initialContent.get(chordIndex);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void updateArrangement(){
        currentSnake.shiftSnake();
        List<String> collectedChords= currentSnake.getCollectedChords();
        List<int[]> snakePositions =currentSnake.getSnakePosition();

        int counter=0;
        for (int[] position: snakePositions
             ) {
            arrangement[position[0]][position[1]]=collectedChords.get(counter);
            counter++;
            //TODO check if snakeHead is on field with gameobject --> if yes calls ActOnEncouter)
        }
    }

    //TODO private actOnEncounter (x,y)
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
