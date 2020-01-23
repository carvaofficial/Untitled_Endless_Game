package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class PlayScene extends Scene {


    public PlayScene(int sceneNumber, int screenWidth, int screenHeight, Context context, boolean orientation) {
        super(sceneNumber, screenWidth, screenHeight, context, orientation);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
