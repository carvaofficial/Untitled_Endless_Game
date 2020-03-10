package com.example.untitledendlessgame.Games;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.untitledendlessgame.Resources.AppConstants;
import com.example.untitledendlessgame.Resources.Tools;

import java.util.Arrays;

import jonathanfinerty.once.Once;

public class Game1SurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder;
    Context context;
    GameThread thread;
    CountDownTimer countDown;
    private boolean runBoxes;

    public Game1SurfaceView(Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        thread = new GameThread(surfaceHolder);

        //CountDownTimer para lanzar las cajas
        runBoxes = false;
        countDown = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                runBoxes = true;
            }
        };

        Tools.initializeHardware(this.context, Tools.GAME_MUSIC);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && AppConstants.getGameEngine().isRunning()) {
            AppConstants.getGameEngine().setGameState(true);
            AppConstants.getGameEngine().character.setVelocity(AppConstants.VELOCITY_WHEN_JUMPED);
            if (!Once.beenDone(Tools.TIMER)) {
                countDown.start();
                Once.markDone(Tools.TIMER);
            }
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Se ejecuta inmediatamente después de la creación de la superficie de dibujo
        if (!thread.isRunning()) {
            thread = new GameThread(holder);
            thread.start();
        } else {
            thread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Se ejecuta inmediatamente después de que la superficie de dibujo tenga cambios
        // o bien de tamaño o bien de forma
        Tools.SCREEN_WIDTH = width;
        Tools.SCREEN_HEIGHT = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //Se ejecuta inmediatamente antes de la destrucción de la superficie de dibujo
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

    //TODO pendiente averiguar cómo pausar el hilo cuando la actividad está en pausa, y evitar un reinicio
    class GameThread extends Thread {
        SurfaceHolder surfaceHolder;
        private boolean running, suspend;
        long startTime, loopTime;
        long DELAY = 30;

        GameThread(SurfaceHolder surfaceHolder) {
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
                    startTime = SystemClock.uptimeMillis();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        canvas = surfaceHolder.lockHardwareCanvas();
                    } else {
                        canvas = surfaceHolder.lockCanvas(null);
                    }
                    if (canvas != null) {
                        synchronized (surfaceHolder) {
                            while (suspend) {
                                surfaceHolder.wait();
                            }
                            AppConstants.getGameEngine().updateAndDrawBackground(canvas);
                            if (runBoxes) {
                                AppConstants.getGameEngine().updateAndDrawBoxes(canvas);
                            }
                            AppConstants.getGameEngine().updateandDrawScore(canvas);
                            AppConstants.getGameEngine().updateAndDrawCharacter(canvas);
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                    loopTime = SystemClock.uptimeMillis() - startTime;
                    if (loopTime < DELAY) {
                        Thread.sleep(DELAY - loopTime);
                    }
                }
            } catch (InterruptedException ie) {
                Log.e("Thread Exception", Arrays.toString(ie.getStackTrace()));
            }
        }

        void suspendThread() {
            synchronized (surfaceHolder) {
                suspend = true;
                surfaceHolder.notifyAll();
            }
        }

        void resumeThread() {
            synchronized (surfaceHolder) {
                suspend = false;
                surfaceHolder.notifyAll();
            }
        }
    }
}
