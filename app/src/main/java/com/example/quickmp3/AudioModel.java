package com.example.quickmp3;

import java.io.Serializable;

public class AudioModel implements Serializable {
    private String title;
    private String artist;
    private String album;
    private String duration;
    private String path;

    public AudioModel(String title, String artist, String album, String duration, String path) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getDuration() {
        return duration;
    }

    public String getPath() {
        return path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
