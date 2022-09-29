package com.game.chordsnake.model;

public class GameObject {
    int location_x = 0;
    int location_y = 0;

    public boolean checkCollision(int x, int y) {
        return x == location_x && y == location_y;
    }
}
