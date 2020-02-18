package com.example.untitledendlessgame.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.untitledendlessgame.MainActivity;
import com.example.untitledendlessgame.Resources.AppConstants;
import com.example.untitledendlessgame.Resources.Tools;

public class Game1Activity extends AppCompatActivity {
    Game1SurfaceView game1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game1 = new Game1SurfaceView(this);

        //Configuración de decorado de la actividad
        Tools.manageDecorationView(this, true);

        game1.setKeepScreenOn(true);
        setContentView(game1);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        Tools.initializeHardware(this);
    }
}
