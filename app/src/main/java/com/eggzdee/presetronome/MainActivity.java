package com.eggzdee.presetronome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
	private SoundPool soundPool;
	private int tickSound;
	//BPMEntry entry0;
	//private Button playPause;
	//List<BPMEntry> entries;
	public static List<Integer> entries;
	public static int selectedBPM = -1;
	private boolean playing = false;
	private Button playButton;
	public static RecyclerView recyclerView;
	public static Context thisContext;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//Default
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Context
		thisContext = getApplicationContext();

		//SoundPool
		AudioAttributes audioAttributes = new AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
				.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
				.build();
		soundPool = new SoundPool.Builder()
				.setMaxStreams(1)
				.setAudioAttributes(audioAttributes)
				.build();
		tickSound = soundPool.load(this, R.raw.tick, 1);

		//List of entries
		entries = new ArrayList<>();

		//RecyclerView
		recyclerView = findViewById(R.id.recyclerview);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(new MyAdapter(thisContext, entries));

		//Add button
		Button addButton = (Button) findViewById(R.id.addButton);
		EditText enterBPM = (EditText) findViewById(R.id.enterBPM);
		addButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				try
				{
					int bpm = Integer.parseInt(enterBPM.getText().toString());
					if (bpm >= 30 && bpm <= 300 && !entries.contains(bpm))
					{
						//entries.add(0, new BPMEntry(bpm, tickSound, soundPool, entries));
						entries.add(0, bpm);
						recyclerView.setAdapter(new MyAdapter(thisContext, entries));
						enterBPM.setText("");
					}
				}
				catch (Exception e) {}
			}
		});

		//Play button
		playButton = (Button) findViewById(R.id.playButton);
	}

	public void play(View v)
	{
		soundPool.autoPause();
		if (!playing)
		{
			soundPool.play(tickSound, 1, 1, 0, -1, 1);
			playing = true;
			playButton.setText("Pause");
		}
		else
		{
			playing = false;
			playButton.setText("Play");
		}
	}

	public void delete(View v)
	{
		int toDelete = entries.indexOf(selectedBPM);
		if (toDelete != -1)
		{
			entries.remove(toDelete);
			recyclerView.setAdapter(new MyAdapter(getApplicationContext(), entries));
		}
		selectedBPM = -1;
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		soundPool.release();
		soundPool = null;
	}

}