package com.example.untitledendlessgame.Games;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.untitledendlessgame.Resources.AppConstants;
import com.example.untitledendlessgame.Resources.SurfaceViewTools;
import com.example.untitledendlessgame.Resources.Tools;

import java.util.Arrays;

import jonathanfinerty.once.Once;

public class Game1SurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder;
    Context context;
    GameThread thread;
    CountDownTimer countDown, finishCountDown;
    private boolean runBoxes;
    private int jumps;

    public Game1SurfaceView(final Context context) {
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
        //CountDownTimer para lanzar GameOverActivity
        finishCountDown = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(context, GameOverActivity.class);
                intent.putExtra("Score", AppConstants.getGameEngine().score);
                intent.putExtra("Jumps", jumps);
                intent.putExtra("Time", AppConstants.getGameEngine().minutes * 60 +
                        AppConstants.getGameEngine().seconds);
                context.startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                ((Activity) context).overridePendingTransition(0, 0);
                ((Activity) context).finish();
            }
        };

        //Inicialización variables para marcadores
        jumps = 0;

        Tools.initializeHardware(this.context, Tools.GAME_MUSIC, true);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        AppConstants.getGameEngine().updateAndDrawBackground(canvas);
        if (runBoxes) AppConstants.getGameEngine().updateAndDrawBoxes(canvas);
        if (AppConstants.getGameEngine().isRunning() && runBoxes)
            AppConstants.getGameEngine().updateAndDrawCollisions();
        AppConstants.getGameEngine().updateAndDrawScoreAndTime(canvas);
        AppConstants.getGameEngine().updateAndDrawCharacter(canvas);

        if (AppConstants.getGameEngine().collision) {
            if (Tools.vibration) Tools.vibrate(500);
            if (Tools.effects) {
                AppConstants.getSoundsBank().playCrash();
                AppConstants.getSoundsBank().playCykaBlyat();
            }
            AppConstants.getGameEngine().collision = false;
            SurfaceViewTools.intentFlag = true;
            AppConstants.getGameEngine().timer.cancel();
            finishCountDown.start();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && AppConstants.getGameEngine().isRunning()) {
            AppConstants.getGameEngine().setGameRunning(true);
            AppConstants.getGameEngine().character.setVelocity(AppConstants.VELOCITY_WHEN_JUMPED);
            if (Tools.effects) AppConstants.getSoundsBank().playJumps();
            if (!Once.beenDone(Tools.TIMER)) {
                countDown.start();
                AppConstants.getGameEngine().timer.scheduleAtFixedRate(
                        AppConstants.getGameEngine().time, 0, 1000);
                Once.markDone(Tools.TIMER);
            }
            jumps++;
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
        final SurfaceHolder surfaceHolder;
        private boolean running, suspend;
        //        long startTime, loopTime;
//        long DELAY = 30;
        long sleepTime = 0, startTime;
        final int FPS = 33, TPS = 1000000000, loopTime = TPS / FPS;

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

//        public void run() {
//            try {
//                while (isRunning()) {
//                    Canvas canvas;
//                    startTime = SystemClock.uptimeMillis();
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        canvas = surfaceHolder.lockHardwareCanvas();
//                    } else {
//                        canvas = surfaceHolder.lockCanvas(null);
//                    }
//                    if (canvas != null) {
//                        synchronized (surfaceHolder) {
//                            while (suspend) {
//                                surfaceHolder.wait();
//                            }
//                            draw(canvas);
//                            surfaceHolder.unlockCanvasAndPost(canvas);
//                        }
//                    }
//                    loopTime = SystemClock.uptimeMillis() - startTime;
//                    if (loopTime <= DELAY) {
//                        Thread.sleep(DELAY - loopTime);
//                    }
//                }
//            } catch (InterruptedException ie) {
//                Log.e("Thread Exception", Arrays.toString(ie.getStackTrace()));
//            }
//        }

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
                            while (suspend) {
                                surfaceHolder.wait();
                            }
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
