package com.example.geoffandfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        MediaPlayer mediaPlayer;

        Button playAgain = findViewById(R.id.playAgainButton);
        TextView winnerLoser = findViewById(R.id.winnerLoser);
        ImageView treasureChest = findViewById(R.id.treasureChest);
        ImageView pirateShip = findViewById(R.id.pirateShip);

        if (getIntent().getBooleanExtra("won", false)) {
            mediaPlayer = MediaPlayer.create(EndActivity.this,R.raw.opening_pirate);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            winnerLoser.setText("You Won :)");
            pirateShip.setVisibility(View.GONE);
            treasureChest.setVisibility(View.VISIBLE);

        } else {
            mediaPlayer = MediaPlayer.create(EndActivity.this,R.raw.sad_pirate);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            treasureChest.setVisibility(View.VISIBLE);
            winnerLoser.setText("You Lose :(");
            treasureChest.setVisibility(View.GONE);
            ObjectAnimator animation = ObjectAnimator.ofFloat(pirateShip,
                    "translationX", 1200f);
            animation.setDuration(8000);
            animation.start();
        }

        playAgain.setOnClickListener(v -> {
            mediaPlayer.stop();
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            finish();
        });
    }
}
