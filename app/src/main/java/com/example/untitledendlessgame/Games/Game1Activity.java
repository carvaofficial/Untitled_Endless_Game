package com.example.untitledendlessgame.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Bundle;

import com.example.untitledendlessgame.Resources.AppConstants;
import com.example.untitledendlessgame.Resources.SurfaceViewTools;
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

        if (music && !mediaPlayer.isPlaying()) mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (music && mediaPlayer.isPlaying() && !SurfaceViewTools.intentFlag) mediaPlayer.pause();
//        game1.thread.suspendThread();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (music && !mediaPlayer.isPlaying()) mediaPlayer.start();
//        game1.thread.resumeThread();
        Tools.manageDecorationView(this, false);
        //Controla el volumen si se silencia la aplicación
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onBackPressed() {

    }
}
