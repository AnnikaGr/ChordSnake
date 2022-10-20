package com.game.chordsnake.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {
    private final int height = 15;
    private final int width = 15;
    private final String[] possibleChords = {"E", "Em", "D", "C", "G", "Am", "F", "Bm", "B", "Cm", "Gm", "Fm"}; //TODO update possible Chords
    public String[][] arrangement;
    private Song currentSong;
    private Snake currentSnake;
    private int[] trashPosition;
    private int[] instrumentPosition;
    private String[] savedChords;
    private boolean gameWon;
    private List<String> initialContent;
    private String encounteredElement;
    private String chordToCheck;
    private String[] noteText = {"", "", ""};
    //private List<chordTriad> chordTriadRef = new ArrayList<>(13);

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

        arrangement = new String[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                String current = initialContent.get(width * i + j);
                arrangement[i][j] = current;
                if (current.equals("SH")) {
                    currentSnake = new Snake(i, j);
                }
                if (current.equals("T")) {
                    trashPosition = new int[]{i, j};
                }
                if (current.equals("I")) {
                    instrumentPosition = new int[]{i, j};
                }
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

    public String getOneChord(int x, int y) {
        return arrangement[x][y];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int updateArrangement() {
        boolean appended = false;
        int[] tailPositions = currentSnake.getSnakePosition().get((currentSnake.getSnakePosition().size() - 1));
        List<int[]> bodyPositions = List.copyOf(currentSnake.getSnakePosition());
        currentSnake.shiftSnake();
        int[] newHeadPosition = currentSnake.getSnakePosition().get(0);
        for (int[] bodyPosition : bodyPositions) {
            if (bodyPosition[0] == newHeadPosition[0] && bodyPosition[1] == newHeadPosition[1]) {
                return 0;
            }
        }
        //if snakehead out of grid --> game failed
        if (newHeadPosition[0] <= -1 || newHeadPosition[1] <= -1 || newHeadPosition[0] >= getWidth() || newHeadPosition[1] >= getHeight()) {
            return 0;
        }

        setEncounter(newHeadPosition[0], newHeadPosition[1]);

        if (encounteredElement.equals("Z")) {
            arrangement[tailPositions[0]][tailPositions[1]] = "Z";
        } else if (encounteredElement.equals("T")) {
            arrangement[tailPositions[0]][tailPositions[1]] = "Z";
        } else if (encounteredElement.equals("I")) {
            arrangement[tailPositions[0]][tailPositions[1]] = "Z";
        }
        for (String possibleChord : possibleChords) {
            if (encounteredElement.equals(possibleChord)) {
                chordToCheck = encounteredElement;
                currentSnake.appendElement(tailPositions[0], tailPositions[1], encounteredElement);
                appended = true;
            }
        }
        List<String> collectedChords = currentSnake.getCollectedChords();
        List<int[]> snakePositions = currentSnake.getSnakePosition();

        int counter = 0;

        for (int[] position : snakePositions) {
            arrangement[position[0]][position[1]] = collectedChords.get(counter);
            counter++;
        }

        if (appended) {
            return 2;
        } else {
            return 1;
        }
    }

    private List<String> getRandomChords(int number) {
        List<String> selectedChords = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            selectedChords.add(possibleChords[(int) Math.floor(Math.random() * possibleChords.length)]);
        }
        return selectedChords;
    }

    public Snake getCurrentSnake() {
        return currentSnake;
    }

    public void setEncounter(int x, int y) {
        encounteredElement = arrangement[x][y];
    }

    public String getEncounter() {
        return encounteredElement;
    }

    public int[] getTrashPosition() {
        return trashPosition;
    }

    public int[] getInstrumentPosition() {
        return instrumentPosition;
    }

    public void shuffleBoard() {
        Collections.shuffle(Arrays.asList(arrangement));
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                if (arrangement[i][j].equals("SH")) getCurrentSnake().setSnake(i, j);
            }
    }

    public void setNoteText(int counter, String note) {
        noteText[counter] += note;
    }

    public void resetNoteText() {
        noteText[0] = "";
        noteText[1] = "";
        noteText[2] = "";
    }

    public String getChordToCheck() {
        return chordToCheck;
    }

    public boolean checkNotes() {
        System.out.println("user put " + noteText[0] + noteText[1] + noteText[2]);
        System.out.println("encounteredElement " + chordToCheck);
        Chord currentChord= new Chord(chordToCheck);
       return currentChord.isSameOrder(new String[]{noteText[0], noteText[1] , noteText[2]});
    }

    class chordTriad {
        String name = "";
        List<String> notes = new ArrayList<>(3);

        public chordTriad(String name, List<String> notes) {
            this.name = name;
            this.notes.addAll(notes);
        }
    }

}
