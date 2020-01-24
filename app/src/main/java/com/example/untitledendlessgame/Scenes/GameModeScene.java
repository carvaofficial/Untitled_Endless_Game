package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

//TODO falta insertar imgaenes de ejemplo de GameMode y diseñar sus rects, preguntar a Javi sobre
// el uso de intents en una escena, para pasar de SurfaceView a un Layout y cómo volver sin problemas.
public class GameModeScene extends Scene {
    String sContinue;
    boolean tutorial;
    Paint pGameMode;
    Rect rGameMode1, rGameMode2, rContinue;

    public GameModeScene(int sceneNumber, int screenWidth, int screenHeight, Context context, boolean orientation, boolean tutorial) {
        super(sceneNumber, screenWidth, screenHeight, context, orientation);
        this.tutorial = tutorial;

        //Declaración e inicialización pinceles
        pGameMode = new Paint();
        pGameMode.setColor(Color.argb(0, 0, 0, 0));
        pGameMode.setColor(Color.WHITE);
        pGameMode.setAntiAlias(true);
        pGameMode.setStyle(Paint.Style.STROKE);
        pGameMode.setStrokeWidth(5);

        //Declaración rectángulos
        rGameMode1 = new Rect();
        rGameMode2 = new Rect();
        rContinue = new Rect();

        //Gestión de tamaño de pinceles según orientación
        if (orientation) {
            util.pRegular.setTextSize(screenWidth / 18);
            util.pRegular2.setTextSize(screenWidth / 12);
        } else {
            util.pRegular.setTextSize(screenWidth / 30);
            util.pRegular2.setTextSize(screenWidth / 24);
        }

        //Detecta si pulsamos la opción tutorial o jugar del menú principal
        if (tutorial) {
            sContinue = "Ver tutorial";
        } else {
            sContinue = "Comenzar";
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        util.optionSeparation = (int) (util.pRegular2.getTextSize() * 2 - (util.pRegular2.getTextSize() / 5));

        //Obteniendo ancho de texto
        util.pRegular2.getTextBounds(sContinue, 0, sContinue.length(), rContinue);
        int rContinueLength = rContinue.right;

        if (orientation) {
            //Dibujo de textos (Vertical);
            canvas.drawText("Seleccionar modo de juego", screenWidth / 2 + util.iconSeparation,
                    screenHeight / 19, util.pRegular);
            canvas.drawText(sContinue, screenWidth / 2, screenHeight - (screenHeight / 4) +
                    (util.optionSeparation * 2), util.pRegular2);

            //Dibujo de rectángulos (Vertical):
            //Rect rContinue
            rContinue.left = screenWidth / 2 - (rContinueLength / 2);
            rContinue.right = screenWidth / 2 + (rContinueLength / 2);
            rContinue.top = (int) (screenHeight - (screenHeight / 4) + (util.optionSeparation * 2)
                    - util.pRegular2.getTextSize());
            rContinue.bottom = (int) (screenHeight - (screenHeight / 4) + (util.optionSeparation * 2)
                    + (util.pRegular2.getTextSize() / 3));

            //Rect rGameMode1
            rGameMode1.left = screenWidth / 4;
            rGameMode1.right = screenWidth / 4;
            rGameMode1.top = screenWidth / 4;
            rGameMode1.bottom = screenWidth / 4;

            //Rect rGameMode2
            rGameMode2.left = screenWidth - (screenWidth / 4);
            rGameMode2.right = screenWidth - (screenWidth / 4);
            rGameMode2.top = screenWidth - (screenWidth / 4);
            rGameMode2.bottom = screenWidth - (screenWidth / 4);

        } else {
            //Dibujo de textos (Horizontal):
            canvas.drawText("Seleccionar modo de juego", screenWidth / 2,
                    screenHeight / 9, util.pRegular);
            canvas.drawText(sContinue, screenWidth / 2, screenHeight - (screenHeight / 4) +
                    util.optionSeparation, util.pRegular2);

            //Dibujo de rectángulos (Horizontal):
            //Rect rContinue
            rContinue.left = screenWidth / 2 - (rContinueLength / 2);
            rContinue.right = screenWidth / 2 + (rContinueLength / 2);
            rContinue.top = (int) (screenHeight - (screenHeight / 4) + util.optionSeparation
                    - util.pRegular2.getTextSize());
            rContinue.bottom = (int) (screenHeight - (screenHeight / 4) + util.optionSeparation
                    + (util.pRegular2.getTextSize() / 3));

            //Rect rGameMode1
//            rGameMode1.left = screenWidth / 4;
//            rGameMode1.right = screenWidth / 4;
//            rGameMode1.top = screenWidth / 4;
//            rGameMode1.bottom = screenWidth / 4;

            //Rect rGameMode2
//            rGameMode2.left =;
//            rGameMode2.right =;
//            rGameMode2.top =;
//            rGameMode2.bottom =;
        }

        //Dibuja rectángulos
        canvas.drawRect(rGameMode1, pGameMode);
        canvas.drawRect(rGameMode2, pGameMode);
        canvas.drawRect(rContinue, util.pRects);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (rGameMode1.contains((int) event.getX(), (int) event.getY()) && tutorial) {

                } else {

                }
                if (rGameMode2.contains((int) event.getX(), (int) event.getY()) && tutorial) {

                } else {

                }
                if (rContinue.contains((int) event.getX(), (int) event.getY()) && tutorial) {

                } else {

                }
        }
        return super.onTouchEvent(event);
    }
}
