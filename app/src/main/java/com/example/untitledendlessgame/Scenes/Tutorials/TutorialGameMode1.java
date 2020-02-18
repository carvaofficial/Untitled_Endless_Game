package com.example.untitledendlessgame.Scenes.Tutorials;

import androidx.appcompat.app.AppCompatActivity;

import com.example.untitledendlessgame.*;
import com.example.untitledendlessgame.Resources.Tools;

import android.media.AudioManager;
import android.os.Bundle;

public class TutorialGameMode1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_game_mode1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tools.manageDecorationView(this, false);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }
}
