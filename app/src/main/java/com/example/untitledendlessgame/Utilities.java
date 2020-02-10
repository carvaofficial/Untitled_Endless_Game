package com.example.untitledendlessgame;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class Utilities {
    static final String DISPLAY_TAG = "Display", DEFAULT_SHARED_PREFERENCES = "DSP",
            INITIAL_TUTORIAL = "IT";
    public static int viewOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    private Context context;
    private static Display display;
    private static DisplayMetrics metrics;
    private static Point screenSize;

    public Paint pRects, pIcons;
    public Paint pBold[] = new Paint[5], pRegular[] = new Paint[5];
    public int iconSeparation, optionSeparation, landSeparation;
    private int screenWidth, screenHeight;
    public static boolean intentFlag;

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    public static Vibrator vibrator;
    public static AudioManager audioManager;
    public static MediaPlayer gameMusic;
    public static SoundPool gameEffects;
    static int volume;

    public Utilities(Context context) {
        this.context = context;
        this.iconSeparation = 0;
        this.optionSeparation = 0;
        intentFlag = false;

        //Inicializacion características pantalla:
        display = ((Activity) context).getWindowManager().getDefaultDisplay();
        screenSize = new Point();
        display.getSize(screenSize);
        metrics = new DisplayMetrics();

        //Inicialización pinceles:
        //Paints fuente gruesa
        for (int i = 0; i < pBold.length; i++) {
            pBold[i] = new Paint();
            pBold[i].setColor(Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                pBold[i].setTypeface(context.getResources().getFont(R.font.comfortaa_bold));
            } else {
                pBold[i].setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/comfortaa-bold.ttf"));
            }
            pBold[i].setAntiAlias(true);
        }

        //Paints fuente regular
        for (int i = 0; i < pRegular.length; i++) {
            pRegular[i] = new Paint();
            pRegular[i].setColor(Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                pRegular[i].setTypeface(context.getResources().getFont(R.font.comfortaa_regular));
            } else {
                pRegular[i].setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/comfortaa-regular.ttf"));
            }
            pRegular[i].setAntiAlias(true);
        }

        //Paint iconos
        pIcons = new Paint();
        pIcons.setColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pIcons.setTypeface(context.getResources().getFont(R.font.icons_solid));
        } else {
            pIcons.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/icons-solid.ttf"));
        }
        pIcons.setAntiAlias(true);

        //Paint para rectángulos límite de onTouch
        pRects = new Paint();
        pRects.setColor(Color.argb(0, 0, 0, 0));
//        pRects.setColor(Color.BLACK);
        pRects.setStyle(Paint.Style.STROKE);
        pRects.setStrokeWidth(2);
    }

    public Utilities(Context context, int screenWidth, int screenHeight) {
        this(context);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
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

    public static void vibrate(int miliseconds) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrator.vibrate(VibrationEffect.createOneShot(miliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(miliseconds);
        }
    }
}
