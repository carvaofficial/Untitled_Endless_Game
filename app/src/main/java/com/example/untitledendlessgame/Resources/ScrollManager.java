package com.example.untitledendlessgame.Resources;

public class ScrollManager {
    private int X, Y, velocity;

    public ScrollManager(int velocity) {
        X = 0;
        Y = 0;
        this.velocity = velocity;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}
