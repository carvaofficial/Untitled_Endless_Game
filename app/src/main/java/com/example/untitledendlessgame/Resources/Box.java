package com.example.untitledendlessgame.Resources;

import android.graphics.Rect;

import java.util.Random;

public class Box {
    private int X, offsetY, boxColor;
    private Random random;
    private Rect[] collisions;

    public Box(int x, int offsetY) {
        X = x;
        this.offsetY = offsetY;
        random = new Random();
        collisions = new Rect[2];
    }

    public int getX() {
        return X;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getTopBoxY() {
        return offsetY - AppConstants.getBitmapBank().getBoxHeight();
    }

    public int getBottomBoxY() {
        return offsetY + AppConstants.gapBetweenTopAndBottomBoxes;
    }

    public void setX(int x) {
        X = x;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public void setBoxColor() {
        boxColor = random.nextInt(2);
    }

    public int getBoxColor() {
        return boxColor;
    }

    public Rect[] getCollisions() {
        return collisions;
    }

    public void setCollisions(Rect[] collisions) {
        this.collisions = collisions;
    }

    public Rect getCollision(int pos) {
        return collisions[pos];
    }

    public void setCollision(Rect collision, int pos) {
        this.collisions[pos] = collision;
    }

    //TODO si lo de GameEngine no funciona, tratar de traer el Bitmap aqui y utilizar estas funcoones
//    public void actualizaRec() {
//        collisions = new Rect(getX(), offsetY, getX() + imagen.getWidth(), offsetY + imagen.getHeight());
//    }
//
//    public void mover(int x) {
//        setX(getX() + x);
//        if (getX() < 0) setX(Tools.SCREEN_WIDTH);
//        actualizaRec();
//    }
//
//    public void dibujar(Canvas c) {
//
//        c.drawBitmap(imagen, getX(), getOffsetY(), null);
//        c.drawRect(collisions, AppConstants.SVTools.pRects);
//    }
}
