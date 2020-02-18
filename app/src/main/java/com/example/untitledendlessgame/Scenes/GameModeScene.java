package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.example.untitledendlessgame.Games.Game1Activity;
import com.example.untitledendlessgame.R;
import com.example.untitledendlessgame.Resources.Tools;
import com.example.untitledendlessgame.Scenes.Tutorials.TutorialGameMode1;
import com.example.untitledendlessgame.Scenes.Tutorials.TutorialGameMode2;

import static com.example.untitledendlessgame.Resources.SurfaceViewTools.*;
import static com.example.untitledendlessgame.Resources.Tools.*;

public class GameModeScene extends Scene {
    boolean tutorial, gameSelected;
    int img1, img2;
    String sContinue, sGameMode1, sGameMode2;
    Paint pGMStroke, pGameMode;
    Rect rGameMode1, rGameMode2, rGMStroke1, rGMStroke2, rContinue;
    Bitmap bGameMode1, bGameMode2;

    public GameModeScene(int sceneNumber, Context context, int screenWidth, int screenHeight, boolean orientation) {
        super(sceneNumber, context, screenWidth, screenHeight, orientation);
        Log.i("Orientation", "public GameModeScene: " + screenWidth + ":" + screenHeight);
        this.gameSelected = true;
        this.tutorial = sceneNumber == Scene.TUTORIAL;

        //Inicializacion pinceles:
        pGMStroke = new Paint();
        pGMStroke.setColor(Color.WHITE);
        pGMStroke.setAntiAlias(true);
        pGMStroke.setStyle(Paint.Style.STROKE);
        pGMStroke.setStrokeWidth(5);
        pGameMode = new Paint();
        pGameMode.setColor(Color.argb(0, 0, 0, 0));

        //Propiedades Paints:
        SVTools.pBold[0].setTextAlign(Paint.Align.CENTER);
        SVTools.pRegular[0].setTextAlign(Paint.Align.CENTER);
        SVTools.pRegular[1].setTextAlign(Paint.Align.CENTER);

        //Gestión de tamaño de pinceles según orientación
        if (orientation) {
            SVTools.pBold[0].setTextSize(screenWidth / 18);
            SVTools.pRegular[0].setTextSize(screenWidth / 12);
            SVTools.pRegular[1].setTextSize(screenWidth / 18);
        } else {
            SVTools.pBold[0].setTextSize(screenWidth / 30);
            SVTools.pRegular[0].setTextSize(screenWidth / 24);
            SVTools.pRegular[1].setTextSize(screenWidth / 30);
        }

        //Inicializacion imágenes:
        img1 = R.drawable.gamemode;
        img2 = R.drawable.gamemode;
        bGameMode1 = BitmapFactory.decodeResource(context.getResources(), img1);
        bGameMode2 = BitmapFactory.decodeResource(context.getResources(), img2);

        //Inicializacion rectángulos
        rGameMode1 = new Rect();
        rGameMode2 = new Rect();
        rGMStroke1 = new Rect();
        rGMStroke2 = new Rect();
        rContinue = new Rect();

        //Inicialización textos:
        sGameMode1 = context.getResources().getString(R.string.gamemode1);
        sGameMode2 = context.getResources().getString(R.string.gamemode2);
        //Detecta si pulsamos la opción tutorial o jugar del menú principal
        if (tutorial) {
            sContinue = context.getResources().getString(R.string.watch_tutorial);
        } else {
            sContinue = context.getResources().getString(R.string.start);
        }
    }

    //TODO Error muy extraño: si dibujo primero los bitmaps y luego rContinue, no funciona bien la pulsación...
    // Sin embargo, si se hace del revés funciona a la perfección. Comentarlo con Javi y buscar solución para redibujado
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        SVTools.optionSeparation = (int) (SVTools.pRegular[0].getTextSize() * 2 - (SVTools.pRegular[0].getTextSize() / 5));
        //Obteniendo ancho de texto
        SVTools.pRegular[0].getTextBounds(sContinue, 0, sContinue.length(), rContinue);
        int rContinueLength = rContinue.right;

