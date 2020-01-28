package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;

import com.example.untitledendlessgame.R;
import com.example.untitledendlessgame.Utilities;

public class Scene {
    public static final int MENU = 0, PLAY = 1, TUTORIAL = 2, ACHIEVEMENTS = 3, MARKERS = 4,
            SETTINGS = 5, CREDITS = 6;
    int sceneNumber, screenWidth, screenHeight;
    boolean orientation;    //true -> Vertical, false -> Horizontal
    Context context;
    AudioManager sounds;
    SoundPool effects;
    Rect rBack;
    Utilities util;

    Scene(int sceneNumber, int screenWidth, int screenHeight, Context context, boolean orientation) {
        this.sceneNumber = sceneNumber;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.context = context;
        this.orientation = orientation;
        this.util = new Utilities(this.context, this.screenWidth, this.screenHeight);

        //Inicialización sonido y efectos
        sounds = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        SoundPool.Builder spb = new SoundPool.Builder();
        spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
        this.effects = spb.build();

        //Gestión de tamaño de pinceles según orientación
        if (orientation) {
            util.pIcons.setTextSize(screenWidth / 12);
        } else {
            util.pIcons.setTextSize(screenWidth / 24);
        }

        //Inicialización rectángulo botón atrás
        int iconSeparation = util.getPixels(15), rBLength;

        rBack = new Rect();
        util.pIcons.getTextBounds(context.getString(R.string.icon_back), 0,
                context.getString(R.string.icon_back).length(), rBack);
        rBLength = rBack.right;

        rBack.left = iconSeparation;
        rBack.right = iconSeparation + rBLength;
        rBack.top = iconSeparation + util.getPixels(5);
        rBack.bottom = iconSeparation + util.getPixels(5) + rBLength;
    }

    public int getSceneNumber() {
        return sceneNumber;
    }

    public void draw(Canvas canvas) {
        util.iconSeparation = util.getPixels(15);
        canvas.drawColor(context.getResources().getColor(R.color.backgorund));
        if (sceneNumber != Scene.MENU) {
            canvas.drawText(context.getString(R.string.icon_back), util.iconSeparation, util.iconSeparation +
                    util.pIcons.getTextSize(), util.pIcons);
            canvas.drawRect(rBack, util.pRects);
        }
    }

    public void updatePhysics() {

    }

    public int onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (rBack.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.MENU;
                }
        }
        return sceneNumber;
    }
}
