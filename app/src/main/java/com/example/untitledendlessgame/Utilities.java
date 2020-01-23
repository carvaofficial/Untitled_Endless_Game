package com.example.untitledendlessgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Utilities {
    int screenWidth, screenHeight;
    public int iconSeparation, optionSeparation;
    Context context;
    public Paint pBold, pRegular, pRects, pIcons;

    public Utilities(Context context, int screenWidth, int screenHeight) {
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.iconSeparation = 0;
        this.optionSeparation = 0;

        //Declaración e inicialización pinceles
        //Paint fuente gruesa
        pBold = new Paint();
        pBold.setColor(Color.WHITE);
        pBold.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/comfortaa-bold.ttf"));
        pBold.setTextAlign(Paint.Align.CENTER);
        pBold.setAntiAlias(true);

        //Paint fuente regular
        pRegular = new Paint();
        pRegular.setColor(Color.WHITE);
        pRegular.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/comfortaa-regular.ttf"));
        pRegular.setTextAlign(Paint.Align.CENTER);
        pRegular.setAntiAlias(true);

        //Paint iconos
        pIcons = new Paint();
        pIcons.setColor(Color.WHITE);
        pIcons.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/icons-solid.ttf"));
        pIcons.setAntiAlias(true);

        //Pincel para rectángulos
        pRects = new Paint();
        pRects.setColor(Color.argb(0, 0, 0, 0));
        pRects.setColor(Color.BLACK);
        pRects.setStyle(Paint.Style.STROKE);
        pRects.setStrokeWidth(2);
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
