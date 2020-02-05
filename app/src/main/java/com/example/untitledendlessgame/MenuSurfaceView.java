package com.example.untitledendlessgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.untitledendlessgame.Scenes.CreditsScene;
import com.example.untitledendlessgame.Scenes.MenuScene;
import com.example.untitledendlessgame.Scenes.GameModeScene;
import com.example.untitledendlessgame.Scenes.Scene;
import com.example.untitledendlessgame.Scenes.SettingsActivity;

import static com.example.untitledendlessgame.Utilities.*;

import java.util.Arrays;

public class MenuSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    private SurfaceHolder surfaceHolder;
    private MenuThread thread;
    public Scene actualScene;
    private boolean working;
    private boolean orientation;
    private int screenWidth, screenHeight;
    private int surfaceSceneNumber = Scene.MENU;
    Intent intent;
    public static boolean music, effects, vibration, gyroscope;
    public static AudioManager audioManager;
    public static MediaPlayer gameMusic;
    public static SoundPool gameEffects;
    public static Vibrator vibrator;
    static int volume;

    public MenuSurfaceView(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;

        working = false;
        thread = new MenuThread();
        setFocusable(true);

        //Inicialización música y efectos
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        gameMusic = MediaPlayer.create(context, R.raw.main_music);
        volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        gameMusic.setVolume(volume, volume);

        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
        gameEffects = builder.build();

        //Inicialización vibrador
        vibrator = (Vibrator) context.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        //Inicialización booleanas Settings
        preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        music = preferences.getBoolean("Music", true);
        effects = preferences.getBoolean("Effects", true);
        vibration = preferences.getBoolean("Vibration", true);
        gyroscope = preferences.getBoolean("Gyroscope", false);

        Log.i("Orientation", "public MenuSurfaceView: " + screenWidth + ":" + screenHeight);
        actualScene = new MenuScene(surfaceSceneNumber, screenWidth, screenHeight, context, orientation);

        if (music && !gameMusic.isPlaying()) gameMusic.start();
    }

    public int getSurfaceSceneNumber() {
        return surfaceSceneNumber;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public void setSurfaceSceneNumber(int surfaceSceneNumber) {
        this.surfaceSceneNumber = surfaceSceneNumber;
        changeScene(surfaceSceneNumber);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int newScene = actualScene.onTouchEvent(event);
        if (newScene != actualScene.getSceneNumber()) {
            changeScene(newScene);
        }
        return true;
    }

    public void changeScene(int newScene) {
        Log.i("Orientation", "changeScene into: " + screenWidth + ":" + screenHeight);
        switch (newScene) {
            case Scene.MENU:
                actualScene = new MenuScene(Scene.MENU, screenWidth, screenHeight, context, orientation);
                break;
            case Scene.PLAY:
                actualScene = new GameModeScene(Scene.PLAY, screenWidth, screenHeight, context, orientation);
                break;
            case Scene.TUTORIAL:
                actualScene = new GameModeScene(Scene.TUTORIAL, screenWidth, screenHeight, context, orientation);
                break;
            case Scene.ACHIEVEMENTS:
                actualScene = new CreditsScene(Scene.ACHIEVEMENTS, screenWidth, screenHeight, context, orientation);
                break;
            case Scene.MARKERS:
                actualScene = new CreditsScene(Scene.MARKERS, screenWidth, screenHeight, context, orientation);
                break;
            case Scene.SETTINGS:
                intentFlag = true;
                intent = new Intent(context, SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
                break;
            case Scene.CREDITS:
                actualScene = new CreditsScene(Scene.CREDITS, screenWidth, screenHeight, context, orientation);
                break;
        }
        this.surfaceSceneNumber = actualScene.getSceneNumber();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Se ejecuta inmediatamente después de la creación de la superficie de dibujo
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Se ejecuta inmediatamente después de que la superficie de dibujo tenga cambios
        // o bien de tamaño o bien de forma
        screenWidth = width;
        screenHeight = height;
        Log.i("Orientation", "surfaceChanged: " + screenWidth + ":" + screenHeight);
//        thread.setSurfaceSize(width, height);
        if (!working) {
            thread.setWorking(true);
            if (thread.getState() == Thread.State.NEW) {
                thread.start();
            }
            if (thread.getState() == Thread.State.TERMINATED) {
                thread = new MenuThread();
                thread.start();
            }
        }
        changeScene(actualScene.getSceneNumber());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //Se ejecuta inmediatamente antes de la destrucción de la superficie de dibujo
        thread.setWorking(false);
        try {
            thread.join();
        } catch (InterruptedException ie) {
            Log.e("Thread Exception", Arrays.toString(ie.getStackTrace()));
        }
    }

    class MenuThread extends Thread {
        public MenuThread() {
        }

        void setWorking(boolean flag) {
            working = flag;
        }

        @Override
        public void run() {
            while (working) {
                Canvas canvas = null;
                try {
                    if (!surfaceHolder.getSurface().isValid()) {
                        continue;
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        canvas = surfaceHolder.lockHardwareCanvas();
                    } else {
                        canvas = surfaceHolder.lockCanvas();
                    }
                    synchronized (surfaceHolder) {
                        if (canvas != null) {
                            actualScene.updatePhysics();
                            actualScene.draw(canvas);
                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

//        public void setSurfaceSize(int width, int height) {
//            synchronized (surfaceHolder) {
//                if (background != null) {
//                    background = Bitmap.createScaledBitmap(background, width, height, true);
//                }
//            }
//        }
    }
}
