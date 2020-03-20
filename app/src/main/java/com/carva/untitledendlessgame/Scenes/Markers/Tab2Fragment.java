package com.carva.untitledendlessgame.Scenes.Markers;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carva.untitledendlessgame.R;
import com.carva.untitledendlessgame.Resources.Tools;

/**
 * <code>Fragment</code> de los marcadores del juego 2.
 *
 * @author carva
 * @version 1.0
 * @see Tab1Fragment
 */
public class Tab2Fragment extends Fragment {
    /**
     * Texto de mejor puntuación.
     */
    private TextView bestScore;
    /**
     * Texto de  mejor tiempo.
     */
    private TextView bestTime;
    /**
     * Texto de máximo número de toques en una partida.
     */
    private TextView maxTouches;
    /**
     * Texto de total de colisiones.
     */
    private TextView totalCollisions;
    /**
     * Texto de puntuación total.
     */
    private TextView totalScore;
    /**
     * Texto de toques totales.
     */
    private TextView totalTouches;

    /**
     * No se usa. Se requiere para la inicialización del <code>Fragment</code>.
     */
    public Tab2Fragment() {
        // Required empty public constructor
    }

    /**
     * Se establecen los valores de los marcadores del juego 1.
     *
     * @param context contecto de la aplicación
     * @see Context
     */
    public Tab2Fragment(Context context) {
        this();
        Tools.establishMarkers(context);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return diseño inflado para el<code>Fragment</code>.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

    /**
     * Inicializa las propiedades con su respectivo componente del diseño. Después asigna cada valor
     * a su respetivo <code>TextView</code>.
     *
     * @param savedInstanceState
     */
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
