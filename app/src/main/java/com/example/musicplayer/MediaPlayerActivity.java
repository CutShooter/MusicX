package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    MediaPlayer mediaPlayer;
    FloatingActionButton playPause;
    TextView textView;
    boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        setTitle("My Music Player");
        //60, 79, 230
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(74, 48, 219)));
        intent = getIntent();
        mediaPlayer = new MediaPlayer();
        playPause = findViewById(R.id.activityMediaPlayerPlayPauseButton);
        textView = findViewById(R.id.activityMediaPlayerSongNameTextView);
        textView.setText(intent.getStringExtra("Name") + "-" + intent.getStringExtra("Artist"));
        AudioAttributes.Builder atrr = new AudioAttributes.Builder();
        atrr.setUsage(AudioAttributes.USAGE_MEDIA);
        atrr.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
        mediaPlayer.setAudioAttributes(atrr.build());
        Uri uri = Uri.parse(intent.getStringExtra("URI"));
        try {
            mediaPlayer.setDataSource(this,uri);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
       playPause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.activityMediaPlayerPlayPauseButton){
            if(!isPlaying){
                playPause.setImageResource(R.drawable.baseline_pause_black_36dp);
                mediaPlayer.start();
            }else{
                playPause.setImageResource(R.drawable.baseline_play_arrow_black_36dp);
                mediaPlayer.pause();
            }
            isPlaying = !isPlaying;
        }else if(v.getId() == R.id.activityMediaPlayerNextTrackButton){
            mediaPlayer.stop();
//            mediaPlayer.setDataSource();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
