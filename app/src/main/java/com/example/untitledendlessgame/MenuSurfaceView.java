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

import com.example.untitledendlessgame.Scenes.CreditsActivity;
import com.example.untitledendlessgame.Scenes.CreditsScene;
import com.example.untitledendlessgame.Scenes.MenuScene;
import com.example.untitledendlessgame.Scenes.GameModeScene;
import com.example.untitledendlessgame.Scenes.Scene;
import com.example.untitledendlessgame.Scenes.SettingsActivity;

import static com.example.untitledendlessgame.Utilities.*;

import java.util.Arrays;
//TODO al cambiar la orientación de la pantalla la música vuelve a comenzar de 0... Algún problema
// con surfaceDestroyed o onDestroy el MainActivity. Revisar con paciencia.
public class MenuSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    private SurfaceHolder surfaceHolder;
    private MenuThread thread;
    public Scene actualScene;
    Intent intent;

    private boolean working;
    private int screenWidth, screenHeight;
    private boolean orientation;
    private int surfaceSceneNumber = Scene.MENU;
    public static boolean music, effects, vibration, gyroscope;

    public MenuSurfaceView(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;

        working = false;
        thread = new MenuThread();
        setFocusable(true);

        //Inicialización booleanas Settings
        preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        music = preferences.getBoolean("Music", true);
        effects = preferences.getBoolean("Effects", true);
        vibration = preferences.getBoolean("Vibration", true);
        gyroscope = preferences.getBoolean("Gyroscope", false);

        //Inicialización música y efectos
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        gameMusic = MediaPlayer.create(context, R.raw.main_music);
        gameMusic.setVolume(volume, volume);

        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
        gameEffects = builder.build();

        //Inicialización vibración
        vibrator = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);

        Log.i("Orientation", "public MenuSurfaceView: " + screenWidth + ":" + screenHeight);
        actualScene = new MenuScene(surfaceSceneNumber, context, screenWidth, screenHeight, orientation);
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
                actualScene = new MenuScene(Scene.MENU, context, screenWidth, screenHeight, orientation);
                break;
            case Scene.PLAY:
                actualScene = new GameModeScene(Scene.PLAY, context, screenWidth, screenHeight, orientation);
                break;
            case Scene.TUTORIAL:
                actualScene = new GameModeScene(Scene.TUTORIAL, context, screenWidth, screenHeight, orientation);
                break;
            case Scene.ACHIEVEMENTS:
                actualScene = new CreditsScene(Scene.ACHIEVEMENTS, context, screenWidth, screenHeight, orientation);
                break;
            case Scene.MARKERS:
                actualScene = new CreditsScene(Scene.MARKERS, context, screenWidth, screenHeight, orientation);
                break;
            case Scene.SETTINGS:
                intentFlag = true;
                intent = new Intent(context, SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
                break;
            case Scene.CREDITS:
                intentFlag = true;
                intent = new Intent(context, CreditsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
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
            Log.e("Thread Exception: ", Arrays.toString(ie.getStackTrace()));
        }
    }

    class MenuThread extends Thread {
        MenuThread() {
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
