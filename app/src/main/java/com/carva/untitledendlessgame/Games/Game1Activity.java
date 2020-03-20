package com.carva.untitledendlessgame.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Bundle;

import com.carva.untitledendlessgame.Resources.AppConstants;
import com.carva.untitledendlessgame.Resources.SurfaceViewTools;
import com.carva.untitledendlessgame.Resources.Tools;

import static com.carva.untitledendlessgame.Resources.Tools.*;


/**
 * Gestiona el SurfaceView del juego
 *
 * @author carva
 * @version 1.0
 */
public class Game1Activity extends AppCompatActivity {
    Game1SurfaceView game1;

    /**
     * Se eliminan decoraciones (barra de notificaciones, barra de navegación, etc.), se inicializan
     * las propiedades necesarias para la ejecución del juego, y se inicia la música si está activada
     * en los ajustes.
     *
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     * @see Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game1 = new Game1SurfaceView(this);
        AppConstants.initialize(getApplicationContext(), 4, -44, 12,
                Tools.SCREEN_WIDTH, Tools.SCREEN_HEIGHT);

        //Configuración de decorado de la actividad
        Tools.manageDecorationView(this, true);

        game1.setKeepScreenOn(true);
        setContentView(game1);

        if (music && !mediaPlayer.isPlaying()) mediaPlayer.start();
    }

    /**
     * Se produce cuando se sale de la aplicación. Si el juego ya se ha comenzado, se pausa la ejecución
     * de recursos. Si la música está activada en los ajustes y se está reproduciendo, se pausa.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (AppConstants.getGameEngine().isGameRunning() && game1.firstTocuh) {
            AppConstants.getGameEngine().pauseGame();
        }
        if (music && mediaPlayer.isPlaying() && !SurfaceViewTools.intentFlag) mediaPlayer.pause();
    }

    /**
     * Se produce cuando se vuelve a abrir la aplicación. Se eliminan decoraciones (barra de notificaciones,
     * barra de navegación, etc.), se reestablece el volumen del sistema, se resume la ejecución de
     * recursos del juego, y si la música está activada en los ajustesy no está reproduciéndose, se reproduce.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Tools.manageDecorationView(this, false);
        //Controla el volumen si se silencia la aplicación
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if (!AppConstants.getGameEngine().isGameRunning() && game1.firstTocuh) {
            AppConstants.getGameEngine().resumeGame();
        }
        if (music && !mediaPlayer.isPlaying()) mediaPlayer.start();
    }

    /**
     * Se produce cuando el usuaria pulsa el botón 'Atrás' de la barra de navegación o si realiza
     * el respectivo gesto de ir 'Atrás' (varía según fabricante). Al estar vacío, se evita que
     * el jugador le de por error y se salga del juego.
     */
    @Override
    public void onBackPressed() {

    }
}
