package com.yahyaghanem.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyaghanem.music.R;
import com.yahyaghanem.music.model.SongsInfo;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder>{

    private List<SongsInfo> songs;
    private Context context;

    public MusicAdapter(List<SongsInfo> songList, Context context){
        this.songs = songList;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView artist, title ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             artist = itemView.findViewById(R.id.artist_name);
             title = itemView.findViewById(R.id.title_name);
        }
    }
}
