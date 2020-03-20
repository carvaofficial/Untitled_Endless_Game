package com.carva.untitledendlessgame.Resources;

import android.graphics.Rect;

/**
 * Gestor del objeto/recurso 'personaje'.
 *
 * @author carva
 * @version 1.0
 */
public class Character {
    /**
     * Coordenada de eje X del personaje.
     */
    private int X;
    /**
     * Coordenada de eje Y del personaje.
     */
    private int Y;
    /**
     * Fotograma actual del personaje.
     */
    private int currentFrame;
    /**
     * Velocidad por defecto del personaje.
     */
    private int velocity;
    /**
     * Fotograma final del personaje.
     */
    public static int maxFrame;
    /**
     * Colisión del personaje.
     */
    private Rect collision;

    /**
     * Establece coordenadas de eje del personaje, el fotograma inicla/actual, su velocidad de movimiento,
     * el fotograma final y su colisión vacía.
     *
     * @param maxFrame fotograma final del conjunto de imágenes del personaje
     */
    public Character(int maxFrame) {
        X = Tools.SCREEN_WIDTH / 4 - AppConstants.getBitmapBank().getCharacterWidth() / 4;
        Y = Tools.SCREEN_HEIGHT / 2 - AppConstants.getBitmapBank().getCharacterHeight() / 2;
        currentFrame = 0;
        velocity = 0;
        Character.maxFrame = maxFrame;
        collision = new Rect();
    }

    /**
     * @return eje X del personaje ({@link #X}).
     */
    public int getX() {
        return X;
    }

    /**
     * @param x establece un nuevo eje X para el personaje.
     */
    public void setX(int x) {
        X = x;
    }

    /**
     * @return eje Y del personaje ({@link #Y}).
     */
    public int getY() {
        return Y;
    }

    /**
     * @param y establece un nuevo eje Y para el personaje.
     */
    public void setY(int y) {
        Y = y;
    }

    /**
     * @return fotograma actual del personaje ({@link #currentFrame}).
     */
    public int getCurrentFrame() {
        return currentFrame;
    }

    /**
     * @param currentFrame establece un nuevo fotograma actual para el personaje.
     */
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * @return velocidad del personaje ({@link #velocity}).
     */
    public int getVelocity() {
        return velocity;
    }

    /**
     * @param velocity establece la velocidad del personaje.
     */
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    /**
     * @return colisión del personaje ({@link #collision}).
     */
    public Rect getCollision() {
        return collision;
    }

    /**
     * @param collision establece una nueva colisión para el personaje.
     */
    public void setCollision(Rect collision) {
        this.collision = collision;
    }
}
