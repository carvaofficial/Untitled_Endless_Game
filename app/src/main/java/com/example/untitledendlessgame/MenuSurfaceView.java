package com.example.untitledendlessgame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.untitledendlessgame.Resources.SurfaceViewTools;
import com.example.untitledendlessgame.Resources.Tools;
import com.example.untitledendlessgame.Scenes.CreditsActivity;
import com.example.untitledendlessgame.Scenes.CreditsScene;
import com.example.untitledendlessgame.Scenes.MenuScene;
import com.example.untitledendlessgame.Scenes.GameModeScene;
import com.example.untitledendlessgame.Scenes.Scene;
import com.example.untitledendlessgame.Scenes.SettingsActivity;

import java.util.Arrays;

import jonathanfinerty.once.Once;

//FIXME al cambiar la orientación de la pantalla la música se pausa. Problema en el ciclo de vida
// del MainActivity: se ejecutan los métodos onPause, onStop, onDestoy, onCreate y onResume (en dicho orden)
// y al pasar por onPause pausa la música... Revisar con paciencia.
public class MenuSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    private SurfaceHolder surfaceHolder;
    private MenuThread thread;
    public Scene actualScene;

    final String INITIALIZE_HARDWARE = "IH";
    private boolean running, orientation;
    private int screenWidth, screenHeight;
    private int SVSceneNumber = Scene.MENU;

    public MenuSurfaceView(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;

        running = false;
        thread = new MenuThread();
        setFocusable(true);
        Tools.getScreenInfo();

        //Inicialización booleanas Settings
        Tools.establishPreferences(this.context);

        //Inicialización de hardware por cada sesión
        if (!Once.beenDone(Once.THIS_APP_SESSION, INITIALIZE_HARDWARE)) {
            Tools.initializeHardware(this.context, Tools.MENU_MUSIC);
            Once.markDone(INITIALIZE_HARDWARE);
        }

        Log.i("Orientation", "public MenuSurfaceView: " + screenWidth + ":" + screenHeight);
        actualScene = new MenuScene(SVSceneNumber, context, screenWidth, screenHeight, orientation);
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public int getSVSceneNumber() {
        return SVSceneNumber;
    }

    public void setSVSceneNumber(int SVSceneNumber) {
        this.SVSceneNumber = SVSceneNumber;
        changeScene(SVSceneNumber);
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
                SurfaceViewTools.intentFlag = true;
                Tools.createIntent(context, SettingsActivity.class, false, false);
                break;
            case Scene.CREDITS:
                SurfaceViewTools.intentFlag = true;
                Tools.createIntent(context, CreditsActivity.class, false, false);
//                actualScene = new CreditsScene(Scene.CREDITS, context, screenWidth, screenHeight, orientation);
                break;
        }
        this.SVSceneNumber = actualScene.getSceneNumber();
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
        if (!thread.isRunning()) {
            thread.setRunning(true);
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
        //Se ejecuta inmediatamente antes de la destrucción de la superficie de dibujo;
        if (thread.isRunning()) {
            thread.setRunning(false);
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

        boolean isRunning() {
            return running;
        }

        void setRunning(boolean flag) {
            running = flag;
        }

        @Override
        public void run() {
            while (isRunning()) {
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
    }
}
