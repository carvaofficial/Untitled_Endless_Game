package com.carva.untitledendlessgame.Resources;

import android.graphics.Rect;

/**
 * Gestor de movimiento de imágenes del juego.
 *
 * @author carva
 * @version 1.0
 */
public class ScrollManager {
    /**
     * Coordenada de eje X.
     */
    private int X;
    /**
     * Coordenada de eje Y.
     */
    private int Y;
    /**
     * Velocidad de movimiento de la imagen.
     */
    private int velocity;
    /**
     * Colisión de la imagen.
     */
    private Rect collision;

    /**
     * Se inicializan las coordenadas de ejes y se establece la velocidad de movimiento.
     *
     * @param velocity velocidad de movimiento.
     */
    public ScrollManager(int velocity) {
        X = 0;
        Y = 0;
        this.velocity = velocity;
    }

    /**
     * @return eje X ({@link #X}).
     */
    public int getX() {
        return X;
    }

    /**
     * @param x establece un nuevo eje X.
     */
    public void setX(int x) {
        X = x;
    }

    /**
     * @return eje Y ({@link #Y}).
     */
    public int getY() {
        return Y;
    }

    /**
     * @param y establece un nuevo eje Y.
     */
    public void setY(int y) {
        Y = y;
    }

    /**
     * @return velocidad de movimiento de la imagen ({@link #velocity}).
     */
    public int getVelocity() {
        return velocity;
    }

    /**
     * @param velocity establece la velocidad de movimiento de la imagen.
     */
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    /**
     * @return colisión de la imagen ({@link #collision}).
     */
    public Rect getCollision() {
        return collision;
    }

    /**
     * @param collision establece una nueva colisión para la imagen.
     */
    public void setCollision(Rect collision) {
        this.collision = collision;
    }
}
