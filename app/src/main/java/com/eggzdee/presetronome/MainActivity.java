package com.eggzdee.presetronome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private SoundPool soundPool;
    private int tickSound;
    //BPMEntry entry0;
    //private Button playPause;
    List<BPMEntry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //playPause = (Button)findViewById(R.id.entry0);
        //entry0 = new BPMEntry(playPause);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();
        tickSound = soundPool.load(this, R.raw.tick, 1);

        entries = new ArrayList<BPMEntry>();
        entries.add(new BPMEntry(100));
        entries.add(new BPMEntry(100));
        entries.add(new BPMEntry(100));
        entries.add(new BPMEntry(100));
        entries.add(new BPMEntry(100));
        entries.add(new BPMEntry(100));
        entries.add(new BPMEntry(100));
        entries.add(new BPMEntry(100));
        entries.add(new BPMEntry(100));
        entries.add(new BPMEntry(100));

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), entries));
    }

    public void play(View v)
    {
        //entry0.play(soundPool, tickSound);
        Log.d("tag", "In play method");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}