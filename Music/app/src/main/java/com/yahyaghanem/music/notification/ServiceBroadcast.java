package com.yahyaghanem.music.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceBroadcast extends BroadcastReceiver {

    public interface Playable {

        void onTrackPlay();

        void onTrackPause();

        void onTrackClose();
    }

    private Playable playable;

    public void setPlayable(Playable playable) {
        this.playable = playable;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case "pause":
                playable.onTrackPause();
                break;
            case "play":
                playable.onTrackPlay();
                break;
            case "close":
                playable.onTrackClose();
                break;
        }
    }
}
