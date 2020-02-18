package com.example.untitledendlessgame.Resources;

import android.graphics.Rect;

public class Character {
    private int X, Y, currentFrame, velocity;
    public static int maxFrame;
    private Rect collision;

    public Character(int maxFrame) {
        X = Tools.SCREEN_WIDTH / 4 - AppConstants.getBitmapBank().getCharacterWidth() / 4;
        Y = Tools.SCREEN_HEIGHT / 2 - AppConstants.getBitmapBank().getCharacterHeight() / 2;
        currentFrame = 0;
        velocity = 0;
        this.maxFrame = maxFrame;
        collision = new Rect();
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

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public Rect getCollision() {
        return collision;
    }

    public void setCollision(Rect collision) {
        this.collision = collision;
    }
}