        if (orientation) {
            Log.i("Orientation", "drawPort: " + screenWidth + ":" + screenHeight);
            //Dibujo de rectángulos (Vertical):
            //Rect rGameMode1
            rGameMode1.left = screenWidth / 6;
            rGameMode1.right = rGameMode1.left + bGameMode1.getWidth();
            rGameMode1.top = screenHeight / 8;
            rGameMode1.bottom = rGameMode1.top + bGameMode1.getHeight();

            //Rect rGameMode2
            rGameMode2.left = screenWidth / 6;
            rGameMode2.right = screenWidth / 6 + bGameMode2.getWidth();
            rGameMode2.top = screenHeight / 4 + bGameMode2.getHeight();
            rGameMode2.bottom = screenHeight / 4 + bGameMode2.getHeight() * 2;

            //Rect rContinue
            rContinue.left = screenWidth / 2 - (rContinueLength / 2);
            rContinue.right = screenWidth / 2 + (rContinueLength / 2);
            rContinue.top = (int) (screenHeight - (screenHeight / 4) + (SVTools.optionSeparation * 2)
                    - SVTools.pRegular[0].getTextSize());
            rContinue.bottom = (int) (screenHeight - (screenHeight / 4) + (SVTools.optionSeparation * 2)
                    + (SVTools.pRegular[0].getTextSize() / 3));

            //Dibujo de imágenes (Vertical):
            //Bitmap bGameMode1
            bGameMode1 = SVTools.scaleHeight(img1, screenHeight - (screenHeight / 3));
            bGameMode1 = SVTools.scaleWidth(img1, screenWidth - (screenWidth / 3));

            //Bitmap bGameMode2
            bGameMode2 = SVTools.scaleHeight(img2, screenHeight - (screenHeight / 3));
            bGameMode2 = SVTools.scaleWidth(img2, screenWidth - (screenWidth / 3));

            //Dibuja imágenes
            canvas.drawBitmap(bGameMode1, screenWidth / 6, screenHeight / 8, null);
            canvas.drawBitmap(bGameMode2, screenWidth / 6, screenHeight / 4 + bGameMode1.getHeight(), null);

            //Dibujo de textos (Vertical);
            canvas.drawText(context.getString(R.string.select_gamemode), screenWidth / 2 + SVTools.iconSeparation,
                    screenHeight / 19, SVTools.pBold[0]);
            canvas.drawText(sContinue, screenWidth / 2, screenHeight - (screenHeight / 4) +
                    (SVTools.optionSeparation * 2), SVTools.pRegular[0]);
            canvas.drawText(sGameMode1, screenWidth / 2, rGameMode1.bottom + SVTools.getPixels(40),
                    SVTools.pRegular[1]);
            canvas.drawText(sGameMode2, screenWidth / 2, rGameMode2.bottom + SVTools.getPixels(40),
                    SVTools.pRegular[1]);
        } else {
            Log.i("Orientation", "drawLand: " + screenWidth + ":" + screenHeight);
            //Dibujo de rectángulos (Horizontal):
            //Rect rGameMode1
            rGameMode1.left = screenWidth / 12;
            rGameMode1.right = screenWidth / 12 + bGameMode1.getWidth();
            rGameMode1.top = screenHeight / 5;
            rGameMode1.bottom = screenHeight / 5 + bGameMode1.getHeight();

            //Rect rGameMode2
            rGameMode2.left = screenWidth - (screenWidth / 12) - bGameMode2.getWidth();
            rGameMode2.right = screenWidth - (screenWidth / 12);
            rGameMode2.top = screenHeight / 5;
            rGameMode2.bottom = screenHeight / 5 + bGameMode2.getHeight();

            //Rect rContinue
            rContinue.left = screenWidth / 2 - (rContinueLength / 2);
            rContinue.right = screenWidth / 2 + (rContinueLength / 2);
            rContinue.top = (int) (screenHeight - (screenHeight / 4) + SVTools.optionSeparation -
                    SVTools.pRegular[0].getTextSize());
            rContinue.bottom = (int) (screenHeight - (screenHeight / 4) + SVTools.optionSeparation +
                    (SVTools.pRegular[0].getTextSize() / 3));

            //TODO Pequeña condición "chapucera", tratar de solucionar el problema de reescalado de Bitmaps.
            // IllegalArgumentException: width and height must be > 0
            if (screenWidth > 0 && screenHeight > 0) {
                //Dibujo de imágenes (Horizontal):
                //Bitmap bGameMode1
                bGameMode1 = SVTools.scaleHeight(img1, screenHeight / 2);
                bGameMode1 = SVTools.scaleWidth(img1, screenWidth / 3);

                //Bitmap bGameMode2
                bGameMode2 = SVTools.scaleHeight(img2, screenHeight / 2);
                bGameMode2 = SVTools.scaleWidth(img2, screenWidth / 3);

                //Dibuja imágenes
                canvas.drawBitmap(bGameMode1, screenWidth / 12, screenHeight / 5, null);
                canvas.drawBitmap(bGameMode1, screenWidth - (screenWidth / 12) - bGameMode2.getWidth(),
                        screenHeight / 5, null);
            }

            //Dibujo de textos (Horizontal):
            canvas.drawText(context.getString(R.string.select_gamemode), screenWidth / 2,
                    screenHeight / 9, SVTools.pBold[0]);
            canvas.drawText(sContinue, screenWidth / 2, screenHeight - (screenHeight / 4) +
                    SVTools.optionSeparation, SVTools.pRegular[0]);
            canvas.drawText(sGameMode1, rGameMode1.left + (bGameMode1.getWidth() / 2), rGameMode1.bottom + SVTools.getPixels(40),
                    SVTools.pRegular[1]);
            canvas.drawText(sGameMode2, rGameMode2.left + (bGameMode2.getWidth() / 2), rGameMode2.bottom + SVTools.getPixels(40),
                    SVTools.pRegular[1]);
        }
        //Dibuja rectángulos
        //Rect rGMStroke1
        rGMStroke1.left = rGameMode1.left;
        rGMStroke1.right = rGameMode1.right;
        rGMStroke1.top = rGameMode1.top;
        rGMStroke1.bottom = rGameMode1.bottom;

