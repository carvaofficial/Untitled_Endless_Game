package com.carva.untitledendlessgame.Games;

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

import com.carva.untitledendlessgame.Resources.AppConstants;
import com.carva.untitledendlessgame.Resources.SurfaceViewTools;
import com.carva.untitledendlessgame.Resources.Tools;

import java.util.Arrays;

import jonathanfinerty.once.Once;

/**
 * Ejecuta recursos para el juego y gestiona su funcionamiento general.
 *
 * @author carva
 * @version 1.0
 */
public class Game1SurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * @see SurfaceHolder
     */
    SurfaceHolder surfaceHolder;
    /**
     * Contexto de la aplicación.
     *
     * @see Context
     */
    Context context;
    /**
     * Hilo del juego.
     */
    GameThread thread;
    /**
     * Cuentas atrás para iniciar el dibujado de cajas.
     */
    CountDownTimer countDown;
    /**
     * Cuentas atrás para terminar el juego e iniciar la pantalla de fin del juego.
     *
     * @see GameOverActivity
     */
    CountDownTimer finishCountDown;
    /**
     * Activa el dibujado de las cajas y sus colisiones.
     */
    private boolean runBoxes;
    /**
     * Saltos que realiza el personaje en la partida.
     */
    private int jumps;
    /**
     * Para comprobar la primera pulsación por partida.
     */
    public boolean firstTocuh;

    /**
     * Inicialización de propiedades, inicialización del hilo, e inicialización de hardware.
     *
     * @param context contexto de la aplicación
     * @see Context
     */
    public Game1SurfaceView(final Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        thread = new GameThread(surfaceHolder);

        firstTocuh = false;
        runBoxes = false;
        countDown = new CountDownTimer(4000, 1000) {
            /**
             * Repetición de código cada vez que se cumple el tiempo de repetición. Se repite hasta
             * que la cuenta atrás llegue a su fin.
             * @param millisUntilFinished tiempo de repetición.
             */
            @Override
            public void onTick(long millisUntilFinished) {
            }

            /**
             * Se produce cuando el contador termina la cuenta atrás
             */
            @Override
            public void onFinish() {
                runBoxes = true;
            }
        };

        finishCountDown = new CountDownTimer(1000, 1000) {
            /**
             * Repetición de código cada vez que se cumple el tiempo de repetición. Se repite hasta
             * que la cuenta atrás llegue a su fin.
             * @param millisUntilFinished tiempo de repetición.
             */
            @Override
            public void onTick(long millisUntilFinished) {

            }

            /**
             * Se produce cuando el contador termina la cuenta atrás. Se lanza un <code>Intent</code>
             * para mostrar la pantalla de fin del juego, se le pasa mediante <code>putExtra()</code>
             * el número de saltos durante la partida, puntuación y tiempo. Se finaliza la actividad
             * <code>Game1Activity</code>y se eliminan animaciones de transición entre actividades.
             * @see GameOverActivity
             */
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

        jumps = 0;

        Tools.initializeHardware(this.context, Tools.GAME_MUSIC, true);
    }

    /**
     * Se dibujan todos los recursos del juego: imagenes del personaje y su colisión, imágenes de
     * las cajas y sus colisiones, imagenes de fondo, imagen de la carretera y su colisión, texto del
     * marcador de puntuación, y texto del tiempo de juego. Cuando el personaje intercepta con alguna
     * colisión, el juego termina y se reproducen efectos de sonido y vibraciones si ambos están activados
     * en los ajustes.
     *
     * @param canvas lienzo de dibujo.
     * @see Canvas
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        AppConstants.getGameEngine().updateAndDrawBackground(canvas);
        AppConstants.getGameEngine().updateAndDrawHighway(canvas);
        AppConstants.getGameEngine().updateHighwayCollision();
        if (runBoxes) {
            AppConstants.getGameEngine().updateAndDrawBoxes(canvas);
            AppConstants.getGameEngine().updateBoxesCollisions();
        }
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

    /**
     * Se detecta el tipo de pulsación realizada sobre la pantalla. Si se pulsa la pantalla, el personaje
     * realiza un salto (se establece una velocidad de salto). Se reproducen efectos de sonido si están
     * activados en los ajustes. Si es la primera vez que se pulsa, comienza una cuenta atrás para dibujar
     * las cajas y se activa la booleana <code>firstTocuh</code>. Se suma 1 al contador de saltos <code>jumps</code>.
     *
     * @param event detector de movimiento en pantalla.
     * @return true cada vez que se detecte una puksación.
     * @see MotionEvent
     */
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
            firstTocuh = true;
        }
        return true;
    }

    /**
     * Se ejecuta inmediatamente después de la creación de la superficie de dibujo. Se crea y
     * se lanza el hilo del juego si no está funcionando, sino únicamente se lanza.
     *
     * @param holder Abstract interface to someone holding a display surface.
     * @see SurfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!thread.isRunning()) {
            thread = new GameThread(holder);
            thread.start();
        } else {
            thread.start();
        }
    }

    /**
     * Se ejecuta inmediatamente después de que la superficie de dibujo tenga cambios de tamaño o de forma.
     * Se establece el tamaño de la pantalla (<code>Tools.SCREEN_WIDTH</code>, <code>Tools.SCREEN_HEIGHT</code>).
     *
     * @param holder Abstract interface to someone holding a display surface.
     * @param format
     * @param width  nuevo ancho de la superficie.
     * @param height nueva altura de la superficie.
     * @see SurfaceHolder
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Tools.SCREEN_WIDTH = width;
        Tools.SCREEN_HEIGHT = height;
    }

    /**
     * Se ejecuta inmediatamente antes de la destrucción de la superficie de dibujo. Si el hilo del juego
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
     * Hilo del juego
     *
     * @author carva
     * @version 1.0
     */
    class GameThread extends Thread {
        final SurfaceHolder surfaceHolder;
        /**
         * Gestor del funcionamiento del hilo.
         */
        private boolean running;
//        long startTime, loopTime;
//        long DELAY = 30;
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
        GameThread(SurfaceHolder surfaceHolder) {
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

/*        public void run() {
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
                            draw(canvas);
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                    loopTime = SystemClock.uptimeMillis() - startTime;
                    if (loopTime <= DELAY) {
                        Thread.sleep(DELAY - loopTime);
                    }
                }
            } catch (InterruptedException ie) {
                Log.e("Thread Exception", Arrays.toString(ie.getStackTrace()));
            }
        }*/
    }
}
