package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.example.untitledendlessgame.R;

public class MenuScene extends Scene {
    Context context;
    Paint pTitle, pOptions, pIcons;
    Rect rPlay, rTutorial, rAchievments, rMarkers, rSettings, rCredits;

    public MenuScene(int sceneNumber, int screenWidth, int screenHeight, Context context, boolean orientation) {
        super(sceneNumber, screenWidth, screenHeight, context, orientation);
        this.context = context;

        //Declaración e inicialización pinceles para textos:
        //Paint titulo
        pTitle = new Paint();
        pTitle.setColor(Color.WHITE);
        pTitle.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/comfortaa-bold.ttf"));
        pTitle.setTextAlign(Paint.Align.CENTER);
        pTitle.setAntiAlias(true);

        //Paint texto opciones menu
        pOptions = new Paint();
        pOptions.setColor(Color.WHITE);
        pOptions.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/comfortaa-regular.ttf"));
        pOptions.setTextAlign(Paint.Align.CENTER);
        pOptions.setAntiAlias(true);

        //Paint iconos
        pIcons = new Paint();
        pIcons.setColor(Color.WHITE);
        pIcons.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/icons-solid.ttf"));
        pIcons.setAntiAlias(true);

        //Declaración de rectángulos:
        rPlay = new Rect();
        rTutorial = new Rect();
        rAchievments = new Rect();
        rMarkers = new Rect();
        rSettings = new Rect();
        rCredits = new Rect();

        //Gestión de tamaño de pinceles según orientación
        if (orientation) {
            pTitle.setTextSize(screenWidth / 6);
            pOptions.setTextSize(screenWidth / 12);
            pIcons.setTextSize(pOptions.getTextSize());
        } else {
            pTitle.setTextSize(screenWidth / 12);
            pOptions.setTextSize(screenWidth / 24);
            pIcons.setTextSize(pOptions.getTextSize());
        }
    }

    //TODO Comfigurar el dibujado según la orientación
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Dibujo de textos:
        String mainOptions[] = {"Jugar", "Tutorial", "Creditos", context.getString(R.string.icon_config),
                context.getString(R.string.icon_achievments), context.getString(R.string.icon_markers)};
        int optSeparation = (int) (pOptions.getTextSize() * 2 - (pOptions.getTextSize() / 3));

        //Obteniendo ancho de textos e iconos
        pOptions.getTextBounds(mainOptions[0], 0, mainOptions[0].length(), rPlay);
        pOptions.getTextBounds(mainOptions[1], 0, mainOptions[1].length(), rTutorial);
        pOptions.getTextBounds(mainOptions[2], 0, mainOptions[2].length(), rCredits);
        pIcons.getTextBounds(mainOptions[3], 0, mainOptions[3].length(), rSettings);
        pIcons.getTextBounds(mainOptions[4], 0, mainOptions[4].length(), rAchievments);
        pIcons.getTextBounds(mainOptions[5], 0, mainOptions[5].length(), rMarkers);
        int rLengths[] = {rPlay.right, rTutorial.right, rCredits.right, rSettings.right,
                rAchievments.right, rMarkers.right};


        //Dibuja titulo
        canvas.drawText("Untitled", screenWidth / 2, screenHeight / 4, pTitle);
        canvas.drawText("Endless", screenWidth / 2, (screenHeight / 4) + pTitle.getTextSize(), pTitle);
        canvas.drawText("Game", screenWidth / 2, (screenHeight / 4) + (pTitle.getTextSize() * 2), pTitle);

        //Dibuja iconos
        int iconSeparation = getPixels(15);
        canvas.drawText(mainOptions[3], iconSeparation,
                iconSeparation + pIcons.getTextSize(), pIcons);
        canvas.drawText(mainOptions[4], (iconSeparation * 2) +
                pIcons.getTextSize(), iconSeparation + pIcons.getTextSize(), pIcons);
        canvas.drawText(mainOptions[5], (iconSeparation * 3) +
                (pIcons.getTextSize() * 2), iconSeparation + pIcons.getTextSize(), pIcons);

