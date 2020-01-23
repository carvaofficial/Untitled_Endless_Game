package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class PlayScene extends Scene {
    Paint pGames;
    Rect rGameMode1, rGameMode2;

    public PlayScene(int sceneNumber, int screenWidth, int screenHeight, Context context, boolean orientation) {
        super(sceneNumber, screenWidth, screenHeight, context, orientation);
        //Declaración e inicialización pinceles
        pGames = new Paint();
        pGames.setColor(Color.WHITE);

        //Declaración rectángulos
        rGameMode1 = new Rect();
        rGameMode2 = new Rect();

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
