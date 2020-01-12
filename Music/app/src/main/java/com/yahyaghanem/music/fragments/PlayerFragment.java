package com.yahyaghanem.music.fragments;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yahyaghanem.music.R;
import com.yahyaghanem.music.model.SongsInfo;
import com.yahyaghanem.music.notification.ServiceBroadcast;
import com.yahyaghanem.music.services.MusicService;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment implements ServiceBroadcast.Playable{

    @BindView(R.id.muisc_title)
    TextView title;
    @BindView(R.id.music_artist)
    TextView artist;
    @BindView(R.id.pause_play)
    ImageView pause;
    private boolean playFlag = true;
    private ServiceBroadcast serviceBroadcast;


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
        registerBroadcast();


        SongsInfo songsInfo = getArguments().getParcelable("selected song");
        artist.setText(songsInfo.getArtistName());
        title.setText(songsInfo.getSongName());

        Bundle bundle = new Bundle();
        Intent intent = new Intent(getActivity(), MusicService.class);
        bundle.putParcelable("song", songsInfo);
        intent.putExtras(bundle);
        Objects.requireNonNull(getActivity()).startService(intent);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playFlag) {
                    playFlag = false;
                    Objects.requireNonNull(getContext()).sendBroadcast(new Intent("pause"));

                } else {
                    playFlag = true;
                    Objects.requireNonNull(getContext()).sendBroadcast(new Intent("play"));

                }
            }
        });
    }

    private void registerBroadcast() {
        serviceBroadcast = new ServiceBroadcast();
        serviceBroadcast.setPlayable(this);
        Objects.requireNonNull(getActivity()).registerReceiver(serviceBroadcast, new IntentFilter("pause"));
        getActivity().registerReceiver(serviceBroadcast, new IntentFilter("play"));
        getActivity().registerReceiver(serviceBroadcast, new IntentFilter("close"));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(getContext()).unregisterReceiver(serviceBroadcast);
    }

    @Override
    public void onTrackPlay() {
        pause.setImageResource(R.drawable.ic_pause_circle);
    }

    @Override
    public void onTrackPause() {
        pause.setImageResource(R.drawable.ic_play_circle);
    }

    @Override
    public void onTrackClose() {
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

}
