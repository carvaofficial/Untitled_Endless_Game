package com.carva.untitledendlessgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import com.carva.untitledendlessgame.Resources.Tools;
import com.carva.untitledendlessgame.Scenes.Scene;

import java.util.Locale;

import jonathanfinerty.once.Once;

import static com.carva.untitledendlessgame.Resources.SurfaceViewTools.*;
import static com.carva.untitledendlessgame.Resources.Tools.*;

public class MainActivity extends AppCompatActivity {
    /**
     * SurfaveView del menú principal.
     *
     * @see MenuSurfaceView
     */
    MenuSurfaceView main_menu;
    /**
     * Tags para {@link Once}.
     *
     * @see Once
     */
    final String DEFAULT_SHARED_PREFERENCES = "DSP",
            INITIAL_TUTORIAL = "IT";

    /**
     * Se eliminan decoraciones (barra de notificaciones, barra de navegación, etc.), se inicializa
     * la clase {@link Once} (permite realizar acciones una sola vez, o cada vez que se abre la aplicación, etc.).
     * si es la primera vez que se abre la aplicación, se estableces valores por defecto de ajuste y marcadores,
     * y mediante un {@link AlertDialog} se pregunta al usuario si desea ver el tutorial del juego.
     * Se muestra el {@link MenuSurfaceView}, se establecen los ajustes y se reproduce la música si
     * está la opción activada en ajustes.
     *
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     * @see Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se inicializa antes de dibujar el SurfaceView y resto, para evitar NullPointer en otros
        // sitios donde se utiliza esta clase
        Once.initialise(this);
        //Primera vez SharedPreferences
        if (!Once.beenDone(Once.THIS_APP_INSTALL, DEFAULT_SHARED_PREFERENCES)) {
            defaultSettings(this);
            defaultMarkers(this);
            Once.markDone(DEFAULT_SHARED_PREFERENCES);
        }

        //Configuración de decorado de la actividad
        manageDecorationView(this, true);

        //Inicializacion características pantalla
        initializeMetrics(this);

        main_menu = new MenuSurfaceView(this);
        main_menu.setKeepScreenOn(true);
        setContentView(main_menu);

        //Primera vez Ver Tutorial
        if (!Once.beenDone(Once.THIS_APP_INSTALL, INITIAL_TUTORIAL)) {
            AlertDialog.Builder beginTutorial = new AlertDialog.Builder(MainActivity.this);
            beginTutorial.setTitle(getString(R.string.welcome_excl));
            beginTutorial.setMessage(getString(R.string.welcome_txt));
            beginTutorial.setNegativeButton(getString(R.string.welcome_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (vibration) vibrate(10);
                    Once.markDone(INITIAL_TUTORIAL);
                }
            });
            beginTutorial.setPositiveButton(getString(R.string.welcome_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (vibration) vibrate(10);
                    main_menu.changeScene(Scene.TUTORIAL);
                    Once.markDone(INITIAL_TUTORIAL);
                }
            });
            beginTutorial.show();
        }

        establishSettings(this);
        //Reestablecer Once's de los juegos
        reDoOnce(TIMER);

        if (music && !mediaPlayer.isPlaying()) mediaPlayer.start();
    }

    /**
     * Se produce cuando se sale de la aplicación. Si la música está activada en los ajustes y
     * se está reproduciendo, se pausa.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (music && mediaPlayer.isPlaying() && !intentFlag) mediaPlayer.pause();
    }

    /**
     * Se produce cuando se vuelve a abrir la aplicación. Se eliminan decoraciones (barra de notificaciones,
     * barra de navegación, etc.), se reestablece el volumen del sistema,y si la música está activada
     * en los ajustes y no está reproduciéndose, se reproduce.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Tools.manageDecorationView(this, false);
        //Pasamos al SurfaceView el estado del la orientación de la pantalla
        main_menu.setOrientation(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);

        //Controla el volumen si se silencia la aplicación
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if (music && !mediaPlayer.isPlaying()) mediaPlayer.start();
    }

    /**
     * Se produce cuando se reinicia la actividad. Si la música está activada en los ajustes y
     * no está reproduciéndose, se reproduce.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        if (music && !mediaPlayer.isPlaying()) mediaPlayer.start();
    }

    /**
     * Se produce cuando el usuaria pulsa el botón 'Atrás' de la barra de navegación o si realiza
     * el respectivo gesto de ir 'Atrás' (varía según fabricante).
     * Si el número de escena de {@link MenuSurfaceView} es distinto de <code>Scene.MENU</code> se mostrará
     * la escena de meú principal. Por lo contrario, si la escena es el menú principal, se mostrará
     * un {@link AlertDialog} preguntando si desea salir del juego, con dos opciones como respuesta
     * ('Volver' y 'Salir').
     */
    @Override
    public void onBackPressed() {
        if (main_menu.getSVSceneNumber() != Scene.MENU) {
            main_menu.changeScene(Scene.MENU);
        } else {
            AlertDialog.Builder closeGame = new AlertDialog.Builder(MainActivity.this);
            closeGame.setTitle(getString(R.string.exit_game));
            closeGame.setMessage(getString(R.string.exit_game_question));
            closeGame.setNegativeButton(getString(R.string.exit_game_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (vibration) vibrate(10);
                }
            });
            closeGame.setPositiveButton(getString(R.string.exit_game_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (vibration) vibrate(10);
                    MainActivity.super.onBackPressed();
                    mediaPlayer.stop();
                    menuEffects.release();
                    vibrator.cancel();
                }
            });
            closeGame.show();
        }
    }

    /**
     * Al cambiar la orientación de la pantalla, se guarda el número de escena para luego redibujar
     * dicha escena y evitar que dibuje el menú principal.
     *
     * @param outState A mapping from String keys to various Parcelable values.
     * @see Bundle
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Guardamos el número de escena mediante hashing
        outState.putInt("sceneNumber", main_menu.getSVSceneNumber());
        Log.i("Orientation", "onSaveInstanceState: entra " + outState.getInt("sceneNumber"));
        super.onSaveInstanceState(outState);
    }

    /**
     * Al cambiar la orientación de la pantalla, se recupera el número de escena para luego redibujar
     * dicha escena y evitar que dibuje el menú principal.
     *
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     * @see Bundle
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Aquí se recupera el número de escena
        super.onRestoreInstanceState(savedInstanceState);
        main_menu.setSVSceneNumber(savedInstanceState.getInt("sceneNumber"));
        Log.i("Orientation", "onRestoreInstanceState: sale " + savedInstanceState.getInt("sceneNumber"));
    }
}
