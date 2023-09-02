package com.eggzdee.presetronome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
	private static boolean playing = false;
	private static ImageButton playButton;
	public static RecyclerView recyclerView;
	public static Context thisContext;
	public static Handler handler;
	public static Runnable myRunnable;
	final String ENTRIES_TAG = "com.eggzdee.presetronome.ENTRIES";

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
				.setUsage(AudioAttributes.USAGE_MEDIA)
				.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
				.build();
		soundPool = new SoundPool.Builder()
				.setMaxStreams(1)
				.setAudioAttributes(audioAttributes)
				.build();
		tickSound = soundPool.load(this, R.raw.tick4, 1);

		//List of entries
		SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = prefs.edit();
		Gson gson = new Gson();
		String jsonGet = prefs.getString(ENTRIES_TAG, "");
		if (jsonGet.equals("")) entries = new ArrayList<>();
		else
		{
			Type type = new TypeToken<List<Integer>>() {}.getType();
			entries = gson.fromJson(jsonGet, type);
		}
		//entries = new ArrayList<>();
		//entries = prefs.getClass();

		//RecyclerView
		recyclerView = findViewById(R.id.recyclerview);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(new MyAdapter(thisContext, entries));

		//Add button
		ImageButton addButton = (ImageButton) findViewById(R.id.addButton);
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

						String jsonPut = gson.toJson(entries);
						prefEditor.putString(ENTRIES_TAG, jsonPut);
						prefEditor.commit();
					}
				}
				catch (Exception e) {}
			}
		});

		//Play button
		playButton = (ImageButton) findViewById(R.id.playButton);

		handler = new Handler();
		myRunnable = new Runnable()
		{
			public void run()
			{
				soundPool.play(tickSound, 1, 1, 0, 0, 1);
				handler.postDelayed(this, 60000/selectedBPM);
			}
		};
	}

	public static void play(View v)
	{
		if (selectedBPM == -1) return;
		if (!playing)
		{
			//soundPool.play(tickSound, 1, 1, 0, -1, 1);
			handler.postDelayed(myRunnable, 5);
			playing = true;
			playButton.setImageResource(R.drawable.pause);
			v.setKeepScreenOn(true);
		}
		else
		{
			handler.removeCallbacks(myRunnable);
			playing = false;
			playButton.setImageResource(R.drawable.play);
			v.setKeepScreenOn(false);
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
		if (playing) play(v);
		selectedBPM = -1;

		SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = prefs.edit();
		Gson gson = new Gson();
		String jsonPut = gson.toJson(entries);
		prefEditor.putString(ENTRIES_TAG, jsonPut);
		prefEditor.commit();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		soundPool.release();
		soundPool = null;
	}

	/*
	@Override
	protected void onStop()
	{
		super.onStop();
		handler.removeCallbacks(myRunnable);
	}
	*/
}