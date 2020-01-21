package com.example.untitledendlessgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {
    View decorView;
    int viewOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PruebaGameMain game = new PruebaGameMain(this);
        decorView = getWindow().getDecorView();

        decorView.setSystemUiVisibility(viewOptions);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        game.setKeepScreenOn(true);
        setContentView(game);
    }
}
