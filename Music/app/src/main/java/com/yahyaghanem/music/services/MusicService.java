package com.yahyaghanem.music.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.yahyaghanem.music.R;
import com.yahyaghanem.music.activities.MainActivity;
import com.yahyaghanem.music.model.SongsInfo;
import com.yahyaghanem.music.notification.ServiceBroadcast;

import java.io.IOException;

import static com.yahyaghanem.music.notification.App.CHANNEL_ID;

//implement Playable in AllMusic Fragment +++++++++++++++++ and try again
// try in the main after selcetion of the song /// start with this at home

public class MusicService extends Service implements ServiceBroadcast.Playable {

    private MediaPlayer player ;
    private SongsInfo songsInfo;
    private ServiceBroadcast serviceBroadcast;
    private Notification playNotification,pauseNotification;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceBroadcast = new ServiceBroadcast();
        serviceBroadcast.setPlayable(this);
        registerReceiver(serviceBroadcast,new IntentFilter("pause"));
        registerReceiver(serviceBroadcast,new IntentFilter("play"));
        registerReceiver(serviceBroadcast,new IntentFilter("close"));

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle extras = intent.getExtras();
        songsInfo = extras.getParcelable("song");
        String song = intent.getStringExtra("song");


        Log.d("songInfo", "onStartCommand: " + songsInfo.getArtistName());

        createNotification();


        if(player!= null){
         if(player.isPlaying()) {
             player.stop();
         }

            player.release();
            player = new MediaPlayer();
        }
        else {
            player = new MediaPlayer();
        }

            try {

                player.setDataSource(songsInfo.getSongUrl());
                player.prepareAsync();
            } catch (IllegalArgumentException | SecurityException | IllegalStateException e) {
                Toast.makeText(getApplication(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //staring the player
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer player) {
                player.start();
            }
        });
        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY;
    }

    private void createNotification() {
        Bitmap icon = BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.ic_music);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,0);

        Intent playIntent = new Intent("play");
        Intent pauseIntent = new Intent("pause");
        Intent closeIntent = new Intent("close");



        pauseNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
                // Show controls on lock screen even when user hides sensitive content.
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_music)
                .setLargeIcon(icon)
                .setContentTitle(songsInfo.getSongName())
                .setContentText(songsInfo.getArtistName())
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())
                .addAction(R.drawable.ic_pause_circle, "Pause",
                        PendingIntent.getBroadcast(this,
                                1,
                                pauseIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        playNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
                // Show controls on lock screen even when user hides sensitive content.
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_music)
                .setLargeIcon(icon)
                .setContentTitle(songsInfo.getSongName())
                .setContentText(songsInfo.getArtistName())
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())
                .addAction(R.drawable.ic_play_circle, "play",
                        PendingIntent.getBroadcast(this,
                                1,
                                playIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_close_black, "close",
                        PendingIntent.getBroadcast(this,
                                1,
                                closeIntent,
                                PendingIntent.FLAG_CANCEL_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        startForeground(1, pauseNotification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
        unregisterReceiver(serviceBroadcast);
    }

    @Override
    public void onTrackPlay() {
        player.start();
        startForeground(1,pauseNotification);
        Toast.makeText(this, "play", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTrackPause() {
        player.pause();
        startForeground(1,playNotification);
        Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTrackClose() {
        stopSelf();
    }
}
