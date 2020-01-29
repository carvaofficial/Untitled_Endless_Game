package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.example.untitledendlessgame.R;
import com.example.untitledendlessgame.Tutorials.TutorialGameMode1;
import com.example.untitledendlessgame.Tutorials.TutorialGameMode2;

//TODO al cambiar la orientación en esta escena, salta IllegalArgumentException: width and height must be > 0
// Preguntar a Javi porqué los valores screenWidth y screenHeight se quedan a 0, es provocado por los Bitmaps
// También revisar qué falla al pulsar rContinue, tras varias pulsaciones se ejecuta el codigo de onTouchEvent();
public class GameModeScene extends Scene {
    String sContinue, sGameMode1, sGameMode2;
    boolean tutorial, gameSelected;
    int img1, img2;
    Paint pGMStroke, pGameMode;
    Rect rGameMode1, rGameMode2, rGMStroke1, rGMStroke2, rContinue;
    Bitmap bGameMode1, bGameMode2;
    Intent intent;

    public GameModeScene(int sceneNumber, int screenWidth, int screenHeight, Context context, boolean orientation, boolean tutorial) {
        super(sceneNumber, screenWidth, screenHeight, context, orientation);
        this.tutorial = tutorial;
        this.gameSelected = true;
        Log.i("Orientation", "public GameModeScene: " + screenWidth + ":" + screenHeight);

        //Inicializacion pinceles:
        pGMStroke = new Paint();
        pGMStroke.setColor(Color.WHITE);
        pGMStroke.setAntiAlias(true);
        pGMStroke.setStyle(Paint.Style.STROKE);
        pGMStroke.setStrokeWidth(5);
        pGameMode = new Paint();
        pGameMode.setColor(Color.argb(0, 0, 0, 0));

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

        //Propiedades Paints:
        util.pBold[0].setTextAlign(Paint.Align.CENTER);
        util.pRegular[0].setTextAlign(Paint.Align.CENTER);
        util.pRegular[1].setTextAlign(Paint.Align.CENTER);


        //Gestión de tamaño de pinceles según orientación
        if (orientation) {
            util.pBold[0].setTextSize(screenWidth / 18);
            util.pRegular[0].setTextSize(screenWidth / 12);
            util.pRegular[1].setTextSize(screenWidth / 18);
        } else {
            util.pBold[0].setTextSize(screenWidth / 30);
            util.pRegular[0].setTextSize(screenWidth / 24);
            util.pRegular[1].setTextSize(screenWidth / 30);
        }

        //Detecta si pulsamos la opción tutorial o jugar del menú principal
        if (tutorial) {
            sContinue = "Ver tutorial";
        } else {
            sContinue = "Comenzar";
        }

        //Inicialización textos:
        sGameMode1 = "\"Jumpin'\" boxes";
        sGameMode2 = "Going up and up";
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        util.optionSeparation = (int) (util.pRegular[0].getTextSize() * 2 - (util.pRegular[0].getTextSize() / 5));

        //Obteniendo ancho de texto
        util.pRegular[0].getTextBounds(sContinue, 0, sContinue.length(), rContinue);
        int rContinueLength = rContinue.right;

        if (orientation) {
            Log.i("Orientation", "drawPort: " + screenWidth + ":" + screenHeight);

            //Esto es una solución temporal, buscar la manera de arreglarlo
            if (screenWidth > 0 && screenHeight > 0) {
                //Dibujo de imágenes (Vertical):
                //Bitmap bGameMode1
                bGameMode1 = util.scaleHeight(img1, screenHeight - (screenHeight / 3));
                bGameMode1 = util.scaleWidth(img1, screenWidth - (screenWidth / 3));

                //Bitmap bGameMode2
                bGameMode2 = util.scaleHeight(img2, screenHeight - (screenHeight / 3));
                bGameMode2 = util.scaleWidth(img2, screenWidth - (screenWidth / 3));

                //Dibuja imágenes
                canvas.drawBitmap(bGameMode1, screenWidth / 6, screenHeight / 8, null);
                canvas.drawBitmap(bGameMode2, screenWidth / 6, screenHeight / 4 + bGameMode1.getHeight(), null);
            }

            //Dibujo de rectángulos (Vertical):
            //Rect rContinue
            rContinue.left = screenWidth / 2 - (rContinueLength / 2);
            rContinue.right = screenWidth / 2 + (rContinueLength / 2);
            rContinue.top = (int) (screenHeight - (screenHeight / 4) + (util.optionSeparation * 2)
                    - util.pRegular[0].getTextSize());
            rContinue.bottom = (int) (screenHeight - (screenHeight / 4) + (util.optionSeparation * 2)
                    + (util.pRegular[0].getTextSize() / 3));
//            rContinue.left =;
//            rContinue.right =;
//            rContinue.top =;
//            rContinue.bottom =;

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

            //Dibujo de textos (Vertical);
            canvas.drawText("Seleccionar modo de juego", screenWidth / 2 + util.iconSeparation,
                    screenHeight / 19, util.pBold[0]);
            canvas.drawText(sContinue, screenWidth / 2, screenHeight - (screenHeight / 4) +
                    (util.optionSeparation * 2), util.pRegular[0]);
            canvas.drawText(sGameMode1, screenWidth / 2, rGameMode1.bottom + util.getPixels(40),
                    util.pRegular[1]);
            canvas.drawText(sGameMode2, screenWidth / 2, rGameMode2.bottom + util.getPixels(40),
                    util.pRegular[1]);
        } else {
            Log.i("Orientation", "drawLand: " + screenWidth + ":" + screenHeight);
            if (screenWidth > 0 && screenHeight > 0) {
                //Dibujo de imágenes (Horizontal):
                //Bitmap bGameMode1
                bGameMode1 = util.scaleHeight(img1, screenHeight / 2);
                bGameMode1 = util.scaleWidth(img1, screenWidth / 3);

                //Bitmap bGameMode2
                bGameMode2 = util.scaleHeight(img2, screenHeight / 2);
                bGameMode2 = util.scaleWidth(img2, screenWidth / 3);

                //Dibuja imágenes
                canvas.drawBitmap(bGameMode1, screenWidth / 12, screenHeight / 5, null);
                canvas.drawBitmap(bGameMode1, screenWidth - (screenWidth / 12) - bGameMode2.getWidth(),
                        screenHeight / 5, null);
            }

            //Dibujo de rectángulos (Horizontal):
            //Rect rContinue
            rContinue.left = screenWidth - (screenWidth / 2) - (rContinueLength / 2);
            rContinue.right = screenWidth - (screenWidth / 2) + (rContinueLength / 2);
            rContinue.top = (int) (screenHeight - (screenHeight / 4) + util.optionSeparation -
                    util.pRegular[0].getTextSize());
            rContinue.bottom = (int) (screenHeight - (screenHeight / 4) + util.optionSeparation +
                    (util.pRegular[0].getTextSize() / 3));
//            rContinue.left =;
//            rContinue.right =;
//            rContinue.top =;
//            rContinue.bottom =;

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

            //Dibujo de textos (Horizontal):
            canvas.drawText("Seleccionar modo de juego", screenWidth / 2,
                    screenHeight / 9, util.pBold[0]);
            canvas.drawText(sContinue, screenWidth / 2, screenHeight - (screenHeight / 4) +
                    util.optionSeparation, util.pRegular[0]);
            canvas.drawText(sGameMode1, rGameMode1.left + (bGameMode1.getWidth() / 2), rGameMode1.bottom + util.getPixels(40),
                    util.pRegular[1]);
            canvas.drawText(sGameMode2, rGameMode2.left + (bGameMode2.getWidth() / 2), rGameMode2.bottom + util.getPixels(40),
                    util.pRegular[1]);
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
        canvas.drawRect(rContinue, util.pRects);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rGameMode1.contains((int) event.getX(), (int) event.getY())) {
                    gameSelected = true;
                    Log.i("GameMode", "onTouchEvent: entra rGameMode1");
                }
                if (rGameMode2.contains((int) event.getX(), (int) event.getY())) {
                    gameSelected = false;
                    Log.i("GameMode", "onTouchEvent: entra rGameMode2");
                }
                break;
            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_MOVE:
                if (rContinue.contains((int) event.getX(), (int) event.getY()) && tutorial) {
                    Log.i("GameMode", "onTouchEvent: entra rContinue");
                    if (gameSelected) {
                        Log.i("GameMode", "onTouchEvent: gameSelected");
                        intent = new Intent(context, TutorialGameMode1.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent);
                    }
                    if (!gameSelected) {
                        Log.i("GameMode", "onTouchEvent: !gameSelected");
                        intent = new Intent(context, TutorialGameMode2.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent);
                    }
                }else if (rContinue.contains((int) event.getX(), (int) event.getY()) && !tutorial) {
                    Log.i("GameMode", "onTouchEvent: entra !rContinue");
                    if (gameSelected) {
                        Log.i("GameMode", "onTouchEvent: gameSelected");
                    }
                    if (!gameSelected) {
                        Log.i("GameMode", "onTouchEvent: !gameSelected");
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
