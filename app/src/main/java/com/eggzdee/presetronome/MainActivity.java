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

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
	private SoundPool soundPool;
	private int tickSound;
	public static List<Integer> entries;
	public static int selectedBPM = -1;
	private static boolean playing = false;
	private ImageButton playButton;
	public static RecyclerView recyclerView;
	public Context thisContext;
	public static Handler handler;
	final String ENTRIES_TAG = "com.eggzdee.presetronome.ENTRIES";
	public static long lastClickTime = 0;
	private float volume = 0.00001f;

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
						entries.add(0, bpm);
						recyclerView.setAdapter(new MyAdapter(thisContext, entries));
						enterBPM.setText("");

						String jsonPut = gson.toJson(entries);
						prefEditor.putString(ENTRIES_TAG, jsonPut);
						prefEditor.apply();
					}
				}
				catch (Exception e) {}
			}
		});

		//Play button
		playButton = (ImageButton) findViewById(R.id.playButton);

		//Start tick loop
		handler = new Handler();
		handler.post(new Runnable()
		{
			@Override
			public void run()
			{
				if (System.currentTimeMillis() >= lastClickTime + 60000/selectedBPM)
				{
					soundPool.play(tickSound, volume, volume, 0, 0, 1);
					lastClickTime += 60000/selectedBPM;
				}
				handler.post(this);
			}
		});
	}

	public void play(View v)
	{
		if (selectedBPM == -1) return;
		if (!playing)
		{
			lastClickTime = System.currentTimeMillis() - 60000/selectedBPM;
			volume = 1;
			playing = true;
			playButton.setImageResource(R.drawable.pause);
			v.setKeepScreenOn(true);
		}
		else
		{
			volume = 0.00001f;
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
		prefEditor.apply();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		soundPool.release();
		soundPool = null;
	}
}