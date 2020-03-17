package com.example.untitledendlessgame.Scenes.MarkersTabs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.untitledendlessgame.R;
import com.example.untitledendlessgame.Resources.Tools;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {
    private TextView bestScore, bestTime, maxJumps, totalCollisions, totalScore, totalJumps;

    public Tab1Fragment(Context context) {
        // Required empty public constructor
        Tools.establishMarkers(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bestScore = getView().findViewById(R.id.tvGM1Score);
        bestTime = getView().findViewById(R.id.tvGM1Time);
        maxJumps = getView().findViewById(R.id.tvGM1MaxJumps);
        totalCollisions = getView().findViewById(R.id.tvGM1Collisions);
        totalScore = getView().findViewById(R.id.tvGM1TotalScore);
        totalJumps = getView().findViewById(R.id.tvGM1TotalJumps);

        bestScore.setText(String.valueOf(Tools.maxCrossedBoxes));
        bestTime.setText(String.format("%02d:%02d", Tools.bestTimeInSeconds / 60, Tools.bestTimeInSeconds % 60));
        maxJumps.setText(String.valueOf(Tools.maxJumpsInAMatch));
        totalCollisions.setText(String.valueOf(Tools.totalCrashedBoxes));
        totalScore.setText(String.valueOf(Tools.totalCrossedBoxes));
        totalJumps.setText(String.valueOf(Tools.totalJumps));
    }
}
