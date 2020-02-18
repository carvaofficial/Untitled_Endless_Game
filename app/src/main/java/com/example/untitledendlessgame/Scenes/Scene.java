package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.untitledendlessgame.R;
import com.example.untitledendlessgame.Resources.SurfaceViewTools;
import com.example.untitledendlessgame.Resources.Tools;

public class Scene {
    public static final int MENU = 0, PLAY = 1, TUTORIAL = 2, ACHIEVEMENTS = 3, MARKERS = 4,
            SETTINGS = 5, CREDITS = 6;
    Context context;
    private Rect rBack;
    SurfaceViewTools SVTools;

    int sceneNumber, screenWidth, screenHeight;
    boolean orientation;    //true -> Vertical, false -> Horizontal

    Scene(int sceneNumber, Context context, int screenWidth, int screenHeight, boolean orientation) {
        this.sceneNumber = sceneNumber;
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.orientation = orientation;
        SVTools = new SurfaceViewTools(this.context);

        //Gestión de tamaño de pinceles según orientación
        if (orientation) {
            SVTools.pIcons.setTextSize(screenWidth / 12);
        } else {
            SVTools.pIcons.setTextSize(screenWidth / 24);
        }

        //Inicialización rectángulo botón atrás
        int rBLength;
        SVTools.iconSeparation = SVTools.getPixels(15);

        rBack = new Rect();
        SVTools.pIcons.getTextBounds(context.getString(R.string.icon_back), 0,
                context.getString(R.string.icon_back).length(), rBack);
        rBLength = rBack.right;

        rBack.left = SVTools.iconSeparation;
        rBack.right = SVTools.iconSeparation + rBLength;
        rBack.top = SVTools.iconSeparation + SVTools.getPixels(5);
        rBack.bottom = SVTools.iconSeparation + SVTools.getPixels(5) + rBLength;
    }

    public int getSceneNumber() {
        return sceneNumber;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(context.getResources().getColor(R.color.backgorund));
        if (sceneNumber != Scene.MENU) {
            canvas.drawText(context.getString(R.string.icon_back), SVTools.iconSeparation, SVTools.iconSeparation +
                    SVTools.pIcons.getTextSize(), SVTools.pIcons);
            canvas.drawRect(rBack, SVTools.pRects);
        }
    }

    public int onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (rBack.contains((int) event.getX(), (int) event.getY())) {
                    if (Tools.vibration) Tools.vibrate(10);
                    return Scene.MENU;
                }
        }
        return sceneNumber;
    }
}
