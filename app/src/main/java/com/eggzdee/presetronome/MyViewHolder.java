package com.eggzdee.presetronome;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder
{
    TextView bpmView;
    Button playPause;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        bpmView = itemView.findViewById(R.id.bpmText);
        playPause = itemView.findViewById(R.id.playPause);
    }
}
