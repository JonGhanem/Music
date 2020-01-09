package com.yahyaghanem.music.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SongsInfo implements Parcelable {
    private String songName;
    private String artistName;
    private String songUrl;
    private int image;



    public SongsInfo(){

    }

    public SongsInfo(String songname, String artistname, String songUrl, int image) {
        this.songName = songname;
        this.artistName = artistname;
        this.songUrl = songUrl;
        this.image = image;

    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.songName);
        dest.writeString(this.artistName);
        dest.writeString(this.songUrl);
    }

    protected SongsInfo(Parcel in) {
        this.songName = in.readString();
        this.artistName = in.readString();
        this.songUrl = in.readString();
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static final Parcelable.Creator<SongsInfo> CREATOR = new Parcelable.Creator<SongsInfo>() {
        @Override
        public SongsInfo createFromParcel(Parcel source) {
            return new SongsInfo(source);
        }

        @Override
        public SongsInfo[] newArray(int size) {
            return new SongsInfo[size];
        }
    };
}
