package com.yahyaghanem.music.activities;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yahyaghanem.music.R;
import com.yahyaghanem.music.fragments.AllMusicFragment;
import com.yahyaghanem.music.fragments.OnlineFragment;
import com.yahyaghanem.music.fragments.PlayerFragment;
import com.yahyaghanem.music.interfaces.SelectedSongInterface;
import com.yahyaghanem.music.model.SongsInfo;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks, SelectedSongInterface{
    //button objects
    private Button deviceSongs;
    private Button onlineLink;
    private List<SongsInfo> songs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
    }

    private void setView() {
        //getting buttons from xml
        deviceSongs = findViewById(R.id.device_songs);
        onlineLink = findViewById(R.id.online_link);

        //attaching onclicklistener to buttons
        deviceSongs.setOnClickListener(this);
        onlineLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == deviceSongs) {
            //starting service
            //startService(new Intent(this, MusicService.class));
            getmusic();
        } else if (view == onlineLink) {
            OnlineFragment onlineFragment = new OnlineFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, onlineFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @AfterPermissionGranted(123)
    private void getmusic() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "reading music Tracks", Toast.LENGTH_SHORT).show();
            //write code for retrieving music tracks from the phone
            ContentResolver contentResolver = getContentResolver();
            Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor songCursor = contentResolver.query(songUri, null,null, null,null);
            if(songCursor != null && songCursor.moveToFirst()){

                do {
                    String name = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String artist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String url = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    int image = songCursor.getInt(songCursor.getColumnIndex(MediaStore.Images.Media._ID));


                    SongsInfo song = new SongsInfo(name,artist,url, image);
                    songs.add(song);
                } while(songCursor.moveToNext());
                    Log.d("getmusictracks", "getmusic: "+ songs.get(0).getImage());
                    if(songs != null) {
                        showSongList(songs);
                    }
            }else{
                Toast.makeText(this, "No music tracks on this device", Toast.LENGTH_SHORT).show();
            }
            songCursor.close();
        } else {
            EasyPermissions.requestPermissions(this, "We need permissions access Music tracks",
                    123, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        getmusic();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){

        }
    }

    private void showSongList(List<SongsInfo> songsInfoArrayList) {
        AllMusicFragment allMusicFragment = new AllMusicFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("listofsongs", new ArrayList<>(songsInfoArrayList));
        allMusicFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, allMusicFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onItemClickListener(SongsInfo songsInfo) {
        Log.d("onclick", "data back: "+songsInfo.getArtistName());

        Bundle bundle = new Bundle();
        bundle.putParcelable("selected song", songsInfo);
        PlayerFragment playerFragment = new PlayerFragment();
        playerFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, playerFragment)
                .addToBackStack(null)
                .commit();
    }
}
