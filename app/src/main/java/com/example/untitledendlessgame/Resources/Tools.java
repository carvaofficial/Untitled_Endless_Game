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

    //Hardware tools
    public static Vibrator vibrator;
    public static AudioManager audioManager;
    public static MediaPlayer mediaPlayer;
    public static SoundPool menuEffects;

    //Shared Preferences
    public static SharedPreferences settings;
    public static SharedPreferences markers;
    public static SharedPreferences.Editor editor;
    //Variables for SharedPreferences
    public static boolean music, effects, vibration, gyroscope, theme1, theme2, themeAuto;
    public static int totalJumps, maxJumpsInAMatch, totalCrossedBoxes, maxCrossedBoxes, totalCrashedBoxes, bestTimeInSeconds;
    public static int MENU_MUSIC = R.raw.main_music, GAME_MUSIC = R.raw.game_music; //Music resources

    //General Intent tool
    public static Intent intent;

    //Tags for Once
    public static final String DISPLAY = "Display", TIMER = "Timer";

    public static void initializeMetrics(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager != null ? windowManager.getDefaultDisplay() : null;
        screenSize = new Point();
        display.getSize(Tools.screenSize);
        metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
    }

    public static void getScreenInfo() {
        Log.i(DISPLAY, "Nombre: " + display.getName());
        Log.i(DISPLAY, String.format("Width: %d\nHeight: %d", screenSize.x, screenSize.y));
        Log.i(DISPLAY, String.format("Width (px): %d\nHeight (px): %d", metrics.widthPixels, metrics.heightPixels));
        Log.i(DISPLAY, "Density: " + metrics.densityDpi);
        Log.i(DISPLAY, String.format("DPI X: %f\nDPI Y: %f", metrics.xdpi, metrics.ydpi));
        //Deprecated
        Log.i(DISPLAY, String.format("Screen Width: %d\nScreen Height: %d", display.getWidth(), display.getHeight()));
    }

    public static void defaultSettings(Context context) {
        settings = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = settings.edit();
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

    public static void establishSettings(Context context) {
        settings = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        music = settings.getBoolean("Music", true);
        effects = settings.getBoolean("Effects", true);
        vibration = settings.getBoolean("Vibration", true);
        gyroscope = settings.getBoolean("Gyroscope", false);
        themeAuto = settings.getBoolean("ThemeAuto", false);
        theme1 = settings.getBoolean("Theme1", true);
        theme2 = settings.getBoolean("Theme2", false);
    }

    public static void defaultMarkers(Context context) {
        markers = context.getSharedPreferences("Markers", Context.MODE_PRIVATE);
        editor = markers.edit();
        editor.putInt("totalJumps", 0);
        editor.putInt("maxJumpsInAMatch", 0);
        editor.putInt("totalCrossedBoxes", 0);
        editor.putInt("maxCrossedBoxes", 0);
        editor.putInt("totalCrashedBoxes", 0);
        editor.putInt("bestTimeInSeconds", 0);
        editor.apply();
    }

    public static void establishMarkers(Context context) {
        markers = context.getSharedPreferences("Markers", Context.MODE_PRIVATE);
        totalJumps = markers.getInt("totalJumps", 0);
        maxJumpsInAMatch = markers.getInt("maxJumpsInAMatch", 0);
        totalCrossedBoxes = markers.getInt("totalCrossedBoxes", 0);
        maxCrossedBoxes = markers.getInt("maxCrossedBoxes", 0);
            totalCrashedBoxes = markers.getInt("totalCrashedBoxes", 0);
        bestTimeInSeconds = markers.getInt("bestTimeInSeconds", 0);
    }

    public static void initializeHardware(Context context, int music, boolean loop) {
        //Inicialización música y efectos
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float volume = audioManager != null ? audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) : 0;
        mediaPlayer = MediaPlayer.create(context, music);
        mediaPlayer.setVolume(volume, volume);
        mediaPlayer.setLooping(loop);

        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
        builder.setMaxStreams(10);
        menuEffects = builder.build();

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
