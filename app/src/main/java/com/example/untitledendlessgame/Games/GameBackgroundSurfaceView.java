package com.example.untitledendlessgame.Games;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.untitledendlessgame.Resources.AppConstants;
import com.example.untitledendlessgame.Resources.ScrollManager;
import com.example.untitledendlessgame.Resources.Tools;

import java.util.Arrays;

public class GameBackgroundSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder;
    ScrollManager background;
    BackgroundThread thread;

    public GameBackgroundSurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        background = new ScrollManager(3);
        thread = new BackgroundThread(surfaceHolder);

    }

    public void updateAndDrawBackground(Canvas canvas) {
        //Dibujo de background
        background.setX(background.getX() - background.getVelocity());
        if (background.getX() < -AppConstants.getBitmapBank().getBackgroundWidth()) {
            background.setX(0);
        }

        canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), background.getX(), background.getY(), null);
        if (background.getX() < -(AppConstants.getBitmapBank().getBackgroundWidth() - Tools.SCREEN_WIDTH)) {
            canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), background.getX() +
                    AppConstants.getBitmapBank().getBackgroundWidth(), background.getY(), null);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        updateAndDrawBackground(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!thread.isRunning()) {
            thread = new BackgroundThread(holder);
            thread.start();
        } else {
            thread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (thread.isRunning()) {
            thread.setRunning(false);
            boolean retry = true;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException ie) {
                    Log.e("Thread Exception", Arrays.toString(ie.getStackTrace()));
                }
            }
        }
    }

    class BackgroundThread extends Thread {
        final SurfaceHolder surfaceHolder;
        private boolean running;
        long sleepTime = 0, startTime;
        final int FPS = 33, TPS = 1000000000, loopTime = TPS / FPS;

        BackgroundThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            setRunning(true);
        }

        boolean isRunning() {
            return running;
        }

        void setRunning(boolean running) {
            this.running = running;
        }

        public void run() {
            try {
                while (isRunning()) {
                    Canvas canvas;
                    startTime = System.nanoTime();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        canvas = surfaceHolder.lockHardwareCanvas();
                    } else {
                        canvas = surfaceHolder.lockCanvas(null);
                    }
                    if (canvas != null) {
                        synchronized (surfaceHolder) {
                            draw(canvas);
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                    startTime += loopTime;
                    sleepTime = startTime - System.nanoTime();
                    if (sleepTime > 0) {
                        Thread.sleep(sleepTime / 1000000);
                    }
                }
            } catch (InterruptedException ie) {
                Log.e("Thread Exception", Arrays.toString(ie.getStackTrace()));
            }
        }
    }
}
