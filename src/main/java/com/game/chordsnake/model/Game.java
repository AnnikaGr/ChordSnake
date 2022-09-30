package com.game.chordsnake.model;

public class Game {
    private Board board;
    private int songChosenID;
    private int instrumentChosenID;
    private boolean gameStart;

    public void setInstrumentChosenID(int instrumentChosenID) {
        this.instrumentChosenID = instrumentChosenID;
        System.out.println("instrument id: "+ this.instrumentChosenID);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }

    public void setSongChosenID(int songChosenID) {
        this.songChosenID = songChosenID;
        System.out.println("song id: "+ this.songChosenID);
    }
}
