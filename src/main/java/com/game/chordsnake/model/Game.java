package com.game.chordsnake.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private int songChosenID;
    private int instrumentChosenID;
    private boolean gameStart;
    //private ArrayList<String>[] songChords = (ArrayList<String>[]) new ArrayList[3];
    private List<String> songChords = new ArrayList<>();

    public void setInstrumentChosenID(int instrumentChosenID) {
        this.instrumentChosenID = instrumentChosenID;
        System.out.println("instrument id: " + this.instrumentChosenID);
    }

    public boolean getGameStarted() {
        return gameStart;
    }

    public void setGameStarted(boolean gameStart) {
        this.gameStart = gameStart;
    }


    public void setBoard(Board board) {
        this.board = board;
    }

    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }

    public int getSongChosenID() {
        return songChosenID;
    }

    public void setSongChosenID(int songChosenID) {
        this.songChosenID = songChosenID;
        System.out.println("song id: " + this.songChosenID);
    }

    public List<String> getSongChords() {
        return songChords;
    }
}
