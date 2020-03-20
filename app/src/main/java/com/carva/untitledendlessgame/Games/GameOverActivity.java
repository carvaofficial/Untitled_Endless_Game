package com.carva.untitledendlessgame.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.carva.untitledendlessgame.MainActivity;
import com.carva.untitledendlessgame.R;
import com.carva.untitledendlessgame.Resources.Tools;

import static com.carva.untitledendlessgame.Resources.Tools.*;

/**
 * Pantalla de fin del juego. Muestra la puntuación de la partida jugada y el tiempo de duración.
 *
 * @author carva
 * @version 1.0
 */
public class GameOverActivity extends AppCompatActivity {
    /**
     * Superficie para dibujar el fondo del juego
     */
    GameBackgroundSurfaceView background;
    /**
     * Número de saltos de la partida
     */
    int jumps;
    /**
     * Puntuación de la partida
     */
    int score;
    /**
     * Tiempo de duración de la partida
     */
    int timeInSeconds;

    /**
     * Se eliminan decoraciones (barra de notificaciones, barra de navegación, etc.), se establecen
     * los valores de los TextView y se actualizan los valores de los marcadores del juego. También
     * se establecen los gestores de pulsación de los botones 'Reiniciar' y 'Volver al menú'.
     * Si la vibración está activada en los ajustes, se produce una vibración háptica.
     *
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     * @see Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        background = new GameBackgroundSurfaceView(this);
        manageDecorationView(this, true);
        setContentView(R.layout.activity_game_over);
        TextView tvScore = findViewById(R.id.tvScore);
        TextView tvTime = findViewById(R.id.tvTime);
        Button btnRestart = findViewById(R.id.btnRestart);
        Button btnBackToMenu = findViewById(R.id.btnBackToMenu);

        Tools.establishMarkers(this);
        updateMarkers();

        tvScore.setText(String.valueOf(score));
        tvTime.setText(String.format("%02d:%02d", timeInSeconds / 60, timeInSeconds % 60));
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reestablecer Once's de los juegos
                reDoOnce(TIMER);
                if (vibration) vibrate(10);
                if (music && mediaPlayer.isPlaying()) mediaPlayer.stop();
                createIntent(GameOverActivity.this, Game1Activity.class, false, true);
            }
        });
        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vibration) vibrate(10);
                startActivity(new Intent(GameOverActivity.this, MainActivity.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0, 0);
                if (music && mediaPlayer.isPlaying()) mediaPlayer.stop();
                finish();
                initializeHardware(GameOverActivity.this, MENU_MUSIC, true);
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
        manageDecorationView(this, false);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    /**
     * Se produce cuando el usuaria pulsa el botón 'Atrás' de la barra de navegación o si realiza
     * el respectivo gesto de ir 'Atrás' (varía según fabricante). Al estar vacío, obliga al jugador
     * a tener que pulsar uno de los dos botones.
     */
    @Override
    public void onBackPressed() {

    }

    /**
     * Actualiza los valores de los marcadores del juego. Guarda los valores en <code>SharedPreferences</code>.
     */
    public void updateMarkers() {
        jumps = getIntent().getIntExtra("Jumps", 0);
        score = getIntent().getIntExtra("Score", 0);
        timeInSeconds = getIntent().getIntExtra("Time", 0);

        totalJumps += jumps;
        if (maxJumpsInAMatch < jumps) {
            maxJumpsInAMatch = jumps;
        }
        totalCrossedBoxes += score;
        if (maxCrossedBoxes < score) {
            maxCrossedBoxes = score;
        }
        totalCrashedBoxes++;
        if (bestTimeInSeconds < timeInSeconds) {
            bestTimeInSeconds = timeInSeconds;
        }

        markers = getSharedPreferences("Markers", MODE_PRIVATE);
        editor = markers.edit();
        editor.putInt("totalJumps", totalJumps);
        editor.putInt("maxJumpsInAMatch", maxJumpsInAMatch);
        editor.putInt("totalCrossedBoxes", totalCrossedBoxes);
        editor.putInt("maxCrossedBoxes", maxCrossedBoxes);
        editor.putInt("totalCrashedBoxes", totalCrashedBoxes);
        editor.putInt("bestTimeInSeconds", bestTimeInSeconds);
        editor.apply();
    }
}