        if (orientation) {
            //Dibuja opciones (Vertical)
            canvas.drawText(mainOptions[0], screenWidth / 2, screenHeight - (screenHeight / 4), pOptions);
            canvas.drawText(mainOptions[1], screenWidth / 2, screenHeight - (screenHeight / 4) +
                    optSeparation, pOptions);
            canvas.drawText(mainOptions[2], screenWidth / 2, screenHeight - (screenHeight / 4) +
                    (optSeparation * 2), pOptions);

            //Dibujo de rectángulos  (Vertical):
            //Rect rPlay
            rPlay.left = screenWidth / 2 - (rLengths[0] / 2);
            rPlay.right = screenWidth / 2 + (rLengths[0] / 2);
            rPlay.top = (int) (screenHeight - (screenHeight / 4) - pOptions.getTextSize());
            rPlay.bottom = (int) (screenHeight - (screenHeight / 4) + (pOptions.getTextSize() / 3));

            //Rect rTutorial
            rTutorial.left = screenWidth / 2 - (rLengths[1] / 2);
            rTutorial.right = screenWidth / 2 + (rLengths[1] / 2);
            rTutorial.top = (int) (screenHeight - (screenHeight / 4) + optSeparation - pOptions.getTextSize());
            rTutorial.bottom = (int) (screenHeight - (screenHeight / 4) + optSeparation + (pOptions.getTextSize() / 3));

            //Rect rCredits
            rCredits.left = screenWidth / 2 - (rLengths[2] / 2);
            rCredits.right = screenWidth / 2 + (rLengths[2] / 2);
            rCredits.top = (int) (screenHeight - (screenHeight / 4) + (optSeparation * 2) - pOptions.getTextSize());
            rCredits.bottom = (int) (screenHeight - (screenHeight / 4) + (optSeparation * 2) + (pOptions.getTextSize() / 3));

            //Rect rSettings
            rSettings.left = iconSeparation;
            rSettings.right = iconSeparation + rLengths[3];
            rSettings.top = iconSeparation + getPixels(5);
            rSettings.bottom = iconSeparation + getPixels(5) + rLengths[3];

            //Rect rAchievments
            rAchievments.left = (iconSeparation * 2) + rLengths[3];
            rAchievments.right = (iconSeparation * 2) + (rLengths[4] * 2);
            rAchievments.top = iconSeparation + getPixels(5);
            rAchievments.bottom = iconSeparation + getPixels(5) + rLengths[4];

            //Rect rMarkers
            rMarkers.left = (iconSeparation * 3) + (rLengths[3] * 2);
            rMarkers.right = (iconSeparation * 3) + (rLengths[3] * 2) + rLengths[5];
            rMarkers.top = iconSeparation + getPixels(5);
            rMarkers.bottom = iconSeparation + getPixels(5) + rLengths[4];
        } else {
            int landSep = getPixels(20);
            //Dibuja opciones (Horizontal)
            canvas.drawText(mainOptions[0], screenWidth / 2, screenHeight - (screenHeight / 4) +
                    optSeparation, pOptions);
            canvas.drawText(mainOptions[1], screenWidth / 3 - landSep, screenHeight - (screenHeight / 4) +
                    optSeparation, pOptions);
            canvas.drawText(mainOptions[2], screenWidth - (screenWidth / 3) + landSep, screenHeight - (screenHeight / 4) +
                    optSeparation, pOptions);

            //Dibujo de rectángulos  (Horizontal):
            //Rect rPlay
            rPlay.left = screenWidth / 2 - (rLengths[0] / 2);
            rPlay.right = screenWidth / 2 + (rLengths[0] / 2);
            rPlay.top = (int) (screenHeight - (screenHeight / 4) + optSeparation - pOptions.getTextSize());
            rPlay.bottom = (int) (screenHeight - (screenHeight / 4) + optSeparation + (pOptions.getTextSize() / 3));

            //Rect rTutorial
            rTutorial.left = screenWidth / 3 - (rLengths[1] / 2) - landSep;
            rTutorial.right = screenWidth / 3 + (rLengths[1] / 2) - landSep;
            rTutorial.top = (int) (screenHeight - (screenHeight / 4) + optSeparation - pOptions.getTextSize());
            rTutorial.bottom = (int) (screenHeight - (screenHeight / 4) + optSeparation + (pOptions.getTextSize() / 3));

            //Rect rCredits
            rCredits.left = screenWidth - (screenWidth / 3) - (rLengths[2] / 2) + landSep;
            rCredits.right = screenWidth - (screenWidth / 3) + (rLengths[2] / 2) + landSep;
            rCredits.top = (int) (screenHeight - (screenHeight / 4) + optSeparation - pOptions.getTextSize());
            rCredits.bottom = (int) (screenHeight - (screenHeight / 4) + optSeparation + (pOptions.getTextSize() / 3));

            //Rect rSettings
            rSettings.left = iconSeparation;
            rSettings.right = iconSeparation + rLengths[3];
            rSettings.top = iconSeparation + getPixels(5);
            rSettings.bottom = iconSeparation + getPixels(5) + rLengths[3];

            //Rect rAchievments
            rAchievments.left = (iconSeparation * 2) + rLengths[3];
            rAchievments.right = (iconSeparation * 2) + (rLengths[4] * 2);
            rAchievments.top = iconSeparation + getPixels(5);
            rAchievments.bottom = iconSeparation + getPixels(5) + rLengths[4];

            //Rect rMarkers
            rMarkers.left = (iconSeparation * 3) + (rLengths[3] * 2);
            rMarkers.right = (iconSeparation * 3) + (rLengths[3] * 2) + rLengths[5];
            rMarkers.top = iconSeparation + getPixels(5);
            rMarkers.bottom = iconSeparation + getPixels(5) + rLengths[4];
        }

        //Dibuja rectángulos de opciones
        canvas.drawRect(rPlay, super.pRects);
        canvas.drawRect(rTutorial, super.pRects);
        canvas.drawRect(rCredits, super.pRects);
        canvas.drawRect(rSettings, super.pRects);
        canvas.drawRect(rAchievments, super.pRects);
        canvas.drawRect(rMarkers, super.pRects);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (rPlay.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.PLAY;
                }
                if (rTutorial.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.TUTORIAL;
                }
                if (rAchievments.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.ACHIEVMENTS;
                }
                if (rMarkers.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.MARKERS;
                }
                if (rSettings.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.SETTINGS;
                }
                if (rCredits.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.CREDITS;
                }
        }
        return sceneNumber;
    }
}
