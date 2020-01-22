package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class PlayScene extends Scene {


    public PlayScene(int sceneNumber, int screenWidth, int screenHeight, Context context, boolean orientation) {
        super(sceneNumber, screenWidth, screenHeight, context, orientation);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.GREEN);
        canvas.drawRect(super.rBack, super.pRects);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
