package com.yahyaghanem.music.fragments;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.yahyaghanem.music.R;
import com.yahyaghanem.music.activities.MainActivity;
import com.yahyaghanem.music.model.SongsInfo;
import com.yahyaghanem.music.notification.ServiceBroadcast;
import com.yahyaghanem.music.services.MusicService;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yahyaghanem.music.notification.App.CHANNEL_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment implements ServiceBroadcast.Playable{

    private SongsInfo songsInfo = new SongsInfo();
    @BindView(R.id.muisc_title)
    TextView title;
    @BindView(R.id.music_artist)
    TextView artist;
    @BindView(R.id.pause_play)
    ImageView pause;
    private boolean playFlag = true;
    private ServiceBroadcast serviceBroadcast;
    private Intent intent;


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
        serviceBroadcast = new ServiceBroadcast();
        serviceBroadcast.setPlayable(this);
        getActivity().registerReceiver(serviceBroadcast, new IntentFilter("pause"));
        getActivity().registerReceiver(serviceBroadcast, new IntentFilter("play"));

        songsInfo = getArguments().getParcelable("selected song");
        artist.setText(songsInfo.getArtistName());
        title.setText(songsInfo.getSongName());

        Bundle bundle = new Bundle();
        intent = new Intent(getActivity(), MusicService.class);
        bundle.putParcelable("song", songsInfo);
        intent.putExtras(bundle);
        getActivity().startService(intent);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playFlag) {
                    playFlag = false;
                    getContext().sendBroadcast(new Intent("pause"));

                } else {
                    playFlag = true;
                    getContext().sendBroadcast(new Intent("play"));

                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(serviceBroadcast);
    }

    @Override
    public void onTrackPlay() {
        pause.setImageResource(R.drawable.ic_pause_circle);
    }

    @Override
    public void onTrackPause() {
        pause.setImageResource(R.drawable.ic_play_circle);
    }
}
