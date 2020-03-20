package com.carva.untitledendlessgame.Scenes.Achievments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.carva.untitledendlessgame.R;
import com.carva.untitledendlessgame.Resources.Tools;

/**
 * Visualiza y gestiona los logros del juego.
 *
 * @author carva
 * @version 1.0
 */
public class AchievementsActivity extends AppCompatActivity {
    /**
     * Administrador de la vista de logros.
     *
     * @see RecyclerView
     */
    RecyclerView rvAchievments;

    /**
     * Se eliminan decoraciones (barra de notificaciones, barra de navegación, etc.), se inicializa el
     * <code>RecyclerView</code>, su <code>LayoutManager</code> y su adaptador para mostrar los logros
     * de manera vertical. Se establece el gestor de pulsación del botón 'Atrás' (<-).
     *
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     * @see Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        Button btnBack = findViewById(R.id.btnBackAchievments);
        rvAchievments = findViewById(R.id.rvAchievments);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Tools.vibration) Tools.vibrate(10);
                onBackPressed();
            }
        });

        rvAchievments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAchievments.setAdapter(new RecyclerViewAdapter(this));
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
