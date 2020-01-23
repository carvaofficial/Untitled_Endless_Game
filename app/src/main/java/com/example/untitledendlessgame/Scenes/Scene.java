package com.example.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.example.untitledendlessgame.R;

public class Scene {
    public static final int MENU = 0, PLAY = 1, TUTORIAL = 2, ACHIEVMENTS = 3, MARKERS = 4,
            SETTINGS = 5, CREDITS = 6;
    int sceneNumber;
    int screenWidth, screenHeight;
    //    int iconSeparation = getPixels(15);   //TODO No puedo poner esta variable aqui, explota el canvas, consultar a Javi
    boolean orientation;    //true -> Vertical, false -> Horizontal
    Context context;
    AudioManager sounds;
    SoundPool effects;
    Paint pRects, pBack;
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
//        pRects.setColor(Color.argb(0, 0, 0, 0));
        pRects.setColor(Color.BLACK);
        pRects.setStyle(Paint.Style.STROKE);
        pRects.setStrokeWidth(10);

        pBack = new Paint();
        pBack.setColor(Color.WHITE);
        pBack.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/icons-solid.ttf"));
        pBack.setAntiAlias(true);

        //Gestión de tamaño de pinceles según orientación
        if (orientation) {
            pBack.setTextSize(screenWidth / 12);
        } else {
            pBack.setTextSize(screenWidth / 24);
        }

        //Declaración rectángulo botón atrás
        int iconSeparation = getPixels(15), rBLength;

        rBack = new Rect();
        pBack.getTextBounds(context.getString(R.string.icon_back), 0,
                context.getString(R.string.icon_back).length(), rBack);
        rBLength = rBack.right;

        rBack.left = iconSeparation;
        rBack.right = iconSeparation + rBLength;
        rBack.top = iconSeparation + getPixels(5);
        rBack.bottom = iconSeparation + getPixels(5) + rBLength;
    }

    public int getSceneNumber() {
        return sceneNumber;
    }

    public void draw(Canvas canvas) {
        int iconSeparation = getPixels(15);
        canvas.drawARGB(255, 196, 0, 0);
        if (sceneNumber != Scene.MENU) {
            canvas.drawText(context.getString(R.string.icon_back), iconSeparation, iconSeparation + pBack.getTextSize(), pBack);
            canvas.drawRect(rBack, pRects);
        }
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
