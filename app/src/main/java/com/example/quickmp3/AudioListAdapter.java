package com.example.quickmp3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {

    ArrayList<AudioModel> audioModels; // here one model means one song
    Context context;

    public AudioListAdapter(ArrayList<AudioModel> audioModels, Context context) {
        this.audioModels = audioModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new AudioListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        AudioModel audioModel = audioModels.get(position);
        holder.songTitle.setText(audioModel.getTitle());

        if(UIMediaPlayer.currentIndex == position){
            holder.songTitle.setTextColor(Color.parseColor("#ff0000"));
        }else{
            holder.songTitle.setTextColor(Color.parseColor("#000000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIMediaPlayer.getInstance().reset();
                UIMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context, Mp3PlayerActivity.class);
                intent.putExtra("LIST",audioModels);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return audioModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView songIcon;
        TextView songTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            songIcon = itemView.findViewById(R.id.music_icon);
            songTitle = itemView.findViewById(R.id.song_title);
        }
    }
}
