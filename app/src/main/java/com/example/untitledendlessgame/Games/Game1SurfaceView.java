package com.example.untitledendlessgame.Games;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.untitledendlessgame.Utilities;

import java.util.Arrays;

public class Game1SurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    private SurfaceHolder surfaceHolder;
    private GameThread thread;
    private boolean working;
    private boolean orientation;
    private int screenWidth, screenHeight;

    public Game1SurfaceView(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;

        working = false;
        thread = new GameThread();
        setFocusable(true);
        Utilities.getScreenInfo();
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public void updatePhysics() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {

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
            Log.e("Thread Exception", Arrays.toString(ie.getStackTrace()));
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
                            updatePhysics();
                            draw(canvas);
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
