package com.yahyaghanem.music.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyaghanem.music.R;
import com.yahyaghanem.music.interfaces.SelectedSongInterface;
import com.yahyaghanem.music.model.SongsInfo;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder>{

    private List<SongsInfo> songs;
    private SelectedSongInterface mselectedSongInterface;

    public MusicAdapter(List<SongsInfo> songList){
        this.songs = songList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_overview, parent, false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SongsInfo info = songs.get(position);

        holder.title.setText(info.getSongName());
        holder.artist.setText(info.getArtistName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("adapter", "onClick: ");
                if (mselectedSongInterface != null) {
                    Log.d("adapter", "onClick1: ");
                    mselectedSongInterface.onItemClickListener(songs.get(position));
                }
            }
        });
    }

    public void setmSelectedItemIterface(SelectedSongInterface selectedSongInterface) {
        mselectedSongInterface = selectedSongInterface;
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView artist, title ;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
             artist = itemView.findViewById(R.id.artist_name);
             title = itemView.findViewById(R.id.title_name);
        }
    }
}
