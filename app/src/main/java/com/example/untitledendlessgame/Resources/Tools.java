package com.example.untitledendlessgame.Resources;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
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
import android.view.Window;
import android.view.WindowManager;

import com.example.untitledendlessgame.R;

import jonathanfinerty.once.Once;

public class Tools {
    //Metrics and Display tools
    public static WindowManager windowManager;
    public static Display display;
    public static DisplayMetrics metrics;
    public static Point screenSize;
    public static int SCREEN_WIDTH, SCREEN_HEIGHT;

    //Shared Preferences
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    //Hardware tools
    public static Vibrator vibrator;
    public static AudioManager audioManager;
    public static MediaPlayer gameMusic;
    public static SoundPool gameEffects;
    private static int volume;

    public static boolean music, effects, vibration, gyroscope, theme1, theme2, themeAuto;
    public static int MENU_MUSIC = R.raw.main_music, GAME_MUSIC = R.raw.main_music; //Music resources

    //Tags for Once
    public static final String TIMER = "Timer";

    //General Intent tool
    public static Intent intent;

    public static void initializeMetrics(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        screenSize = new Point();
        display.getSize(Tools.screenSize);
        metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
    }

    public static void getScreenInfo() {
        String DISPLAY_TAG = "Display";
        Log.i(DISPLAY_TAG, "Nombre: " + display.getName());
        Log.i(DISPLAY_TAG, String.format("Width: %d\nHeight: %d", screenSize.x, screenSize.y));
        Log.i(DISPLAY_TAG, String.format("Width (px): %d\nHeight (px): %d", metrics.widthPixels, metrics.heightPixels));
        Log.i(DISPLAY_TAG, "Density: " + metrics.densityDpi);
        Log.i(DISPLAY_TAG, String.format("DPI X: %f\nDPI Y: %f", metrics.xdpi, metrics.ydpi));
        //Deprecated
        Log.i(DISPLAY_TAG, String.format("Screen Width: %d\nScreen Height: %d", display.getWidth(), display.getHeight()));
    }

    public static void defaultPreferences(Context context) {
        preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putBoolean("Music", true);
        editor.putBoolean("Effects", true);
        editor.putBoolean("Vibration", true);
        editor.putBoolean("Gyroscope", false);
        editor.putBoolean("ThemeAuto", false);
        editor.putBoolean("Theme1", true);
        editor.putBoolean("Theme2", false);
        editor.putInt("Language", 0);
        editor.apply();
    }

    public static void establishPreferences(Context context) {
        preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        music = preferences.getBoolean("Music", true);
        effects = preferences.getBoolean("Effects", true);
        vibration = preferences.getBoolean("Vibration", true);
        gyroscope = preferences.getBoolean("Gyroscope", false);
        themeAuto = preferences.getBoolean("ThemeAuto", false);
        theme1 = preferences.getBoolean("Theme1", true);
        theme2 = preferences.getBoolean("Theme2", false);
    }

    public static void initializeHardware(Context context, int music) {
        //Inicialización música y efectos
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        gameMusic = MediaPlayer.create(context, music);
        gameMusic.setVolume(volume, volume);

        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
        gameEffects = builder.build();

        //Inicialización vibración
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static void vibrate(int miliseconds) {
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(VibrationEffect.createOneShot(miliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(miliseconds);
            }
        }
    }

    public static void manageDecorationView(Context context, boolean onCreate) {
        final int viewOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        View decorationView = ((Activity) context).getWindow().getDecorView();
        decorationView.setSystemUiVisibility(viewOptions);
        //Desde API 16 oculta la Status Bar (barra de estado/notificaciones)
        ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (onCreate) {
            //En onResume no se coloca, salta excepción
            ((Activity) context).getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
    }

    public static void createIntent(Context context, Class<?> cl4ss, boolean animations, boolean finishActivity) {
        intent = new Intent(context, cl4ss);
        context.startActivity(intent);

        if (!animations) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            ((Activity) context).overridePendingTransition(0, 0);
        }
        if (finishActivity) {
            ((Activity) context).finish();
        }
    }

    public static void reDoOnce(String tag) {
        Once.clearDone(tag);
        Once.toDo(tag);
    }
}
