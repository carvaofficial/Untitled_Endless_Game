package com.example.untitledendlessgame.Resources;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.example.untitledendlessgame.R;

public class SoundsBank {
    private SoundPool gameEffects;
    private int jumps, boxCrossed, crash, cyka_blyat;

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

    public void playJumps() {
        gameEffects.play(jumps, 1, 1, 1, 0, 1);
    }

    public void playBoxCrossed() {
        gameEffects.play(boxCrossed, 1, 1, 1, 0, 1);
    }

    public void playCrash() {
        gameEffects.play(crash, 1, 1, 1, 0, 1);
    }

    public void playCykaBlyat() {
        gameEffects.play(cyka_blyat, 1, 1, 0, 0, 1);
    }
}
