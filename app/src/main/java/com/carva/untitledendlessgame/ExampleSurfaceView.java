package com.carva.untitledendlessgame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.carva.untitledendlessgame.Resources.Tools;

import java.util.Arrays;

/**
 * Clase de ejemplo de un SuurfaceView. Utilizado como guía.
 *
 * @author carva
 * @version 1.0
 */
@Deprecated
public class ExampleSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    private SurfaceHolder surfaceHolder;
    private SVThread thread;
    private boolean working, orientation;
    private int screenWidth, screenHeight;

    public ExampleSurfaceView(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;

        working = false;
        thread = new SVThread();
        setFocusable(true);
        Tools.getScreenInfo();
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public void updatePhysics() {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
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
                thread = new SVThread();
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

    class SVThread extends Thread {

        public SVThread() {

        }

        boolean isWorking() {
            return working;
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

//        public void setSurfaceSize(int width, int height) {
//            synchronized (surfaceHolder) {
//                if (background != null) {
//                    background = Bitmap.createScaledBitmap(background, width, height, true);
//                }
//            }
//        }
    }
}
