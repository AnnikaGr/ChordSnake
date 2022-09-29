package com.game.chordsnake.model;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    //chords in Snake
    private List<String> snake;
    private int direction; // up=0, left=1, right=2, down=3

    //position of Snake
    private final List<int[]> snakePosition = new ArrayList<>();

    public Snake(int head_y, int head_x) {
        int[] coords = {head_x, head_y};
        snakePosition.add(coords);
    }

    public void removeElement(int index) {
        snake.remove(index);
    }

    public void appendElement(String chordName) {
        snake.add(chordName);
        //TODO play sound
    }

    public void shiftSnake() {
        //TODO

        // according to direction calculate new snake head (snakePosition[0])

        // shift everything in snakePosition one index behind

        //set snakePosition[0] to calculated direction

    }

    public void clearSnake() {
        snake = new ArrayList<String>();
        snake.add("SH");

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

    public List<String> getSnake() {
        return snake;
    }
}
