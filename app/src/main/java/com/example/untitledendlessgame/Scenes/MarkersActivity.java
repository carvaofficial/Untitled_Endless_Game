package com.example.untitledendlessgame.Scenes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.untitledendlessgame.R;
import com.example.untitledendlessgame.Resources.Tools;
import com.example.untitledendlessgame.Scenes.MarkersTabs.Tab1Fragment;
import com.example.untitledendlessgame.Scenes.MarkersTabs.Tab2Fragment;
import com.example.untitledendlessgame.Scenes.MarkersTabs.TabsAdapter;
import com.google.android.material.tabs.TabLayout;

import static com.example.untitledendlessgame.Resources.Tools.*;

public class MarkersActivity extends AppCompatActivity {
    TabLayout tabDots;
    ViewPager viewPager;

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

    @Override
    protected void onResume() {
        super.onResume();
        Tools.manageDecorationView(this, false);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
    }
}
