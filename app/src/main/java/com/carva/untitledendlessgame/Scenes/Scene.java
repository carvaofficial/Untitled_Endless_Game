package com.carva.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.carva.untitledendlessgame.R;
import com.carva.untitledendlessgame.Resources.SurfaceViewTools;
import com.carva.untitledendlessgame.Resources.Tools;

/**
 * Creador de escenas para {@link com.carva.untitledendlessgame.MenuSurfaceView}.
 *
 * @author carva
 * @version 1.0
 */
public class Scene {
    /**
     * Tipos de escenas:
     * 0 = MENU (escena {@link MenuScene})
     * 1 = PLAY (escena {@link GameModeScene})
     * 2 = TUTORIAL (escena {@link GameModeScene})
     * 3 = ACHIEVEMENTS (escena {@link com.carva.untitledendlessgame.Scenes.Achievments.AchievementsActivity})
     * 4 = MARKERS (escena {@link com.carva.untitledendlessgame.Scenes.Markers.MarkersActivity})
     * 5 = SETTINGS (escena {@link SettingsActivity})
     * 6 = CREDITS (escena {@link CreditsScene})
     */
    public static final int MENU = 0, PLAY = 1, TUTORIAL = 2, ACHIEVEMENTS = 3, MARKERS = 4,
            SETTINGS = 5, CREDITS = 6;
    /**
     * Contexto de la aplicación.
     *
     * @see Context
     */
    Context context;
    /**
     * Rectángulo del botón 'Atrás'.
     */
    private Rect rBack;
    /**
     * Herramientas para SurfaceView.
     *
     * @see SurfaceViewTools
     */
    SurfaceViewTools SVTools;
    /**
     * Número de escena.
     */
    int sceneNumber;
    /**
     * Ancho y altura de la superficie de dibujo.
     */
    int screenWidth, screenHeight;
    /**
     * Orientación de la pantalla:
     * true -> Vertical
     * false -> Horizontal
     */
    boolean orientation;

    /**
     * Inicializa propiedades y herramientas, y establece tamaños de texto según la orientación.
     *
     * @param sceneNumber  número de escena.
     * @param context      contexto de la aplicación.
     * @param screenWidth  ancho de la superficie del lienzo
     * @param screenHeight altura de la superficie del lienzo
     * @param orientation  orientación de la pantalla.
     */
    Scene(int sceneNumber, Context context, int screenWidth, int screenHeight, boolean orientation) {
        this.sceneNumber = sceneNumber;
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.orientation = orientation;
        SVTools = new SurfaceViewTools(this.context);

        //Gestión de tamaño de pinceles según orientación
        if (orientation) {
            SVTools.pIcons.setTextSize(screenWidth / 12);
        } else {
            SVTools.pIcons.setTextSize(screenWidth / 24);
        }

        //Inicialización rectángulo botón atrás
        int rBLength;
        SVTools.iconSeparation = SVTools.getPixels(15);

        rBack = new Rect();
        SVTools.pIcons.getTextBounds(context.getString(R.string.icon_back), 0,
                context.getString(R.string.icon_back).length(), rBack);
        rBLength = rBack.right;

        rBack.left = SVTools.iconSeparation;
        rBack.right = SVTools.iconSeparation + rBLength;
        rBack.top = SVTools.iconSeparation + SVTools.getPixels(5);
        rBack.bottom = SVTools.iconSeparation + SVTools.getPixels(7) + rBLength;
    }

    /**
     * @return número de escena.
     */
    public int getSceneNumber() {
        return sceneNumber;
    }

    /**
     * Si {@link #sceneNumber} es distinto de <>Scene.MENU</> se dibuja el botón 'Atrás' y su rectágulo.
     *
     * @param canvas lienxo de dibujo.
     */
    public void draw(Canvas canvas) {
        canvas.drawColor(context.getResources().getColor(R.color.backgorund));
        if (sceneNumber != Scene.MENU) {
            canvas.drawText(context.getString(R.string.icon_back), SVTools.iconSeparation, SVTools.iconSeparation +
                    SVTools.pIcons.getTextSize(), SVTools.pIcons);
            canvas.drawRect(rBack, SVTools.pRects);
        }
    }

    /**
     * Se detecta el tipo de pulsación realizada sobre la pantalla. Si se pulsa sobre {@link #rBack},
     * se mostrará la escena <code>Scene.MENU</code>. También se producirá una vibración háptica si
     * la opción está activa en ajustes.
     *
     * @param event detector de movimiento en pantalla.
     * @return true cada vez que se detecte una puksación.
     */
    public int onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (rBack.contains((int) event.getX(), (int) event.getY())) {
                if (Tools.vibration) Tools.vibrate(10);
                return Scene.MENU;
            }
        }
        return sceneNumber;
    }
}
