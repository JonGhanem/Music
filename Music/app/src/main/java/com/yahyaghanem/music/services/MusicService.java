package com.yahyaghanem.music.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.yahyaghanem.music.activities.MainActivity;

import java.io.IOException;

public class MusicService extends Service {

    MediaPlayer player = new MediaPlayer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String song = intent.getStringExtra("song");
        Log.d("song", "name: "+ song);
        //getting systems default ringtone
        if(!player.isPlaying()) {

            try {
                player.setDataSource(song);
            } catch (IllegalArgumentException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (SecurityException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IllegalStateException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //setting loop play to true
            //this will make the ringtone continuously playing
//        player.setLooping(true);
            try {
                player.prepare();
            } catch (IllegalArgumentException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (SecurityException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IllegalStateException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //staring the player
            player.start();
        }
        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player.isPlaying()){
            //stopping the player when service is destroyed
            player.pause();
        }
        player.release();
    }
}
