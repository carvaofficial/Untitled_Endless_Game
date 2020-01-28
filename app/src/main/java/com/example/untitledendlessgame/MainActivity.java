package com.example.untitledendlessgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.untitledendlessgame.Scenes.Scene;

public class MainActivity extends AppCompatActivity {
    MenuSurfaceView main_menu;
    View decorationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_menu = new MenuSurfaceView(this);
        decorationView = getWindow().getDecorView();
        decorationView.setSystemUiVisibility(Utilities.viewOptions);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);    //En onResume no se coloca, salta excepción
        //Desde API 16 oculta la Status Bar (barra de estado/notificaciones)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        main_menu.setKeepScreenOn(true);
        setContentView(main_menu);
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
            super.onBackPressed();
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
