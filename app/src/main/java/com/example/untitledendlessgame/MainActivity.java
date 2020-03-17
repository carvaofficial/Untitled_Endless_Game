package com.example.untitledendlessgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import com.example.untitledendlessgame.Resources.Tools;
import com.example.untitledendlessgame.Scenes.Scene;

import jonathanfinerty.once.Once;

import static com.example.untitledendlessgame.Resources.SurfaceViewTools.*;
import static com.example.untitledendlessgame.Resources.Tools.*;

public class MainActivity extends AppCompatActivity {
    MenuSurfaceView main_menu;
    final String DEFAULT_SHARED_PREFERENCES = "DSP",
            INITIAL_TUTORIAL = "IT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se inicializa antes de dibujar el SurfaceView y resto, para evitar NullPointer en otros
        // sitios donde se utiliza esta clase
        Once.initialise(this);
        //Primera vez SharedPreferences
        if (!Once.beenDone(Once.THIS_APP_INSTALL, DEFAULT_SHARED_PREFERENCES)) {
            Tools.defaultSettings(this);
            Tools.defaultMarkers(this);
            Once.markDone(DEFAULT_SHARED_PREFERENCES);
        }

        //Configuración de decorado de la actividad
        Tools.manageDecorationView(this, true);

        //Inicializacion características pantalla
        Tools.initializeMetrics(this);

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

        //Reestablecer Once's de los juegos
        reDoOnce(TIMER);

        if (music && !mediaPlayer.isPlaying()) mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (music && mediaPlayer.isPlaying() && !intentFlag) mediaPlayer.pause();
    }

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

    @Override
    protected void onRestart() {
        super.onRestart();
        if (music && !mediaPlayer.isPlaying()) mediaPlayer.start();
    }

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Guardamos el número de escena mediante hashing
        outState.putInt("sceneNumber", main_menu.getSVSceneNumber());
        Log.i("Orientation", "onSaveInstanceState: entra " + outState.getInt("sceneNumber"));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Aquí se recupera el número de escena
        super.onRestoreInstanceState(savedInstanceState);
        main_menu.setSVSceneNumber(savedInstanceState.getInt("sceneNumber"));
        Log.i("Orientation", "onRestoreInstanceState: sale " + savedInstanceState.getInt("sceneNumber"));
    }
}
