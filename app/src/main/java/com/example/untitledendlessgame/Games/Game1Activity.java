package com.example.untitledendlessgame.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.untitledendlessgame.MainActivity;
import com.example.untitledendlessgame.Resources.AppConstants;
import com.example.untitledendlessgame.Resources.Tools;

import static com.example.untitledendlessgame.Resources.Tools.*;

public class Game1Activity extends AppCompatActivity {
    Game1SurfaceView game1;

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

        if (music && !gameMusic.isPlaying()) gameMusic.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (music && gameMusic.isPlaying()) gameMusic.pause();
//        game1.thread.suspendThread();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (music && !gameMusic.isPlaying()) gameMusic.start();
//        game1.thread.resumeThread();
        Tools.manageDecorationView(this, false);
        //Pasamos al SurfaceView el estado del la orientación de la pantalla
//        game1.setOrientation(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
        startActivity(new Intent(Game1Activity.this, MainActivity.class));
        if (music && gameMusic.isPlaying()) gameMusic.stop();
        Tools.initializeHardware(this, Tools.MENU_MUSIC);
    }
}
