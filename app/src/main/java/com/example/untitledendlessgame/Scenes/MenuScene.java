package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.untitledendlessgame.R;
import static com.example.untitledendlessgame.Resources.Tools.*;

public class MenuScene extends Scene {
    Context context;
    Rect rPlay, rTutorial, rAchievments, rMarkers, rSettings, rCredits;

    public MenuScene(int sceneNumber, Context context, int screenWidth, int screenHeight, boolean orientation) {
        super(sceneNumber, context, screenWidth, screenHeight, orientation);
        this.context = context;

        //Inicializacion de rectángulos:
        rPlay = new Rect();
        rTutorial = new Rect();
        rAchievments = new Rect();
        rMarkers = new Rect();
        rSettings = new Rect();
        rCredits = new Rect();

        //Propiedades Paints:
        SVTools.pBold[0].setTextAlign(Paint.Align.CENTER);
        SVTools.pRegular[0].setTextAlign(Paint.Align.CENTER);

        //Gestión de tamaño de pinceles según orientación
        if (orientation) {
            SVTools.pBold[0].setTextSize(screenWidth / 6);
            SVTools.pRegular[0].setTextSize(screenWidth / 12);
        } else {
            SVTools.pBold[0].setTextSize(screenWidth / 12);
            SVTools.pRegular[0].setTextSize(screenWidth / 24);
        }
        SVTools.pIcons.setTextSize(SVTools.pRegular[0].getTextSize());


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Dibujo de textos:
        String mainOptions[] = {context.getString(R.string.play), context.getString(R.string.tutorial),
                context.getString(R.string.credits), context.getString(R.string.icon_config),
                context.getString(R.string.icon_achievments), context.getString(R.string.icon_markers)};
        SVTools.optionSeparation = (int) (SVTools.pRegular[0].getTextSize() * 2 - (SVTools.pRegular[0].getTextSize() / 5));

        //Obteniendo ancho de textos e iconos
        SVTools.pRegular[0].getTextBounds(mainOptions[0], 0, mainOptions[0].length(), rPlay);
        SVTools.pRegular[0].getTextBounds(mainOptions[1], 0, mainOptions[1].length(), rTutorial);
        SVTools.pRegular[0].getTextBounds(mainOptions[2], 0, mainOptions[2].length(), rCredits);
        SVTools.pIcons.getTextBounds(mainOptions[3], 0, mainOptions[3].length(), rSettings);
        SVTools.pIcons.getTextBounds(mainOptions[4], 0, mainOptions[4].length(), rAchievments);
        SVTools.pIcons.getTextBounds(mainOptions[5], 0, mainOptions[5].length(), rMarkers);
        int rLengths[] = {rPlay.right, rTutorial.right, rCredits.right, rSettings.right,
                rAchievments.right, rMarkers.right};

        //Dibuja titulo
        canvas.drawText(context.getString(R.string.untitled), screenWidth / 2, screenHeight / 4, SVTools.pBold[0]);
        canvas.drawText(context.getString(R.string.endless), screenWidth / 2, (screenHeight / 4) +
                SVTools.pBold[0].getTextSize(), SVTools.pBold[0]);
        canvas.drawText(context.getString(R.string.game), screenWidth / 2, (screenHeight / 4) +
                (SVTools.pBold[0].getTextSize() * 2), SVTools.pBold[0]);

        //Dibuja iconos
        SVTools.iconSeparation = SVTools.getPixels(15);
        canvas.drawText(mainOptions[3], SVTools.iconSeparation,
                SVTools.iconSeparation + SVTools.pIcons.getTextSize(), SVTools.pIcons);
        canvas.drawText(mainOptions[4], (SVTools.iconSeparation * 2) +
                SVTools.pIcons.getTextSize(), SVTools.iconSeparation + SVTools.pIcons.getTextSize(), SVTools.pIcons);
        canvas.drawText(mainOptions[5], (SVTools.iconSeparation * 3) +
                (SVTools.pIcons.getTextSize() * 2), SVTools.iconSeparation + SVTools.pIcons.getTextSize(), SVTools.pIcons);

        if (orientation) {
            //Dibujo de opciones (Vertical);
            canvas.drawText(mainOptions[0], screenWidth / 2, screenHeight - (screenHeight / 4), SVTools.pRegular[0]);
            canvas.drawText(mainOptions[1], screenWidth / 2, screenHeight - (screenHeight / 4) +
                    SVTools.optionSeparation, SVTools.pRegular[0]);
            canvas.drawText(mainOptions[2], screenWidth / 2, screenHeight - (screenHeight / 4) +
                    (SVTools.optionSeparation * 2), SVTools.pRegular[0]);

            //Dibujo de rectángulos  (Vertical):
            //Rect rPlay
            rPlay.left = screenWidth / 2 - (rLengths[0] / 2);
            rPlay.right = screenWidth / 2 + (rLengths[0] / 2);
            rPlay.top = (int) (screenHeight - (screenHeight / 4) - SVTools.pRegular[0].getTextSize());
            rPlay.bottom = (int) (screenHeight - (screenHeight / 4) + (SVTools.pRegular[0].getTextSize() / 3));

            //Rect rTutorial
            rTutorial.left = screenWidth / 2 - (rLengths[1] / 2);
            rTutorial.right = screenWidth / 2 + (rLengths[1] / 2);
            rTutorial.top = (int) (screenHeight - (screenHeight / 4) + SVTools.optionSeparation -
                    SVTools.pRegular[0].getTextSize());
            rTutorial.bottom = (int) (screenHeight - (screenHeight / 4) + SVTools.optionSeparation
                    + (SVTools.pRegular[0].getTextSize() / 3));

            //Rect rCredits
            rCredits.left = screenWidth / 2 - (rLengths[2] / 2);
            rCredits.right = screenWidth / 2 + (rLengths[2] / 2);
            rCredits.top = (int) (screenHeight - (screenHeight / 4) + (SVTools.optionSeparation * 2)
                    - SVTools.pRegular[0].getTextSize());
            rCredits.bottom = (int) (screenHeight - (screenHeight / 4) + (SVTools.optionSeparation * 2)
                    + (SVTools.pRegular[0].getTextSize() / 3));

            //Rect rSettings
            rSettings.left = SVTools.iconSeparation;
            rSettings.right = SVTools.iconSeparation + rLengths[3];
            rSettings.top = SVTools.iconSeparation + SVTools.getPixels(5);
            rSettings.bottom = SVTools.iconSeparation + SVTools.getPixels(5) + rLengths[3];

            //Rect rAchievments
            rAchievments.left = (SVTools.iconSeparation * 2) + rLengths[3];
            rAchievments.right = (SVTools.iconSeparation * 2) + (rLengths[4] * 2);
            rAchievments.top = SVTools.iconSeparation + SVTools.getPixels(5);
            rAchievments.bottom = SVTools.iconSeparation + SVTools.getPixels(5) + rLengths[4];

            //Rect rMarkers
            rMarkers.left = (SVTools.iconSeparation * 3) + (rLengths[3] * 2);
            rMarkers.right = (SVTools.iconSeparation * 3) + (rLengths[3] * 2) + rLengths[5];
            rMarkers.top = SVTools.iconSeparation + SVTools.getPixels(5);
            rMarkers.bottom = SVTools.iconSeparation + SVTools.getPixels(5) + rLengths[4];
        } else {
            SVTools.landSeparation = SVTools.getPixels(20);

            //Dibujo de opciones (Horizontal):
            canvas.drawText(mainOptions[0], screenWidth / 2, screenHeight - (screenHeight / 4) +
                    SVTools.optionSeparation, SVTools.pRegular[0]);
            canvas.drawText(mainOptions[1], screenWidth / 3 - SVTools.landSeparation,
                    screenHeight - (screenHeight / 4) + SVTools.optionSeparation, SVTools.pRegular[0]);
            canvas.drawText(mainOptions[2], screenWidth - (screenWidth / 3) + SVTools.landSeparation,
                    screenHeight - (screenHeight / 4) + SVTools.optionSeparation, SVTools.pRegular[0]);

            //Dibujo de rectángulos  (Horizontal):
            //Rect rPlay
            rPlay.left = screenWidth / 2 - (rLengths[0] / 2);
            rPlay.right = screenWidth / 2 + (rLengths[0] / 2);
            rPlay.top = (int) (screenHeight - (screenHeight / 4) + SVTools.optionSeparation -
                    SVTools.pRegular[0].getTextSize());
            rPlay.bottom = (int) (screenHeight - (screenHeight / 4) + SVTools.optionSeparation +
                    (SVTools.pRegular[0].getTextSize() / 3));

            //Rect rTutorial
            rTutorial.left = screenWidth / 3 - (rLengths[1] / 2) - SVTools.landSeparation;
            rTutorial.right = screenWidth / 3 + (rLengths[1] / 2) - SVTools.landSeparation;
            rTutorial.top = (int) (screenHeight - (screenHeight / 4) + SVTools.optionSeparation
                    - SVTools.pRegular[0].getTextSize());
            rTutorial.bottom = (int) (screenHeight - (screenHeight / 4) + SVTools.optionSeparation
                    + (SVTools.pRegular[0].getTextSize() / 3));

            //Rect rCredits
            rCredits.left = screenWidth - (screenWidth / 3) - (rLengths[2] / 2) + SVTools.landSeparation;
            rCredits.right = screenWidth - (screenWidth / 3) + (rLengths[2] / 2) + SVTools.landSeparation;
            rCredits.top = (int) (screenHeight - (screenHeight / 4) + SVTools.optionSeparation -
                    SVTools.pRegular[0].getTextSize());
            rCredits.bottom = (int) (screenHeight - (screenHeight / 4) + SVTools.optionSeparation +
                    (SVTools.pRegular[0].getTextSize() / 3));

            //Rect rSettings
            rSettings.left = SVTools.iconSeparation;
            rSettings.right = SVTools.iconSeparation + rLengths[3];
            rSettings.top = SVTools.iconSeparation + SVTools.getPixels(5);
            rSettings.bottom = SVTools.iconSeparation + SVTools.getPixels(5) + rLengths[3];

            //Rect rAchievments
            rAchievments.left = (SVTools.iconSeparation * 2) + rLengths[3];
            rAchievments.right = (SVTools.iconSeparation * 2) + (rLengths[4] * 2);
            rAchievments.top = SVTools.iconSeparation + SVTools.getPixels(5);
            rAchievments.bottom = SVTools.iconSeparation + SVTools.getPixels(5) + rLengths[4];

            //Rect rMarkers
            rMarkers.left = (SVTools.iconSeparation * 3) + (rLengths[3] * 2);
            rMarkers.right = (SVTools.iconSeparation * 3) + (rLengths[3] * 2) + rLengths[5];
            rMarkers.top = SVTools.iconSeparation + SVTools.getPixels(5);
            rMarkers.bottom = SVTools.iconSeparation + SVTools.getPixels(5) + rLengths[4];
        }

        //Dibuja rectángulos de opciones
        canvas.drawRect(rPlay, SVTools.pRects);
        canvas.drawRect(rTutorial, SVTools.pRects);
        canvas.drawRect(rCredits, SVTools.pRects);
        canvas.drawRect(rSettings, SVTools.pRects);
        canvas.drawRect(rAchievments, SVTools.pRects);
        canvas.drawRect(rMarkers, SVTools.pRects);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (rPlay.contains((int) event.getX(), (int) event.getY())) {
                    if (vibration) vibrate(10);
                    return Scene.PLAY;
                }
                if (rTutorial.contains((int) event.getX(), (int) event.getY())) {
                    if (vibration) vibrate(10);
                    return Scene.TUTORIAL;
                }
                if (rAchievments.contains((int) event.getX(), (int) event.getY())) {
                    if (vibration) vibrate(10);
                    return Scene.ACHIEVEMENTS;
                }
                if (rMarkers.contains((int) event.getX(), (int) event.getY())) {
                    if (vibration) vibrate(10);
                    return Scene.MARKERS;
                }
                if (rSettings.contains((int) event.getX(), (int) event.getY())) {
                    if (vibration) vibrate(10);
                    return Scene.SETTINGS;
                }
                if (rCredits.contains((int) event.getX(), (int) event.getY())) {
                    if (vibration) vibrate(10);
                    return Scene.CREDITS;
                }
        }
        return sceneNumber;
    }
}
