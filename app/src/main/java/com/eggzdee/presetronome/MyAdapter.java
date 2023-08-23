package com.eggzdee.presetronome;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>
{
    Context context;
    //List<BPMEntry> entries;
    List<Integer> entries;

    public MyAdapter(Context context, List<Integer> entries)
    {
        this.context = context;
        this.entries = entries;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.bpm_entry_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bpmButton.setText("" + entries.get(position));
        //entries.get(position).setPlayPause(holder.playPause);
        //holder.entry = entries.get(position);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }


}
