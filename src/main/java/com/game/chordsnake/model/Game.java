package com.game.chordsnake.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int songChosenID;
    private int instrumentChosenID;
    private boolean gameStart;

    public int getInstrumentChosenID() {
        return instrumentChosenID;
    }

    public void setInstrumentChosenID(int instrumentChosenID) { this.instrumentChosenID = instrumentChosenID; }

    public boolean getGameStarted() {
        return gameStart;
    }

    public void setGameStarted(boolean gameStart) {
        this.gameStart = gameStart;
    }

    public int getSongChosenID() {
        return songChosenID;
    }

    public void setSongChosenID(int songChosenID) { this.songChosenID = songChosenID; }

}
