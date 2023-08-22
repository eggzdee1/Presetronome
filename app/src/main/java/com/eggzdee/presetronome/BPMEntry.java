package com.eggzdee.presetronome;

import android.media.SoundPool;
import android.widget.Button;
import android.widget.TextView;

public class BPMEntry
{
    private boolean playing = false;
    private Button playPause;
    private int bpm;


    public BPMEntry(int bpm)
    {
        //this.playPause = playPause;
        this.bpm = bpm;
    }
    public void play(SoundPool soundPool, int tickSound)
    {
        soundPool.autoPause();
        if (!playing)
        {
            soundPool.play(tickSound, 1, 1, 0, -1, 1);
            playing = true;
            playPause.setText("Pause".toCharArray(), 0, 5);
        }
        else
        {
            playing = false;
            playPause.setText("Play".toCharArray(), 0, 4);
        }
    }

    public int getBpm()
    {
        return bpm;
    }

    public void setPlayPause(Button playPause)
    {
        this.playPause = playPause;
    }
}
