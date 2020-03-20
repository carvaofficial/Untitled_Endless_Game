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
 * <code>Fragment</code> de los marcadores del juego 1.
 *
 * @author carva
 * @version 1.0
 * @see Fragment
 */
public class Tab1Fragment extends Fragment {
    /**
     * Texto de mejor puntuación.
     */
    private TextView bestScore;
    /**
     * Texto de  mejor tiempo.
     */
    private TextView bestTime;
    /**
     * Texto de máximo número de saltos en una partida.
     */
    private TextView maxJumps;
    /**
     * Texto de total de colisiones.
     */
    private TextView totalCollisions;
    /**
     * Texto de puntuación total.
     */
    private TextView totalScore;
    /**
     * Texto de saltos totales.
     */
    private TextView totalJumps;

    /**
     * No se usa. Se requiere para la inicialización del <code>Fragment</code>.
     */
    public Tab1Fragment() {
        // Required empty public constructor
    }

    /**
     * Se establecen los valores de los marcadores del juego 1.
     *
     * @param context contecto de la aplicación
     * @see Context
     */
    public Tab1Fragment(Context context) {
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
        return inflater.inflate(R.layout.fragment_tab1, container, false);
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
