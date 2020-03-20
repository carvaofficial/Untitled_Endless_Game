package com.carva.untitledendlessgame.Resources;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.carva.untitledendlessgame.R;

/**
 * Banco de sonidos utilizados en el juego.
 *
 * @author carva
 * @version 1.0
 */
public class SoundsBank {
    /**
     * Reproductor de efectos de sonido.
     *
     * @see SoundPool
     */
    private SoundPool gameEffects;
    /**
     * Recurso de audio: saltos del personaje.
     */
    private int jumps;
    /**
     * Recurso de audio: pasar por el hueco de las cajas.
     */
    private int boxCrossed;
    /**
     * Recurso de audio: colisión del personaje.
     */
    private int crash;
    /**
     * Recurso de audio: injuria que pronuncia el personaje por haber colisionado.
     */
    private int cyka_blyat;

    /**
     * Inicialización del reproductor de efectos de sonido y se establecen los sonidos a reproducir.
     *
     * @param context Interface to global information about an application environment.
     * @see Context
     */
    public SoundsBank(Context context) {
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
        builder.setMaxStreams(10);
        gameEffects = builder.build();

        jumps = gameEffects.load(context, R.raw.woosh, 1);
        crash = gameEffects.load(context, R.raw.crash, 1);
        cyka_blyat = gameEffects.load(context, R.raw.cyka_blyat, 1);
        boxCrossed = gameEffects.load(context, R.raw.bell, 1);
    }

    /**
     * Reproduce el recurso {@link #jumps}.
     */
    public void playJumps() {
        gameEffects.play(jumps, 1, 1, 1, 0, 1);
    }

    /**
     * Reproduce el recurso {@link #boxCrossed}.
     */
    public void playBoxCrossed() {
        gameEffects.play(boxCrossed, 1, 1, 1, 0, 1);
    }

    /**
     * Reproduce el recurso {@link #crash}.
     */
    public void playCrash() {
        gameEffects.play(crash, 1, 1, 1, 0, 1);
    }

    /**
     * Reproduce el recurso {@link #cyka_blyat}.
     */
    public void playCykaBlyat() {
        gameEffects.play(cyka_blyat, 1, 1, 0, 0, 1);
    }
}
