package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

public class MenuScene extends Scene {
    Context context;
    Paint pTitle, pOptions;
    Rect rPlay, rTutorial, rCredits;

    public MenuScene(int sceneNumber, int screenWidth, int screenHeight, Context context) {
        super(sceneNumber, screenWidth, screenHeight, context);
        this.context = context;

        //Inicialización pinceles para textos
        //Paint titulo
        pTitle = new Paint();
        pTitle.setColor(Color.WHITE);
        pTitle.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/comfortaa-bold.ttf"));
        pTitle.setTextSize(screenWidth / 7);
        pTitle.setTextAlign(Paint.Align.CENTER);
        pTitle.setAntiAlias(true);

        //Paint texto opciones menu
        pOptions = new Paint();
        pOptions.setColor(Color.WHITE);
        pOptions.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/comfortaa-regular.ttf"));
        pOptions.setTextSize(screenWidth / 12);
        pOptions.setTextAlign(Paint.Align.CENTER);
        pOptions.setAntiAlias(true);

        //Rect opciones
        rPlay = new Rect();
        rTutorial = new Rect();
        rCredits = new Rect();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //Dibuja titulo
        canvas.drawText("Untitled", screenWidth / 2, screenHeight / 6, pTitle);
        canvas.drawText("Endless", screenWidth / 2, (screenHeight / 6) + pTitle.getTextSize(), pTitle);
        canvas.drawText("Game", screenWidth / 2, (screenHeight / 6) + (pTitle.getTextSize() * 2), pTitle);

        //Dibuja opciones
        String play = "Jugar", tutorial = "Tutorial", credits = "Creditos";
        int optSeparation = (int) (pOptions.getTextSize() * 2 - (pOptions.getTextSize() / 3));

        canvas.drawText(play, screenWidth / 2, screenHeight - (screenHeight / 3), pOptions);
        canvas.drawText(tutorial, screenWidth / 2, screenHeight - (screenHeight / 3) +
                optSeparation, pOptions);
        canvas.drawText(credits, screenWidth / 2, screenHeight - (screenHeight / 3) +
                (optSeparation * 2), pOptions);

        //Ajustando tamaño y posición de los rectángulos
        pOptions.getTextBounds(play, 0, play.length(), rPlay);
        pOptions.getTextBounds(tutorial, 0, tutorial.length(), rTutorial);
        pOptions.getTextBounds(credits, 0, credits.length(), rCredits);
        int playLength = rPlay.right, tutorialLength = rTutorial.right, creditsLength = rCredits.right;

        rPlay.left = screenWidth / 2 + (playLength / 2);
        rPlay.right = screenWidth / 2 - (playLength / 2);
        rPlay.top = (int) (screenHeight - (screenHeight / 3) + (pOptions.getTextSize() / 3));
        rPlay.bottom = (int) (screenHeight - (screenHeight / 3) - pOptions.getTextSize());

        rTutorial.left = screenWidth / 2 + (tutorialLength / 2);
        rTutorial.right = screenWidth / 2 - (tutorialLength / 2);
        rTutorial.top = (int) (screenHeight - (screenHeight / 3) + optSeparation + (pOptions.getTextSize() / 3));
        rTutorial.bottom = (int) (screenHeight - (screenHeight / 3) + optSeparation - pOptions.getTextSize());

        rCredits.left = screenWidth / 2 + (creditsLength / 2);
        rCredits.right = screenWidth / 2 - (creditsLength / 2);
        rCredits.top = (int) (screenHeight - (screenHeight / 3) + (optSeparation * 2) + (pOptions.getTextSize() / 3));
        rCredits.bottom = (int) (screenHeight - (screenHeight / 3) + (optSeparation * 2) - pOptions.getTextSize());

        //Dibuja rectángulos de opciones
        canvas.drawRect(rPlay, super.pRects);
        canvas.drawRect(rTutorial, super.pRects);
        canvas.drawRect(rCredits, super.pRects);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (rPlay.contains((int) event.getX(), (int) event.getY())) {
                    Log.i("PLAY", "onTouchEvent: PLAY");
                    return Scene.PLAY;
                }
                if (rTutorial.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.TUTORIAL;
                }
                if (rCredits.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.CREDITS;
                }
        }
        return sceneNumber;
    }
}
