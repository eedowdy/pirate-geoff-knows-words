package com.example.geoffandfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.Image;
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

        Button playAgain = findViewById(R.id.playAgainButton);
        TextView winnerLoser = findViewById(R.id.winnerLoser);
        ImageView treasureChest = findViewById(R.id.treasureChest);
        ImageView pirateShip = findViewById(R.id.pirateShip);

        if (getIntent().getBooleanExtra("won", false)) {
            winnerLoser.setText("You Won :)");
            pirateShip.setVisibility(View.GONE);
            treasureChest.setVisibility(View.VISIBLE);

        } else {
            treasureChest.setVisibility(View.VISIBLE);
            winnerLoser.setText("You Lose :(");
            treasureChest.setVisibility(View.GONE);
            ObjectAnimator animation = ObjectAnimator.ofFloat(pirateShip,
                    "translationX", pirateShip.getX() - 25f);
            animation.setDuration(500);
            animation.start();
        }

        playAgain.setOnClickListener(v -> {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            finish();
        });
    }
}
