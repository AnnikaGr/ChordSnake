package com.game.chordsnake.model;


import com.game.chordsnake.Main;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Snake {
    //chords in Snake
    private ArrayList<String> collectedChords = new ArrayList<>();
    private int direction; // up=0, left=1, right=2, down=3


    //position of Snake
    private List<int[]> snakePosition = new ArrayList<>();

    public Snake(int head_x, int head_y) {
        int[] coords = {head_x, head_y};
        snakePosition.add(coords);
        collectedChords.add("SH");
    }

    public void removeElement(int index) {
        collectedChords.remove(index);
    }

    public void removeLastElement() {
        collectedChords.remove(collectedChords.size() - 1);
        snakePosition.remove(collectedChords.size() - 1);
    }

    public void appendElement(int oldTailX, int oldTailY, String chordName) {
        collectedChords.add(chordName);
        snakePosition.add(new int[]{oldTailX, oldTailY});
        //ref: https://stackoverflow.com/questions/39085830/how-to-play-a-wav-file-using-java
        try {
            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(Main.class.getResource("music/" + chordName + ".wav"))));
            sound.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

    }

    public void setSnake(int head_x, int head_y) {
        int[] coords = {head_x, head_y};
        snakePosition.set(0, coords);
    }

    //shifts snake forward one field
    public void shiftSnake() {
        List<int[]> tmp = snakePosition;
        int[] newHeadPosition = null;
        if (direction == 0) { //up
            newHeadPosition = new int[]{snakePosition.get(0)[0], snakePosition.get(0)[1] - 1};
        } else if (direction == 3) { //down
            newHeadPosition = new int[]{snakePosition.get(0)[0], snakePosition.get(0)[1] + 1};

        } else if (direction == 2) { //right
            newHeadPosition = new int[]{snakePosition.get(0)[0] + 1, snakePosition.get(0)[1]};
        } else if (direction == 1) { //left
            newHeadPosition = new int[]{snakePosition.get(0)[0] - 1, snakePosition.get(0)[1]};
        }

        snakePosition.remove(snakePosition.size() - 1);
        snakePosition.add(0, newHeadPosition);

    }

    //clears snake and leaves only the snake head
    public void clearSnake() {
        collectedChords = new ArrayList<String>();
        collectedChords.add("SH");

        List<int[]> oldPosition = snakePosition;
        snakePosition = new ArrayList<>();
        snakePosition.add(oldPosition.get(0));

    }

    public List<String> getCollectedChordsWithoutHead() {
        return collectedChords.subList(1, collectedChords.size());
    }

    public List<int[]> getCollectedChordsPositions() {
        return snakePosition.subList(1, snakePosition.size());
    }

    public List<String> getCollectedChords() {
        return collectedChords;
    }

    public List<int[]> getSnakePosition() {
        return snakePosition;
    }

    public void setSnakeDirectionRight() {
        direction = 2;
    }

    public void setSnakeDirectionUp() {
        direction = 0;
    }

    public void setSnakeDirectionDown() {
        direction = 3;
    }

    public void setSnakeDirectionLeft() {
        direction = 1;
    }

    public int getSnakeDirection() {
        return direction;
    }

    public int getSnakeHeadX() {
        return snakePosition.get(0)[0];
    }

    public int getSnakeHeadY() {
        return snakePosition.get(0)[1];
    }

}
