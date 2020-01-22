package com.example.untitledendlessgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

//TODO Revisar y retocar toda la clase
public class MainActivity extends AppCompatActivity {
    View decorView;
    PruebaGameMain game;
    int viewOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new PruebaGameMain(this);
        decorView = getWindow().getDecorView();

        decorView.setSystemUiVisibility(viewOptions);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);    //En onResume no se coloca, salta excepción
        //Desde API 16 oculta la Status Bar (barra de estado/notificaciones)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        game.setKeepScreenOn(true);
        setContentView(game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(viewOptions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Pasamos al SurfaceView el estado del la orientación de la pantalla
        game.setOrientation(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }
}
