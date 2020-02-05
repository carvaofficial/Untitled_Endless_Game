package com.example.untitledendlessgame.Games;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.untitledendlessgame.Utilities;

public class Game2Activity extends AppCompatActivity {
    Game2SurfaceView game2;
    View decorationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game2 = new Game2SurfaceView(this);
        decorationView = getWindow().getDecorView();

        //Configuración de decorado de la actividad
        decorationView.setSystemUiVisibility(Utilities.viewOptions);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);    //En onResume no se coloca, salta excepción
        //Desde API 16 oculta la Status Bar (barra de estado/notificaciones)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        game2.setKeepScreenOn(true);

        setContentView(game2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        decorationView = getWindow().getDecorView();
        decorationView.setSystemUiVisibility(Utilities.viewOptions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Pasamos al SurfaceView el estado del la orientación de la pantalla
        game2.setOrientation(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }
}
