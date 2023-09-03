package com.eggzdee.presetronome;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder
{
    Button bpmButton;

    public MyViewHolder(@NonNull View itemView)
    {
        super(itemView);
        bpmButton = itemView.findViewById(R.id.bpmButton);

        bpmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.selectedBPM = Integer.parseInt(bpmButton.getText().toString());
                MainActivity.recyclerView.setAdapter(new MyAdapter(bpmButton.getContext(), MainActivity.entries));
                //MainActivity.getInstance().play(view);
                //MainActivity.getInstance().play(view);
                MainActivity.lastClickTime = 0;
            }
        });
    }


}
