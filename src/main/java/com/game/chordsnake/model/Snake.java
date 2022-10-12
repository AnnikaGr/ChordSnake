package com.game.chordsnake.model;

import java.util.ArrayList;
import java.util.List;

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
        collectedChords.remove(collectedChords.size()-1);
        snakePosition.remove(collectedChords.size()-1);
    }

    public void appendElement(int oldTailX, int oldTailY, String chordName) {
        collectedChords.add(chordName);
        System.out.println("collectedChords "+collectedChords);
        snakePosition.add(new int[]{oldTailX, oldTailY});
        System.out.println("snakePosition.size() "+snakePosition.size());
        for (int[]ele : snakePosition) {
            System.out.println(ele[0]+" " + ele[1]);
        }
        //TODO Snake touch wall after append
        //TODO play sound
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
        snakePosition.remove(snakePosition.size()-1);
        snakePosition.add(0, newHeadPosition);

    }

    //clears snake and leaves only the snake head
    public void clearSnake() {
        collectedChords = new ArrayList<String>();
        collectedChords.add("SH");

    }

    public List<String> getCollectedChordsWithoutHead() {
        return collectedChords.subList(1,collectedChords.size());
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
