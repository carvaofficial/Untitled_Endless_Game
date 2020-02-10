package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.untitledendlessgame.R;
import com.example.untitledendlessgame.Utilities;

import static com.example.untitledendlessgame.MenuSurfaceView.*;
import static com.example.untitledendlessgame.Utilities.*;

public class Scene {
    public static final int MENU = 0, PLAY = 1, TUTORIAL = 2, ACHIEVEMENTS = 3, MARKERS = 4,
            SETTINGS = 5, CREDITS = 6;
    Context context;
    private Rect rBack;
    Utilities util;

    int sceneNumber, screenWidth, screenHeight;
    boolean orientation;    //true -> Vertical, false -> Horizontal

    Scene(int sceneNumber, Context context, int screenWidth, int screenHeight, boolean orientation) {
        this.sceneNumber = sceneNumber;
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.orientation = orientation;
        util = new Utilities(this.context, this.screenWidth, this.screenHeight);

        //Gestión de tamaño de pinceles según orientación
        if (orientation) {
            util.pIcons.setTextSize(screenWidth / 12);
        } else {
            util.pIcons.setTextSize(screenWidth / 24);
        }

        //Inicialización rectángulo botón atrás
        int rBLength;
        util.iconSeparation = util.getPixels(15);

        rBack = new Rect();
        util.pIcons.getTextBounds(context.getString(R.string.icon_back), 0,
                context.getString(R.string.icon_back).length(), rBack);
        rBLength = rBack.right;

        rBack.left = util.iconSeparation;
        rBack.right = util.iconSeparation + rBLength;
        rBack.top = util.iconSeparation + util.getPixels(5);
        rBack.bottom = util.iconSeparation + util.getPixels(5) + rBLength;
    }

    public int getSceneNumber() {
        return sceneNumber;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(context.getResources().getColor(R.color.backgorund));
        if (sceneNumber != Scene.MENU) {
            canvas.drawText(context.getString(R.string.icon_back), util.iconSeparation, util.iconSeparation +
                    util.pIcons.getTextSize(), util.pIcons);
            canvas.drawRect(rBack, util.pRects);
        }
    }

    public int onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (rBack.contains((int) event.getX(), (int) event.getY())) {
                    if (vibration) vibrate(10);
                    return Scene.MENU;
                }
        }
        return sceneNumber;
    }
}
