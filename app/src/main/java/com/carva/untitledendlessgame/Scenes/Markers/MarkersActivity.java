package com.carva.untitledendlessgame.Scenes.Markers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.carva.untitledendlessgame.R;
import com.carva.untitledendlessgame.Resources.Tools;
import com.google.android.material.tabs.TabLayout;

import static com.carva.untitledendlessgame.Resources.Tools.*;

/**
 * Visualiza los marcadores del juego.
 *
 * @author carva
 * @version 1.0
 */
public class MarkersActivity extends AppCompatActivity {
    /**
     * Diseño para las pestañas.
     *
     * @see TabLayout
     */
    TabLayout tabDots;
    /**
     * visor de <code>Fragments</code>.
     *
     * @see ViewPager
     */
    ViewPager viewPager;

    /**
     * Se inicializan las propiedades, se crean los <code>Fragments</code> y se crea un adaptador
     * para {@link #viewPager} y se insertan los <code>Fragments</code> dentro del adaptador
     * para su visualización. Finalmente, se asigna el {@link #viewPager} al {@link #tabDots}.
     * Se establece el gestor de pulsación del botón 'Atrás' (<-).
     *
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     * @see Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markers);

        Button btnBack = findViewById(R.id.btnBackMarkers);
        tabDots = findViewById(R.id.tabDots);
        viewPager = findViewById(R.id.viewPager);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vibration) vibrate(10);
                onBackPressed();
            }
        });

        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
        Tab1Fragment game1Tab = new Tab1Fragment(this);
        Tab2Fragment game2Tab = new Tab2Fragment(this);

        adapter.insert(game1Tab);
        adapter.insert(game2Tab);
        viewPager.setAdapter(adapter);
        tabDots.setupWithViewPager(viewPager);
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
        finish();
        overridePendingTransition(0, 0);
    }
}
