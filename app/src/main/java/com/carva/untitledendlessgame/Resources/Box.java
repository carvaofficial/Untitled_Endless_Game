package com.carva.untitledendlessgame.Resources;

import android.graphics.Rect;

import java.util.Random;

/**
 * Gestor del objeto/recurso 'caja'.
 *
 * @author carva
 * @version 1.0
 */
public class Box {
    /**
     * Coordenada de eje X de las cajas.
     */
    private int X;
    /**
     * Coordenada de eje Y intermedio entre ambas cajas.
     */
    private int offsetY;
    /**
     * Color de las cajas.
     */
    private int boxColor;
    /**
     * Generador de números aleatorios.
     */
    private Random random;
    /**
     * Colisiones de las cajas.
     */
    private Rect[] collisions;


    /**
     * Establece coordenadas de eje de las cajas, sus colisiones e inicializa el generador de números aleatorios.
     *
     * @param x       eje X de las cajas.
     * @param offsetY eje Y intermedio entre ambas cajas.
     */
    public Box(int x, int offsetY) {
        X = x;
        this.offsetY = offsetY;
        random = new Random();
        collisions = new Rect[2];
    }

    /**
     * @return eje X de las cajas ({@link #X}).
     */
    public int getX() {
        return X;
    }

    /**
     * @return eje Y intermedio ({@link #offsetY}).
     */
    public int getOffsetY() {
        return offsetY;
    }

    /**
     * @return eje Y de la caja superior.
     */
    public int getTopBoxY() {
        return offsetY - AppConstants.getBitmapBank().getBoxHeight();
    }

    /**
     * @return eje Y de la caja inferior.
     */
    public int getBottomBoxY() {
        return offsetY + AppConstants.actualGapBetweenTopAndBottomBoxes;
    }

    /**
     * @param x establece un nuevo eje X de las cajas.
     */
    public void setX(int x) {
        X = x;
    }

    /**
     * @param offsetY establece un nuevo eje Y intermedio entre ambas cajas
     */
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    /**
     * Establece de manera aleatoria un color para las cajas.
     */
    public void setBoxColor() {
        boxColor = random.nextInt(2);
    }

    /**
     * @return color de las cajas ({@link #boxColor}).
     */
    public int getBoxColor() {
        return boxColor;
    }

    /**
     * @return colisiones de las cajas ({@link #collisions}).
     */
    public Rect[] getCollisions() {
        return collisions;
    }

    /**
     * @param collisions establece nuevas colisiones para las cajas.
     */
    public void setCollisions(Rect[] collisions) {
        this.collisions = collisions;
    }

    /**
     * @param pos 0 = caja superior, 1 = caja inferior.
     * @return colisión de la caja seleccionada en el parámetro.
     */
    public Rect getCollision(int pos) {
        return collisions[pos];
    }

    /**
     * @param collision establece una nueva colisión para la caja seleccionada en el parámetro <code>pos</code>.
     * @param pos       0 = caja superior, 1 = caja inferior.
     */
    public void setCollision(Rect collision, int pos) {
        this.collisions[pos] = collision;
    }
}
