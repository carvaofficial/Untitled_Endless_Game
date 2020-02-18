package com.example.untitledendlessgame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.untitledendlessgame.Resources.Tools;
import com.example.untitledendlessgame.Scenes.CreditsActivity;
import com.example.untitledendlessgame.Scenes.CreditsScene;
import com.example.untitledendlessgame.Scenes.MenuScene;
import com.example.untitledendlessgame.Scenes.GameModeScene;
import com.example.untitledendlessgame.Scenes.Scene;
import com.example.untitledendlessgame.Scenes.SettingsActivity;

import static com.example.untitledendlessgame.Resources.SurfaceViewTools.*;

import java.util.Arrays;

import jonathanfinerty.once.Once;

//TODO al cambiar la orientación de la pantalla la música se pausa. Problema en el ciclo de vida
// del MainActivity. Se ejecutan los métodos onPause, onStop, onDestoy, onCreate y onResume (en dicho orden)
// y al pasar por onPause pausa la música... Revisar con paciencia.
public class MenuSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    private SurfaceHolder surfaceHolder;
    private MenuThread thread;
    public Scene actualScene;

    static final String INITIALIZE_HARDWARE = "IH";
    private boolean working, orientation;
    private int screenWidth, screenHeight;
    private int surfaceSceneNumber = Scene.MENU;

    public MenuSurfaceView(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;

        working = false;
        thread = new MenuThread();
        setFocusable(true);

        //Inicialización booleanas Settings
        Tools.establishPreferences(this.context);

        if (!Once.beenDone(Once.THIS_APP_SESSION, INITIALIZE_HARDWARE)) {
            Tools.initializeHardware(this.context);
            Once.markDone(INITIALIZE_HARDWARE);
        }

        Log.i("Orientation", "public MenuSurfaceView: " + screenWidth + ":" + screenHeight);
        actualScene = new MenuScene(surfaceSceneNumber, context, screenWidth, screenHeight, orientation);
        Tools.getScreenInfo();
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
                Tools.createIntent(context, SettingsActivity.class, false, false);
                break;
            case Scene.CREDITS:
                intentFlag = true;
                Tools.createIntent(context, CreditsActivity.class, false, false);
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
        Tools.SCREEN_WIDTH = width;
        Tools.SCREEN_HEIGHT = height;

        screenWidth = width;
        screenHeight = height;
        Log.i("Orientation", "surfaceChanged: " + screenWidth + ":" + screenHeight);
//        thread.setSurfaceSize(width, height);
        if (!thread.isWorking()) {
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
        if (thread.isWorking()) {
            thread.setWorking(false);
            try {
                thread.join();
            } catch (InterruptedException ie) {
                Log.e("Thread Exception: ", Arrays.toString(ie.getStackTrace()));
            }
        }
    }

    class MenuThread extends Thread {
        MenuThread() {
        }

        boolean isWorking() {
            return working;
        }

        void setWorking(boolean flag) {
            working = flag;
        }

        @Override
        public void run() {
            while (isWorking()) {
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
