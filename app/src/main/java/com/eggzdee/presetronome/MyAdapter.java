package com.eggzdee.presetronome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>
{
    Context context;
    List<BPMEntry> entries;

    public MyAdapter(Context context, List<BPMEntry> entries)
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
        String tmp = new String();
        tmp += "" + entries.get(position).getBpm();
        //holder.bpmView.setText(tmp);
        entries.get(position).setPlayPause(holder.playPause);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }
}
