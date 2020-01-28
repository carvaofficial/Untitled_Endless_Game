package com.example.untitledendlessgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.untitledendlessgame.Scenes.CreditsScene;
import com.example.untitledendlessgame.Scenes.MenuScene;
import com.example.untitledendlessgame.Scenes.GameModeScene;
import com.example.untitledendlessgame.Scenes.Scene;
import com.example.untitledendlessgame.Scenes.SettingsActivity;

import java.util.Arrays;

public class MenuSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    private SurfaceHolder surfaceHolder;
    private MenuThread thread;
    private Scene actualScene;
    private boolean working;
    private boolean orientation;
    private int screenWidth, screenHeight;
    private int surfaceSceneNumber = Scene.MENU;
    Intent intent;

    public MenuSurfaceView(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;

        working = false;
        thread = new MenuThread();
        setFocusable(true);
        Log.i("Orientation", "public MenuSurfaceView: " + screenWidth + ":" + screenHeight);
        actualScene = new MenuScene(surfaceSceneNumber, screenWidth, screenHeight, context, orientation);
        Utilities.getScreenInfo();
    }

    public int getSurfaceSceneNumber() {
        return surfaceSceneNumber;
    }

    public void setSurfaceSceneNumber(int surfaceSceneNumber) {
        this.surfaceSceneNumber = surfaceSceneNumber;
        changeScene(surfaceSceneNumber);
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
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
                actualScene = new GameModeScene(Scene.PLAY, screenWidth, screenHeight, context, orientation, false);
                break;
            case Scene.TUTORIAL:
                actualScene = new GameModeScene(Scene.TUTORIAL, screenWidth, screenHeight, context, orientation, true);
                break;
            case Scene.ACHIEVEMENTS:
                actualScene = new CreditsScene(Scene.ACHIEVEMENTS, screenWidth, screenHeight, context, orientation);
                break;
            case Scene.MARKERS:
                actualScene = new CreditsScene(Scene.MARKERS, screenWidth, screenHeight, context, orientation);
                break;
            case Scene.SETTINGS:
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
                            Log.i("Ori_port", orientation + "");
                            actualScene.updatePhysics();
//                            Log.i("Orientation", "run: " + screenWidth + ":" + screenHeight);
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
