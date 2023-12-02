package com.example.quickmp3;

import android.media.MediaPlayer;

public class UIMediaPlayer {
    static MediaPlayer instance;

    public static MediaPlayer getInstance(){
        if(instance == null){
            return new MediaPlayer();
        }else{
            return instance;
        }
    }
    public static int currentIndex = -1;
}


