package com.eggzdee.presetronome;

import android.media.SoundPool;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BPMEntry
{
    private boolean playing = false;
    private Button playPause;
    private int bpm;
    private int tickSound;
    private SoundPool soundPool;
    private List<BPMEntry> entries;


    public BPMEntry(int bpm, int tickSound, SoundPool soundPool, List<BPMEntry> entries)
    {
        //this.playPause = playPause;
        this.bpm = bpm;
        this.tickSound = tickSound;
        this.soundPool = soundPool;
        this.entries = entries;
    }
    public void play()
    {
        Log.d("tag", bpm + "");
        soundPool.autoPause();
        if (!playing)
        {
            pauseAllButtons();
            soundPool.play(tickSound, 1, 1, 0, -1, 1);
            playing = true;
            playPause.setText("Pause".toCharArray(), 0, 5);
        }
        else
        {
            playing = false;
            String tmp = new String("Play");
            playPause.setText(tmp);
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

    private void pauseAllButtons()
    {
        for (int i = 0; i < entries.size(); i++)
        {
            BPMEntry curr = entries.get(i);
            curr.playing = false;
            String tmp = new String("Play");
            curr.playPause.setText(tmp);
        }
    }
}
