package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

public class Scene {
    public static final int MENU = 0, PLAY = 1, TUTORIAL = 2, ACHIEVMENTS = 3, MARKERS = 4,
            SETTINGS = 5, CREDITS = 6;
    int sceneNumber;
    int screenWidth, screenHeight;
    boolean orientation;    //true -> Vertical, false -> Horizontal
    Context context;
    AudioManager sounds;
    SoundPool effects;
    Paint pRects;
    Rect rBack;

    public Scene(int sceneNumber, int screenWidth, int screenHeight, Context context, boolean orientation) {
        this.sceneNumber = sceneNumber;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.context = context;
        this.orientation = orientation;

        //Inicialización sonido y efectos
        sounds = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        SoundPool.Builder spb = new SoundPool.Builder();
        spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
        this.effects = spb.build();

        //Declaración e inicialización pincel para rectángulos
        pRects = new Paint();
        pRects.setColor(Color.argb(0, 0, 0, 0));
//        pRects.setColor(Color.BLACK);
        pRects.setStyle(Paint.Style.STROKE);
        pRects.setStrokeWidth(10);

        //Declaración rectángulo botón atrás
        rBack = new Rect(0, 0, this.getPixels(50), this.getPixels(50));
    }

    public int getSceneNumber() {
        return sceneNumber;
    }

    public void draw(Canvas canvas) {
        canvas.drawARGB(255, 196, 0, 0);

    }

    public void updatePhysics() {

    }

    public int onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (rBack.contains((int) event.getX(), (int) event.getY())) {
                    return Scene.MENU;
                }
        }
        return sceneNumber;
    }

    public int getPixels(float dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return (int) (dp * metrics.density);
    }

    public Bitmap scaleWidth(int res, int newWidth) {
        Bitmap bmpAux = BitmapFactory.decodeResource(context.getResources(), res);
        if (newWidth == bmpAux.getWidth()) {
            return bmpAux;
        }
        return Bitmap.createScaledBitmap(bmpAux, newWidth, (bmpAux.getHeight() * newWidth) / bmpAux.getWidth(), true);
    }

    public Bitmap scaleHeight(int res, int newHeight) {
        Bitmap bmpAux = BitmapFactory.decodeResource(context.getResources(), res);
        if (newHeight == bmpAux.getHeight()) {
            return bmpAux;
        }
        return Bitmap.createScaledBitmap(bmpAux, (bmpAux.getWidth() * newHeight) / bmpAux.getHeight(), newHeight, true);
    }

    public void Grid(Canvas canvas, int X, int Y) {
        int line = getPixels(5), gap, position;
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(getPixels(40));
        p.setStrokeWidth(line);

        position = 0;
        gap = (int) Math.ceil(screenWidth / X);
        for (int i = 0; i < screenWidth; i++) {
            canvas.drawLine(position, 0, position, screenHeight, p);
            canvas.drawText(i + "", 10, position, p);
            position += gap;
        }

        p.setColor(Color.BLUE);
        position = 0;
        gap = (int) Math.ceil(screenHeight / Y);
        for (int i = 0; i < screenWidth; i++) {
            canvas.drawLine(position, 0, screenHeight, position, p);
            canvas.drawText(i + "", position, getPixels(50), p);
            position += gap;
        }
    }
}
