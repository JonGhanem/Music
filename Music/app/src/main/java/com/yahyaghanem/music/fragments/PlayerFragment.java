package com.yahyaghanem.music.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yahyaghanem.music.R;
import com.yahyaghanem.music.model.SongsInfo;
import com.yahyaghanem.music.services.MusicService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {

    private SongsInfo songsInfo = new SongsInfo();
    @BindView(R.id.muisc_title)
    TextView title;
    @BindView(R.id.music_artist)
    TextView artist;
    @BindView(R.id.pause_play)
    ImageView pause;
    private int playFlag = 0;


    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        songsInfo = getArguments().getParcelable("selected song");

        artist.setText(songsInfo.getArtistName());
        title.setText(songsInfo.getSongName());
        Intent intent = new Intent(getActivity(), MusicService.class);
        intent.putExtra("song", songsInfo.getSongUrl());
        Log.d("song", "fragment song: "+songsInfo.getSongUrl());

        getActivity().startService(intent);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playFlag ==0){
                    playFlag = 1;
                getActivity().stopService(new Intent(getActivity(), MusicService.class));
                pause.setImageResource(R.drawable.ic_play_circle);
                }else{
                    playFlag = 0;
                    getActivity().startService(intent);
                    pause.setImageResource(R.drawable.ic_pause_circle);

                }
            }
        });
    }

}
