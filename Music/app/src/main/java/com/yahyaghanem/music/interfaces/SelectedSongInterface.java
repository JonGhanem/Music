package com.yahyaghanem.music.interfaces;

import com.yahyaghanem.music.model.SongsInfo;

public interface SelectedSongInterface {

    // Sending Selected Item from Adapter to Details Fragment
    void onItemClickListener(SongsInfo songsInfo);
}