        //Rect rGMStroke2
        rGMStroke2.left = rGameMode2.left;
        rGMStroke2.right = rGameMode2.right;
        rGMStroke2.top = rGameMode2.top;
        rGMStroke2.bottom = rGameMode2.bottom;

        //Gestión de marcado de modo de juego seleccionado
        if (gameSelected) {
            canvas.drawRect(rGMStroke1, pGMStroke);
        } else {
            canvas.drawRect(rGMStroke2, pGMStroke);
        }
        canvas.drawRect(rGameMode1, pGameMode);
        canvas.drawRect(rGameMode2, pGameMode);
        canvas.drawRect(rContinue, SVTools.pRects);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rGameMode1.contains((int) event.getX(), (int) event.getY())) {
                    gameSelected = true;
                    if (vibration) vibrate(10);
                }
                if (rGameMode2.contains((int) event.getX(), (int) event.getY())) {
                    gameSelected = false;
                    if (vibration) vibrate(10);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (rContinue.contains((int) event.getX(), (int) event.getY()) && tutorial) {
                    intentFlag = true;
                    if (vibration) vibrate(10);
                    if (gameSelected) {
                        Tools.createIntent(context, TutorialGameMode1.class, false, false);
                    }
                    if (!gameSelected) {
                        Tools.createIntent(context, TutorialGameMode2.class, false, false);
                    }
                }
                if (rContinue.contains((int) event.getX(), (int) event.getY()) && !tutorial) {
                    if (vibration) vibrate(10);
                    if (gameSelected) {
                        Tools.createIntent(context, Game1Activity.class, false, true);
                    }
                    if (!gameSelected) {
                        Tools.createIntent(context, Game1Activity.class, false, true);
                    }
                    if (music && gameMusic.isPlaying()) gameMusic.stop();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
