package com.carva.untitledendlessgame.Resources;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author carva
 * @version 1.0
 */
public class AppConstants {
    /**
     * Herramientas varias para el uso en SurfaceView.
     */
    static SurfaceViewTools SVTools;
    /**
     * Banco de imágenes del juego.
     */
    private static BitmapBank bitmapBank;
    /**
     * Motor de físicas del juego y dinujo en lienzo.
     */
    private static GameEngine gameEngine;
    /**
     * Banco de sonidos del juego
     */
    private static SoundsBank soundsBank;
    /**
     * Velocidad del personaje al saltar
     */
    public static int VELOCITY_WHEN_JUMPED;
    /**
     * Gravedad del entorno de juego
     */
    static int gravity;
    /**
     * Tamaño inicial del hueco que hay entre la cajas. También se puede considerar como la distancia
     * vertical que hay entre la caja superior y la caja inferior.
     */
    static int initialGapBetweenTopAndBottomBoxes;
    /**
     * Tamaño actual del hueco que hay entre la cajas. También se puede considerar como la distancia
     * vertical que hay entre la caja superior y la caja inferior.
     */
    static int actualGapBetweenTopAndBottomBoxes;
    /**
     * Distancia horizontal que hay entre un conjunto de cajas y otro.
     */
    static int distanceBetweenBoxes;
    /**
     * Número de conjunto de cajas que se dibujan.
     */
    static int numberOfBoxes;
    /**
     * Velocidad de "movimiento" de las cajas
     */
    static int boxVelocity;
    /**
     * Posición mínima en el eje Y de las cajas.
     */
    static int minBoxOffsetY;
    /**
     * Posición máxima en el eje Y de las cajas.
     */
    static int maxBoxOffsetY;


    /**
     * Inicialización de los diferentes valores del juego, recursos (banco de imágenes, herramientas
     * para SurfaceView, motor de físicas, banco de sonidos), y modificación de propiedades de
     * diferentes herramientas.
     *
     * @param context       Interface to global information about an application environment.
     * @param gravity       gravedad del entorno.
     * @param velWhenJumped velocidad del personaje al saltar.
     * @param boxVelocity   velocidad de "movimiento" de las cajas
     * @param screenWidth   ancho de la superficie de dibujo
     * @param screenHeight  alto de la superficie de dibujo
     */
    public static void initialize(Context context, int gravity, int velWhenJumped, int boxVelocity,
                                  int screenWidth, int screenHeight) {
        SVTools = new SurfaceViewTools(context);
        bitmapBank = new BitmapBank(context.getResources(), screenWidth, screenHeight);
        soundsBank = new SoundsBank(context);

        //Inicialización de constantes del juego
        AppConstants.gravity = gravity;
        AppConstants.VELOCITY_WHEN_JUMPED = velWhenJumped;
        initialGapBetweenTopAndBottomBoxes = AppConstants.getBitmapBank().getCharacterHeight() * 4;
        actualGapBetweenTopAndBottomBoxes = initialGapBetweenTopAndBottomBoxes;
        AppConstants.numberOfBoxes = 2;
        AppConstants.boxVelocity = boxVelocity;
        AppConstants.minBoxOffsetY = (int) (AppConstants.actualGapBetweenTopAndBottomBoxes / 2.0);
        AppConstants.maxBoxOffsetY = Tools.SCREEN_HEIGHT - AppConstants.minBoxOffsetY - AppConstants.actualGapBetweenTopAndBottomBoxes;
        AppConstants.distanceBetweenBoxes = (screenWidth * 3) / 4;

        //Propiedades Paint para puntuación
        SVTools.pBold[0].setColor(Color.BLACK);
        SVTools.pBold[0].setTextAlign(Paint.Align.CENTER);
        SVTools.pBold[0].setTextSize(screenWidth / 6);
        SVTools.pBold[1].setTextAlign(Paint.Align.CENTER);
        SVTools.pBold[1].setTextSize(SVTools.pBold[0].getTextSize());
        //Propiedades Paint para temporizador
        SVTools.pRegular[0].setColor(Color.BLACK);
        SVTools.pRegular[0].setTextAlign(Paint.Align.LEFT);
        SVTools.pRegular[0].setTextSize(screenWidth / 18);
        SVTools.pRegular[1].setTextAlign(Paint.Align.LEFT);
        SVTools.pRegular[1].setTextSize(SVTools.pRegular[0].getTextSize());

        gameEngine = new GameEngine(3);
    }

    /**
     * @return banco de imágenes del juego ({@link #bitmapBank}).
     */
    public static BitmapBank getBitmapBank() {
        return bitmapBank;
    }

    /**
     * @return motor de físicas y dinujo en lienzo ({@link #gameEngine}).
     */
    public static GameEngine getGameEngine() {
        return gameEngine;
    }

    /**
     * @return banco de sonidos del juego ({@link #soundsBank}).
     */
    public static SoundsBank getSoundsBank() {
        return soundsBank;
    }
}
