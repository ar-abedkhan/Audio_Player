package com.zeeshan_s.audioplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chibde.visualizer.BarVisualizer;
import com.chibde.visualizer.CircleBarVisualizer;
import com.zeeshan_s.audioplayer.databinding.ActivityAudioClickedBinding;

import java.io.IOException;

public class AudioClicked extends AppCompatActivity {
    ActivityAudioClickedBinding binding;
    MediaPlayer mediaPlayer;

    String uri;
    Uri audioUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAudioClickedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String audioName = intent.getStringExtra("audioName");
//        Uri audioUri = Uri.parse(intent.getStringExtra("uri"));

        mediaPlayer = new MediaPlayer();

        uri = intent.getStringExtra("uri");
        audioUri = Uri.parse(uri);

        Log.i("tag", "Audio Uri-----: "+audioUri);
        binding.selectedAudioName.setText(audioName);

        try {
            mediaPlayer.setDataSource(AudioClicked.this, audioUri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        binding.playBtn.setOnClickListener(view -> {
          mediaPlayer.start();
            binding.selectedAudioName.setSelected(true);

            binding.playBtnLayout.setVisibility(View.GONE);
          binding.pauseBtnLayout.setVisibility(View.VISIBLE);
        });

        binding.pauseBtn.setOnClickListener(view -> {
            mediaPlayer.pause();
            binding.selectedAudioName.setSelected(false);
            binding.playBtnLayout.setVisibility(View.VISIBLE);
            binding.pauseBtnLayout.setVisibility(View.GONE);

        });
        



//        -----------------Setting audio visualizer in my project--------------------------
        BarVisualizer barVisualizer = findViewById(R.id.barvisualizer);
//        mediaPlayer = MediaPlayer.create(this, audioUri);

// set custom color to the line.
        barVisualizer.setColor(ContextCompat.getColor(this, R.color.white));

// Set you media player to the visualizer.
        barVisualizer.setPlayer(mediaPlayer.getAudioSessionId());

        barVisualizer.setDensity(100);

    }



    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null){
            mediaPlayer.pause();
            mediaPlayer.stop();
        }
    }
}