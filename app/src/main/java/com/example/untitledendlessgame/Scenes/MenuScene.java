package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.untitledendlessgame.R;

public class MenuScene extends Scene {
    Context context;
    Rect rPlay, rTutorial, rAchievments, rMarkers, rSettings, rCredits;

    public MenuScene(int sceneNumber, int screenWidth, int screenHeight, Context context, boolean orientation) {
        super(sceneNumber, screenWidth, screenHeight, context, orientation);
        this.context = context;

        //Inicializacion de rectángulos:
        rPlay = new Rect();
        rTutorial = new Rect();
        rAchievments = new Rect();
        rMarkers = new Rect();
        rSettings = new Rect();
        rCredits = new Rect();

        //Propiedades Paints:
        util.pBold[0].setTextAlign(Paint.Align.CENTER);
        util.pRegular[0].setTextAlign(Paint.Align.CENTER);

        //Gestión de tamaño de pinceles según orientación
        if (orientation) {
            util.pBold[0].setTextSize(screenWidth / 6);
            util.pRegular[0].setTextSize(screenWidth / 12);
        } else {
            util.pBold[0].setTextSize(screenWidth / 12);
            util.pRegular[0].setTextSize(screenWidth / 24);
        }
        util.pIcons.setTextSize(util.pRegular[0].getTextSize());
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Dibujo de textos:
        String mainOptions[] = {context.getString(R.string.play), context.getString(R.string.tutorial),
                context.getString(R.string.credits), context.getString(R.string.icon_config),
                context.getString(R.string.icon_achievments), context.getString(R.string.icon_markers)};
        util.optionSeparation = (int) (util.pRegular[0].getTextSize() * 2 - (util.pRegular[0].getTextSize() / 5));

        //Obteniendo ancho de textos e iconos
        util.pRegular[0].getTextBounds(mainOptions[0], 0, mainOptions[0].length(), rPlay);
        util.pRegular[0].getTextBounds(mainOptions[1], 0, mainOptions[1].length(), rTutorial);
        util.pRegular[0].getTextBounds(mainOptions[2], 0, mainOptions[2].length(), rCredits);
        util.pIcons.getTextBounds(mainOptions[3], 0, mainOptions[3].length(), rSettings);
        util.pIcons.getTextBounds(mainOptions[4], 0, mainOptions[4].length(), rAchievments);
        util.pIcons.getTextBounds(mainOptions[5], 0, mainOptions[5].length(), rMarkers);
        int rLengths[] = {rPlay.right, rTutorial.right, rCredits.right, rSettings.right,
                rAchievments.right, rMarkers.right};

        //Dibuja titulo
        canvas.drawText(context.getString(R.string.untitled), screenWidth / 2, screenHeight / 4, util.pBold[0]);
        canvas.drawText(context.getString(R.string.endless), screenWidth / 2, (screenHeight / 4) +
                util.pBold[0].getTextSize(), util.pBold[0]);
        canvas.drawText(context.getString(R.string.game), screenWidth / 2, (screenHeight / 4) +
                (util.pBold[0].getTextSize() * 2), util.pBold[0]);

        //Dibuja iconos
        util.iconSeparation = util.getPixels(15);
        canvas.drawText(mainOptions[3], util.iconSeparation,
                util.iconSeparation + util.pIcons.getTextSize(), util.pIcons);
        canvas.drawText(mainOptions[4], (util.iconSeparation * 2) +
                util.pIcons.getTextSize(), util.iconSeparation + util.pIcons.getTextSize(), util.pIcons);
        canvas.drawText(mainOptions[5], (util.iconSeparation * 3) +
                (util.pIcons.getTextSize() * 2), util.iconSeparation + util.pIcons.getTextSize(), util.pIcons);

        if (orientation) {
            //Dibujo de opciones (Vertical);
            canvas.drawText(mainOptions[0], screenWidth / 2, screenHeight - (screenHeight / 4), util.pRegular[0]);
            canvas.drawText(mainOptions[1], screenWidth / 2, screenHeight - (screenHeight / 4) +
                    util.optionSeparation, util.pRegular[0]);
            canvas.drawText(mainOptions[2], screenWidth / 2, screenHeight - (screenHeight / 4) +
                    (util.optionSeparation * 2), util.pRegular[0]);

            //Dibujo de rectángulos  (Vertical):
            //Rect rPlay
            rPlay.left = screenWidth / 2 - (rLengths[0] / 2);
            rPlay.right = screenWidth / 2 + (rLengths[0] / 2);
            rPlay.top = (int) (screenHeight - (screenHeight / 4) - util.pRegular[0].getTextSize());
            rPlay.bottom = (int) (screenHeight - (screenHeight / 4) + (util.pRegular[0].getTextSize() / 3));

            //Rect rTutorial
            rTutorial.left = screenWidth / 2 - (rLengths[1] / 2);
            rTutorial.right = screenWidth / 2 + (rLengths[1] / 2);
            rTutorial.top = (int) (screenHeight - (screenHeight / 4) + util.optionSeparation -
                    util.pRegular[0].getTextSize());
            rTutorial.bottom = (int) (screenHeight - (screenHeight / 4) + util.optionSeparation
                    + (util.pRegular[0].getTextSize() / 3));

            //Rect rCredits
            rCredits.left = screenWidth / 2 - (rLengths[2] / 2);
            rCredits.right = screenWidth / 2 + (rLengths[2] / 2);
            rCredits.top = (int) (screenHeight - (screenHeight / 4) + (util.optionSeparation * 2)
                    - util.pRegular[0].getTextSize());
            rCredits.bottom = (int) (screenHeight - (screenHeight / 4) + (util.optionSeparation * 2)
                    + (util.pRegular[0].getTextSize() / 3));

            //Rect rSettings
            rSettings.left = util.iconSeparation;
            rSettings.right = util.iconSeparation + rLengths[3];
            rSettings.top = util.iconSeparation + util.getPixels(5);
            rSettings.bottom = util.iconSeparation + util.getPixels(5) + rLengths[3];

            //Rect rAchievments
            rAchievments.left = (util.iconSeparation * 2) + rLengths[3];
            rAchievments.right = (util.iconSeparation * 2) + (rLengths[4] * 2);
            rAchievments.top = util.iconSeparation + util.getPixels(5);
            rAchievments.bottom = util.iconSeparation + util.getPixels(5) + rLengths[4];

            //Rect rMarkers
            rMarkers.left = (util.iconSeparation * 3) + (rLengths[3] * 2);
            rMarkers.right = (util.iconSeparation * 3) + (rLengths[3] * 2) + rLengths[5];
            rMarkers.top = util.iconSeparation + util.getPixels(5);
            rMarkers.bottom = util.iconSeparation + util.getPixels(5) + rLengths[4];
        } else {
            util.landSeparation = util.getPixels(20);

            //Dibujo de opciones (Horizontal):
            canvas.drawText(mainOptions[0], screenWidth / 2, screenHeight - (screenHeight / 4) +
                    util.optionSeparation, util.pRegular[0]);
            canvas.drawText(mainOptions[1], screenWidth / 3 - util.landSeparation,
                    screenHeight - (screenHeight / 4) + util.optionSeparation, util.pRegular[0]);
            canvas.drawText(mainOptions[2], screenWidth - (screenWidth / 3) + util.landSeparation,
                    screenHeight - (screenHeight / 4) + util.optionSeparation, util.pRegular[0]);

            //Dibujo de rectángulos  (Horizontal):
            //Rect rPlay
            rPlay.left = screenWidth / 2 - (rLengths[0] / 2);
            rPlay.right = screenWidth / 2 + (rLengths[0] / 2);
            rPlay.top = (int) (screenHeight - (screenHeight / 4) + util.optionSeparation -
                    util.pRegular[0].getTextSize());
            rPlay.bottom = (int) (screenHeight - (screenHeight / 4) + util.optionSeparation +
                    (util.pRegular[0].getTextSize() / 3));

            //Rect rTutorial
            rTutorial.left = screenWidth / 3 - (rLengths[1] / 2) - util.landSeparation;
            rTutorial.right = screenWidth / 3 + (rLengths[1] / 2) - util.landSeparation;
            rTutorial.top = (int) (screenHeight - (screenHeight / 4) + util.optionSeparation
                    - util.pRegular[0].getTextSize());
            rTutorial.bottom = (int) (screenHeight - (screenHeight / 4) + util.optionSeparation
                    + (util.pRegular[0].getTextSize() / 3));

            //Rect rCredits
            rCredits.left = screenWidth - (screenWidth / 3) - (rLengths[2] / 2) + util.landSeparation;
            rCredits.right = screenWidth - (screenWidth / 3) + (rLengths[2] / 2) + util.landSeparation;
            rCredits.top = (int) (screenHeight - (screenHeight / 4) + util.optionSeparation -
                    util.pRegular[0].getTextSize());
            rCredits.bottom = (int) (screenHeight - (screenHeight / 4) + util.optionSeparation +
                    (util.pRegular[0].getTextSize() / 3));

            //Rect rSettings
            rSettings.left = util.iconSeparation;
            rSettings.right = util.iconSeparation + rLengths[3];
            rSettings.top = util.iconSeparation + util.getPixels(5);
            rSettings.bottom = util.iconSeparation + util.getPixels(5) + rLengths[3];

            //Rect rAchievments
            rAchievments.left = (util.iconSeparation * 2) + rLengths[3];
            rAchievments.right = (util.iconSeparation * 2) + (rLengths[4] * 2);
            rAchievments.top = util.iconSeparation + util.getPixels(5);
            rAchievments.bottom = util.iconSeparation + util.getPixels(5) + rLengths[4];

            //Rect rMarkers
            rMarkers.left = (util.iconSeparation * 3) + (rLengths[3] * 2);
            rMarkers.right = (util.iconSeparation * 3) + (rLengths[3] * 2) + rLengths[5];
            rMarkers.top = util.iconSeparation + util.getPixels(5);
            rMarkers.bottom = util.iconSeparation + util.getPixels(5) + rLengths[4];
        }

        //Dibuja rectángulos de opciones
        canvas.drawRect(rPlay, util.pRects);
        canvas.drawRect(rTutorial, util.pRects);
        canvas.drawRect(rCredits, util.pRects);
        canvas.drawRect(rSettings, util.pRects);
        canvas.drawRect(rAchievments, util.pRects);
        canvas.drawRect(rMarkers, util.pRects);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (rPlay.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.PLAY;
                }
                if (rTutorial.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.TUTORIAL;
                }
                if (rAchievments.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.ACHIEVEMENTS;
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
