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
 * Use the {@link Tab2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2Fragment extends Fragment {
    private TextView bestScore, bestTime, maxTouches, totalCollisions, totalScore, totalTouches;

    public Tab2Fragment(Context context) {
        // Required empty public constructor
        Tools.establishMarkers(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bestScore = getView().findViewById(R.id.tvGM2Score);
        bestTime = getView().findViewById(R.id.tvGM2Time);
        maxTouches = getView().findViewById(R.id.tvGM2MaxTouches);
        totalCollisions = getView().findViewById(R.id.tvGM2Collisions);
        totalScore = getView().findViewById(R.id.tvGM2TotalScore);
        totalTouches = getView().findViewById(R.id.tvGM2TotalTouches);

//        bestScore.setText(String.valueOf(Tools.maxCrossedBoxes));
//        bestTime.setText(String.format("%02d:%02d", Tools.bestTimeInSeconds / 60, Tools.bestTimeInSeconds % 60));
//        maxTouches.setText(String.valueOf(Tools.maxJumpsInAMatch));
//        totalCollisions.setText(String.valueOf(Tools.totalCrashedBoxes));
//        totalScore.setText(String.valueOf(Tools.totalCrossedBoxes));
//        totalTouches.setText(String.valueOf(Tools.totalJumps));
    }
}
