package com.example.untitledendlessgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.untitledendlessgame.Scenes.Scene;

import jonathanfinerty.once.Once;

import static com.example.untitledendlessgame.Utilities.*;

public class MainActivity extends AppCompatActivity {
    MenuSurfaceView main_menu;
    View decorationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_menu = new MenuSurfaceView(this);
        decorationView = getWindow().getDecorView();

        //Configuración de decorado de la actividad
        decorationView.setSystemUiVisibility(Utilities.viewOptions);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);    //En onResume no se coloca, salta excepción
        //Desde API 16 oculta la Status Bar (barra de estado/notificaciones)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        main_menu.setKeepScreenOn(true);

        setContentView(main_menu);

        //Inicialización y propiedades primeras veces
        Once.initialise(this);
        if (!Once.beenDone(Once.THIS_APP_INSTALL, DEFAULT_SHARED_PREFERENCES)) {
            preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
            editor = preferences.edit();
            editor.putBoolean("Music", true);
            editor.putBoolean("Effects", true);
            editor.putBoolean("Vibration", true);
            editor.putBoolean("Gyroscope", false);
            editor.putBoolean("ThemeAuto", false);
            editor.putInt("Language", 0);
            editor.commit();
            Once.markDone(DEFAULT_SHARED_PREFERENCES);
        }
        if (!Once.beenDone(Once.THIS_APP_INSTALL, INITIAL_TUTORIAL)) {
            AlertDialog.Builder beginTutorial = new AlertDialog.Builder(MainActivity.this);
            beginTutorial.setTitle(getString(R.string.welcome_excl));
            beginTutorial.setMessage(getString(R.string.welcome_txt));
            beginTutorial.setNegativeButton(getString(R.string.welcome_no), null);
            beginTutorial.setPositiveButton(getString(R.string.welcome_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    main_menu.changeScene(Scene.TUTORIAL);
                }
            });
            beginTutorial.show();
            Once.markDone(INITIAL_TUTORIAL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        decorationView = getWindow().getDecorView();
        decorationView.setSystemUiVisibility(Utilities.viewOptions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Pasamos al SurfaceView el estado del la orientación de la pantalla
        main_menu.setOrientation(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    @Override
    public void onBackPressed() {
        if (main_menu.getSurfaceSceneNumber() != Scene.MENU) {
            main_menu.changeScene(Scene.MENU);
        } else {
            AlertDialog.Builder closeGame = new AlertDialog.Builder(MainActivity.this);
            closeGame.setTitle(getString(R.string.exit_game));
            closeGame.setMessage(getString(R.string.exit_game_question));
            closeGame.setNegativeButton(getString(R.string.exit_game_no), null);
            closeGame.setPositiveButton(getString(R.string.exit_game_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.super.onBackPressed();
                }
            });
            closeGame.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Guardamos el numero de escena mediante hashing
//        outState.putInt("sceneNumber", 5);
        outState.putInt("sceneNumber", main_menu.getSurfaceSceneNumber());
//        Log.i("Scene", "onSaveInstanceState: entra " + outState.getInt("sceneNumber"));
        Log.i("Orientation", "onSaveInstanceState: entra " + outState.getInt("sceneNumber"));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Aquí se recupera el numero de escena
        super.onRestoreInstanceState(savedInstanceState);
        main_menu.setSurfaceSceneNumber(savedInstanceState.getInt("sceneNumber"));
//        Log.i("Scene", "onRestoreInstanceState: sale " + savedInstanceState.getInt("sceneNumber"));
        Log.i("Orientation", "onRestoreInstanceState: sale " + savedInstanceState.getInt("sceneNumber"));
    }
}
