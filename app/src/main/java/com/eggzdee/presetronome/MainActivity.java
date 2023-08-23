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
	List<Integer> entries;
	public static int selectedBPM = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
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

		entries = new ArrayList<>();

		RecyclerView recyclerView = findViewById(R.id.recyclerview);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(new MyAdapter(getApplicationContext(), entries));

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
					if (bpm <= 300)
					{
						//entries.add(0, new BPMEntry(bpm, tickSound, soundPool, entries));
						entries.add(bpm);
						recyclerView.setAdapter(new MyAdapter(getApplicationContext(), entries));
						enterBPM.setText("");
					}
				}
				catch (Exception e)
				{

				}
			}
		});
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		soundPool.release();
		soundPool = null;
	}

}