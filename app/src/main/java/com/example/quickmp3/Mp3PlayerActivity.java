package com.example.quickmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Mp3PlayerActivity extends AppCompatActivity {

    TextView songTitle, currentTime, totalTime;
    ImageView uiImage, playAndPauseButton, nextButton,previousButton;
    SeekBar seekBar;
    ArrayList<AudioModel> songsList;
    AudioModel currentSong;
    MediaPlayer mediaPlayer = UIMediaPlayer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_music_player);

        songTitle = findViewById(R.id.ui_song_title);
        currentTime = findViewById(R.id.current_time);
        totalTime = findViewById(R.id.total_time);
        uiImage = findViewById(R.id.ui_image);
        playAndPauseButton = findViewById(R.id.pause_play);
        nextButton = findViewById(R.id.skip_next);
        previousButton = findViewById(R.id.skip_previous);
        seekBar = findViewById(R.id.seek_bar);

        songsList = (ArrayList<AudioModel>)getIntent().getSerializableExtra("LIST");
        setSongDetailsToPlayer();

        Mp3PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTime.setText(convertTimeIntoMileSecond(mediaPlayer.getCurrentPosition()+"")); // here .getCurrentPosition gives integer so we need to convert it as Sting that why concatenate with "".
                    if(mediaPlayer.isPlaying()){
                        playAndPauseButton.setImageResource(R.drawable.baseline_pause_24);
                    }else{
                        playAndPauseButton.setImageResource(R.drawable.baseline_play_arrow_24);
                    }
                }
                new Handler().postDelayed(this,100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser){
                   mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    void setSongDetailsToPlayer(){
        currentSong = songsList.get(UIMediaPlayer.currentIndex);
        songTitle.setText(currentSong.getTitle());
        totalTime.setText(convertTimeIntoMileSecond(currentSong.getDuration()));

        playAndPauseButton.setOnClickListener(v -> pausePlaySong());
        previousButton.setOnClickListener(v -> playPreviousSong());
        nextButton.setOnClickListener(v -> playNextSong());

        playSong();
    }

    private void playSong(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    private void pausePlaySong(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else{
            mediaPlayer.start();
        }
    }
    private void playNextSong(){
        if(songsList.size() -1 == UIMediaPlayer.currentIndex){
            return;
        }
        UIMediaPlayer.currentIndex +=1;
        mediaPlayer.reset();
        setSongDetailsToPlayer();
    }
    private void playPreviousSong(){
        if(UIMediaPlayer.currentIndex == 0){
            return;
        }
        UIMediaPlayer.currentIndex -=1;
        mediaPlayer.reset();
        setSongDetailsToPlayer();
    }

    public static String convertTimeIntoMileSecond(String duration){
        Long mileSecond = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(mileSecond) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(mileSecond) % TimeUnit.MINUTES.toSeconds(1));
    }
}