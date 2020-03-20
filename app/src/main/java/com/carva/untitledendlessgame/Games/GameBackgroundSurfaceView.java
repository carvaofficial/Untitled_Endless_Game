package com.carva.untitledendlessgame.Games;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.carva.untitledendlessgame.Resources.AppConstants;
import com.carva.untitledendlessgame.Resources.ScrollManager;
import com.carva.untitledendlessgame.Resources.Tools;

import java.util.Arrays;

/**
 * Dibuja las mismas imágenes de fondo que el fondo del juego. Utilizado en actividades.
 *
 * @author carva
 * @version 1.0
 */
public class GameBackgroundSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * @see SurfaceHolder
     */
    SurfaceHolder surfaceHolder;
    /**
     * Gestor del movimiento de la imagen de fondo del juego
     */
    ScrollManager background;
    /**
     * Hilo de movimiento del fondo
     */
    BackgroundThread thread;

    /**
     * Inicia el gestor de movimiento del fondo y el hilo del movimiento del fondo.
     *
     * @param context Interface to global information about an application environment.
     * @see Context
     */
    public GameBackgroundSurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        background = new ScrollManager(3);
        thread = new BackgroundThread(surfaceHolder);
    }

    /**
     * Gestiona el dibujado de la imagen de fondo y establece su posición en el lienzo.
     *
     * @param canvas lienzo de dibujo
     */
    public void updateAndDrawBackground(Canvas canvas) {
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

    /**
     * Se dibuja la imagen de fondo
     *
     * @param canvas lienzo de dibujo
     * @see Canvas
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        updateAndDrawBackground(canvas);
    }

    /**
     * Se ejecuta inmediatamente después de la creación de la superficie de dibujo. Se crea y
     * se lanza el hilo del fondo si no está funcionando, sino únicamente se lanza.
     *
     * @param holder Abstract interface to someone holding a display surface.
     * @see SurfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!thread.isRunning()) {
            thread = new BackgroundThread(holder);
            thread.start();
        } else {
            thread.start();
        }
    }

    /**
     * Se ejecuta inmediatamente después de que la superficie de dibujo tenga cambios de tamaño o de forma.
     *
     * @param holder Abstract interface to someone holding a display surface.
     * @param format
     * @param width  nuevo ancho de la superficie.
     * @param height nueva altura de la superficie.
     * @see SurfaceHolder
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * Se ejecuta inmediatamente antes de la destrucción de la superficie de dibujo. Si el hilo del fondo
     * está en funcionamiento, se trata de finalizar.
     *
     * @param holder Abstract interface to someone holding a display surface.
     * @see SurfaceHolder
     */
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

    /**
     * Gestor del dibujado de lienzo y actualización de fisicas. Funcionamiento exacto a <code>GameThread</code>.
     *
     * @author carva
     * @version 1.0
     * @see com.carva.untitledendlessgame.Games.Game1SurfaceView.GameThread
     */
    class BackgroundThread extends Thread {
        /**
         * @see SurfaceHolder
         */
        final SurfaceHolder surfaceHolder;
        /**
         * Gestor del funcionamiento del hilo.
         */
        private boolean running;
        /**
         * Tiempo que duerme el hilo.
         */
        long sleepTime = 0;
        /**
         * Tiempo de inicio del hilo.
         */
        long startTime;
        /**
         * Fotogramas por segundo a mostrar en la superficie.
         */
        final int FPS = 33;
        /**
         * Número de ticks por segundo.
         */
        final int TPS = 1000000000;
        /**
         * Tiempo de repetición del dibujado de fotogramas y actualización de físicas.
         */
        final int loopTime = TPS / FPS;

        /**
         * Establece el funcionamiento del hilo.
         *
         * @param surfaceHolder Abstract interface to someone holding a display surface.
         * @see SurfaceHolder
         */
        BackgroundThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            setRunning(true);
        }

        /**
         * @return si el hilo está funcionando o no.
         */
        boolean isRunning() {
            return running;
        }

        /**
         * @param running establece el funcionamiento del hilo
         */
        void setRunning(boolean running) {
            this.running = running;
        }

        /**
         * Mientras el hilo esté en funcionamiento, se dibuja en el lienzo, se actualiza la física, y
         * el hilo duerme si le sobra tiempo entre el proceso de todas las acciones,
         */
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
