package com.eggzdee.presetronome;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder
{
    TextView bpmView;
    Button playPause;
    BPMEntry entry;

    public MyViewHolder(@NonNull View itemView)
    {
        super(itemView);
        bpmView = itemView.findViewById(R.id.bpmText);
        playPause = itemView.findViewById(R.id.playPause);

        itemView.findViewById(R.id.playPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entry.play();
            }
        });
    }


}
