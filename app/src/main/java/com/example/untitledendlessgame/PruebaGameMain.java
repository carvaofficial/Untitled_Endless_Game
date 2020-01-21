package com.example.untitledendlessgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.untitledendlessgame.Scenes.CreditsScene;
import com.example.untitledendlessgame.Scenes.MenuScene;
import com.example.untitledendlessgame.Scenes.PlayScene;
import com.example.untitledendlessgame.Scenes.Scene;
import com.example.untitledendlessgame.Scenes.TutorialScene;

import java.util.Arrays;

public class PruebaGameMain extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Context context;
    private int screenWidth = 1, screenHeight = 1;
    private GameThread thread;
    private boolean working;
    Scene actualScene;

    public PruebaGameMain(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;
        working = false;
        thread = new GameThread();
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int newScene = actualScene.onTouchEvent(event);

        if (newScene != actualScene.getSceneNumber()) {
            changeScene(newScene);
        }
        return super.onTouchEvent(event);
    }

    public void changeScene(int newScene) {
        switch (newScene) {
            case Scene.PLAY:
                actualScene = new PlayScene(Scene.PLAY, screenWidth, screenHeight, context);
                break;
            case Scene.TUTORIAL:
                actualScene = new TutorialScene(Scene.TUTORIAL, screenWidth, screenHeight, context);
                break;
            case Scene.CREDITS:
                actualScene = new CreditsScene(Scene.CREDITS, screenWidth, screenHeight, context);
                break;
        }
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
//        thread.setSurfaceSize(width, height);
        actualScene = new MenuScene(Scene.MENU, screenWidth, screenHeight, context);
        if (!working) {
            thread.setWorking(true);
            if (thread.getState() == Thread.State.NEW) {
                thread.start();
            }
            if (thread.getState() == Thread.State.TERMINATED) {
                thread = new GameThread();
                thread.start();
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //Se ejecuta inmediatamente antes de la destrucción de la superficie de dibujo
        thread.setWorking(false);
        try {
            thread.join();
        } catch (InterruptedException ie) {
            Log.i("Thread Exception", Arrays.toString(ie.getStackTrace()));
        }
    }

    class GameThread extends Thread {
        public GameThread() {
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
