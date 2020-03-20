package com.carva.untitledendlessgame.Scenes.Tutorials;

import androidx.appcompat.app.AppCompatActivity;

import com.carva.untitledendlessgame.R;
import com.carva.untitledendlessgame.Resources.Tools;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Visualiza el tutorial del juego 2.
 *
 * @author carva
 * @version 1.0
 */
public class TutorialGameMode2 extends AppCompatActivity {

    /**
     * Se establece el gestor de pulsación del botón 'Atrás' (<-).
     *
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     * @see Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_game_mode2);

        Button btnBack = findViewById(R.id.btnBackTutorial);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Tools.vibration) Tools.vibrate(10);
                onBackPressed();
            }
        });
    }

    /**
     * Se produce cuando se sale de la aplicación.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Se produce cuando se vuelve a abrir la aplicación. Se eliminan decoraciones (barra de notificaciones,
     * barra de navegación, etc.), se reestablece el volumen del sistema.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Tools.manageDecorationView(this, false);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    /**
     * Se produce cuando el usuaria pulsa el botón 'Atrás' de la barra de navegación o si realiza
     * el respectivo gesto de ir 'Atrás' (varía según fabricante). Finaliza la actividad y se eliminan
     * las animaciones de transición.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }
}
