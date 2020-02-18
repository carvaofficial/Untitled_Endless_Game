package com.example.untitledendlessgame.Scenes;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Bundle;

import com.example.untitledendlessgame.R;
import com.example.untitledendlessgame.Resources.Tools;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.manageDecorationView(this, false);
        setContentView(R.layout.activity_credits);

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
