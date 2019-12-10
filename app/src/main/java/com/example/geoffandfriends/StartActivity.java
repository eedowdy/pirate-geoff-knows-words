package com.example.geoffandfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        MediaPlayer mediaPlayer = MediaPlayer.create(StartActivity.this,R.raw.opening_pirate);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        Button startGame = findViewById(R.id.newGameButton);
        startGame.setOnClickListener(unused -> {
            mediaPlayer.stop();
            final Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
