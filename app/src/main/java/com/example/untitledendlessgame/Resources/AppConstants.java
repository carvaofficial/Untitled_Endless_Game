package com.example.untitledendlessgame.Resources;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

public class AppConstants {
    static SurfaceViewTools SVTools;
    private static BitmapBank bitmapBank;
    private static GameEngine gameEngine;
    private static SoundsBank soundsBank;
    public static int VELOCITY_WHEN_JUMPED;
    static int gravity;
    static int initialGapBetweenTopAndBottomBoxes, gapBetweenTopAndBottomBoxes, distanceBetweenBoxes;
    static int numberOfBoxes, boxVelocity, minBoxOffsetY, maxBoxOffsetY;


    public static void initialize(Context context, int gravity, int velWhenJumped, int boxVelocity,
                                  int screenWidth, int screenHeight) {
        SVTools = new SurfaceViewTools(context);
        bitmapBank = new BitmapBank(context.getResources(), screenWidth, screenHeight);

        //Inicialización de constantes del juego
        AppConstants.gravity = gravity;
        AppConstants.VELOCITY_WHEN_JUMPED = velWhenJumped;
        initialGapBetweenTopAndBottomBoxes = AppConstants.getBitmapBank().getCharacterHeight() * 4;
        gapBetweenTopAndBottomBoxes = initialGapBetweenTopAndBottomBoxes;
        AppConstants.numberOfBoxes = 2;
        AppConstants.boxVelocity = boxVelocity;
        AppConstants.minBoxOffsetY = (int) (AppConstants.gapBetweenTopAndBottomBoxes / 2.0);
        AppConstants.maxBoxOffsetY = Tools.SCREEN_HEIGHT - AppConstants.minBoxOffsetY - AppConstants.gapBetweenTopAndBottomBoxes;
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
        soundsBank = new SoundsBank(context);
    }

    public static BitmapBank getBitmapBank() {
        return bitmapBank;
    }

    public static GameEngine getGameEngine() {
        return gameEngine;
    }

    public static SoundsBank getSoundsBank() {
        return soundsBank;
    }
}
