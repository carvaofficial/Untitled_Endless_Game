package com.carva.untitledendlessgame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.carva.untitledendlessgame.Resources.SurfaceViewTools;
import com.carva.untitledendlessgame.Resources.Tools;
import com.carva.untitledendlessgame.Scenes.Achievments.AchievementsActivity;
import com.carva.untitledendlessgame.Scenes.CreditsScene;
import com.carva.untitledendlessgame.Scenes.Markers.MarkersActivity;
import com.carva.untitledendlessgame.Scenes.MenuScene;
import com.carva.untitledendlessgame.Scenes.GameModeScene;
import com.carva.untitledendlessgame.Scenes.Scene;
import com.carva.untitledendlessgame.Scenes.SettingsActivity;

import java.util.Arrays;

import jonathanfinerty.once.Once;

//FIXME al cambiar la orientación de la pantalla la música se pausa. Problema en el ciclo de vida
// del MainActivity: se ejecutan los métodos onPause, onStop, onDestoy, onCreate y onResume (en dicho orden)
// y al pasar por onPause pausa la música... Revisar con paciencia.

/**
 * Control de escenas del menú principal.
 *
 * @author carva
 * @version 1.0
 */
public class MenuSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * Contexto de la aplicación.
     *
     * @see Context
     */
    Context context;
    /**
     * @see SurfaceHolder
     */
    private SurfaceHolder surfaceHolder;
    /**
     * Hilo del menú principal.
     */
    private MenuThread thread;
    /**
     * Escena actual a mostrar.
     */
    public Scene actualScene;

    /**
     * Tag para {@link Once}.
     */
    final String INITIALIZE_HARDWARE = "IH";

    private boolean running;
    /**
     * Orientación de la pantalla:
     * true -> Vertical
     * false -> Horizontal
     */
    private boolean orientation;
    /**
     * Ancho y altura de la superficie de dibujo.
     */
    private int screenWidth, screenHeight;
    /**
     * Número de escena actual.
     */
    private int SVSceneNumber = Scene.MENU;

    /**
     * Inicialización de propiedades, inicialización del hilo, e inicialización de hardware por cada sesión.
     * Establece los ajustes del sistema y la escena actual.
     *
     * @param context contexto de la aplicación
     * @see Context
     */
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
        Tools.establishSettings(this.context);

        //Inicialización de hardware por cada sesión
        if (!Once.beenDone(Once.THIS_APP_SESSION, INITIALIZE_HARDWARE)) {
            Tools.initializeHardware(this.context, Tools.MENU_MUSIC, true);
            Once.markDone(INITIALIZE_HARDWARE);
        }

        Log.i("Orientation", "public MenuSurfaceView: " + screenWidth + ":" + screenHeight);
        actualScene = new MenuScene(SVSceneNumber, context, screenWidth, screenHeight, orientation);
    }

    /**
     * @param orientation establece la orientación de la pantalla.
     */
    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    /**
     * @return número de escena atual.
     */
    public int getSVSceneNumber() {
        return SVSceneNumber;
    }

    /**
     * @param SVSceneNumber establece un número de escena atual.
     */
    public void setSVSceneNumber(int SVSceneNumber) {
        this.SVSceneNumber = SVSceneNumber;
        changeScene(SVSceneNumber);
    }

    /**
     * Se detecta el tipo de pulsación realizada sobre la pantalla. Cuando se produce una pulsación,
     * se comprueba si el número de escena actual es igual que el número de escena entrante.
     * Si no es igual se cambia de escena.
     *
     * @param event detector de movimiento en pantalla.
     * @return true cada vez que se detecte una puksación.
     * @see MotionEvent
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int newScene = actualScene.onTouchEvent(event);
        if (newScene != actualScene.getSceneNumber()) {
            changeScene(newScene);
        }
        return true;
    }

    /**
     * Establece el cambio de escena y de número de escena.
     *
     * @param newScene número de nueva escena.
     */
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
                SurfaceViewTools.intentFlag = true;
                Tools.createIntent(context, AchievementsActivity.class, false, false);
                break;
            case Scene.MARKERS:
                SurfaceViewTools.intentFlag = true;
                Tools.createIntent(context, MarkersActivity.class, false, false);
                break;
            case Scene.SETTINGS:
                SurfaceViewTools.intentFlag = true;
                Tools.createIntent(context, SettingsActivity.class, false, false);
                break;
            case Scene.CREDITS:
                actualScene = new CreditsScene(Scene.CREDITS, context, screenWidth, screenHeight, orientation);
                break;
        }
        this.SVSceneNumber = actualScene.getSceneNumber();
    }

    /**
     * Se ejecuta inmediatamente después de la creación de la superficie de dibujo.
     *
     * @param holder Abstract interface to someone holding a display surface.
     * @see SurfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    /**
     * Se ejecuta inmediatamente después de que la superficie de dibujo tenga cambios de tamaño o de forma.
     * Se establece el tamaño de la pantalla (<code>Tools.SCREEN_WIDTH</code>, <code>Tools.SCREEN_HEIGHT</code>).
     * Se crea y se lanza el hilo del juego si no está funcionando, sino únicamente se lanza.
     *
     * @param holder Abstract interface to someone holding a display surface.
     * @param format
     * @param width  nuevo ancho de la superficie.
     * @param height nueva altura de la superficie.
     * @see SurfaceHolder
     */
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
            try {
                thread.join();
            } catch (InterruptedException ie) {
                Log.e("Thread Exception: ", Arrays.toString(ie.getStackTrace()));
            }
        }
    }

    /**
     * Hilo del menú principal.
     *
     * @author carva
     * @version 1.0
     */
    class MenuThread extends Thread {

        MenuThread() {
        }

        /**
         * @return si el hilo está funcionando o no.
         */
        boolean isRunning() {
            return running;
        }

        /**
         * @param flag establece el funcionamiento del hilo
         */
        void setRunning(boolean flag) {
            running = flag;
        }

        /**
         * Mientras el hilo esté en funcionamiento, se dibuja en el lienzo de la escena actual,
         * se actualiza la física, y el hilo duerme si le sobra tiempo entre el proceso de todas las acciones,
         */
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
