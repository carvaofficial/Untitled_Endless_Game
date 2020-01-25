package com.example.untitledendlessgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Utilities {
    static final String DISPLAY_TAG = "Display";
    int screenWidth, screenHeight;
    public int iconSeparation, optionSeparation, landSeparation;
    Context context;
    public Paint pBold, pRegular, pRegular2, pRects, pIcons;
    //TODO crear Paint dinámicos, para usar diferentes tamaños de pincel con mismo estilo
//    public Paint pBold[] = new Paint[5], pRegular[] = new Paint[5];
    static Display display;
    static DisplayMetrics metrics;
    static Point screenSize;

    public Utilities(Context context) {
        this.context = context;
        this.iconSeparation = 0;
        this.optionSeparation = 0;

        //Inicializacion características pantalla:
        display = ((Activity) context).getWindowManager().getDefaultDisplay();
        screenSize = new Point();
        display.getSize(screenSize);
        metrics = new DisplayMetrics();

        //Inicialización pinceles:
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
        pRegular2 = new Paint();
        pRegular2.setColor(Color.WHITE);
        pRegular2.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/comfortaa-regular.ttf"));
        pRegular2.setTextAlign(Paint.Align.CENTER);
        pRegular2.setAntiAlias(true);

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

    public Utilities(Context context, int screenWidth, int screenHeight) {
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.iconSeparation = 0;
        this.optionSeparation = 0;

        //Inicializacion características pantalla:
        display = ((Activity) context).getWindowManager().getDefaultDisplay();
        screenSize = new Point();
        display.getSize(screenSize);
        metrics = new DisplayMetrics();

        //Inicialización pinceles:
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
        pRegular2 = new Paint();
        pRegular2.setColor(Color.WHITE);
        pRegular2.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/comfortaa-regular.ttf"));
        pRegular2.setTextAlign(Paint.Align.CENTER);
        pRegular2.setAntiAlias(true);

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
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return (int) (dp * metrics.density);
    }

    public static void getScreenInfo() {
        Log.i(DISPLAY_TAG, "Nombre: " + display.getName());
        Log.i(DISPLAY_TAG, String.format("Width: %d\nHeight: %d", screenSize.x, screenSize.y));
        Log.i(DISPLAY_TAG, String.format("Width (px): %d\nHeight (px): %d", metrics.widthPixels, metrics.heightPixels));
        Log.i(DISPLAY_TAG, "Density: " + metrics.densityDpi);
        Log.i(DISPLAY_TAG, String.format("DPI X: %f\nDPI Y: %f", metrics.xdpi, metrics.ydpi));
        //Deprecated
        Log.i(DISPLAY_TAG, String.format("Screen Width: %d\nScreen Height: %d", display.getWidth(), display.getHeight()));
    }

    public Bitmap scaleWidth(int res, int newWidth) {
        Bitmap bmpAux = BitmapFactory.decodeResource(context.getResources(), res);
        if (newWidth == bmpAux.getWidth()) {
            return bmpAux;
        }
        Log.i("Size", "scaleWidth: " + newWidth);
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
