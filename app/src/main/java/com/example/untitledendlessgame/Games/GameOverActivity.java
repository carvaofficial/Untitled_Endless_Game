package com.example.untitledendlessgame.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.untitledendlessgame.MainActivity;
import com.example.untitledendlessgame.R;

import static com.example.untitledendlessgame.Resources.Tools.*;

public class GameOverActivity extends AppCompatActivity {
    GameBackgroundSurfaceView background;
    int jumps, score, timeInSeconds;

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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        manageDecorationView(this, false);
        //Controla el volumen si se silencia la aplicación
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onBackPressed() {

    }

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
