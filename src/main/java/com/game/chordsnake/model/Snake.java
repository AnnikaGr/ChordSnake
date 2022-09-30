package com.game.chordsnake.model;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    //chords in Snake
    private List<String> snake;
    private int direction; // up=0, left=1, right=2, down=3

    //position of Snake
    private List<int[]> snakePosition = new ArrayList<>();

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

    //shifts snake forward one field
    public void shiftSnake() {
        List<int[]> tmp = snakePosition;
        int [] newHeadPosition = null;
        if (direction == 0){ //up
            newHeadPosition = new int[]{snakePosition.get(0)[0], snakePosition.get(0)[1] + 1};
        }
        else if (direction == 3){ //down
            newHeadPosition = new int[]{snakePosition.get(0)[0], snakePosition.get(0)[1] - 1};
        }
        else if (direction == 2){ //right
            newHeadPosition = new int[]{snakePosition.get(0)[0] + 1, snakePosition.get(0)[1]};
        }
        else if (direction == 1){ //left
            newHeadPosition = new int[]{snakePosition.get(0)[0] - 1, snakePosition.get(0)[1]};
        }
        snakePosition.set(0, newHeadPosition);
        for (int i = 0; i<snakePosition.size(); i++){
            snakePosition.set(i+1, tmp.get(i));
        }
    }

    //clears snake and leaves only the snake head
    public void clearSnake() {
        snake = new ArrayList<String>();
        snake.add("SH");

    }

    public List<String> getSnake() {
        return snake;
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

}
