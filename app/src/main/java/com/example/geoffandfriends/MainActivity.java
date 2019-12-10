package com.example.geoffandfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int turns;

    private ArrayList<Character> letters;

    private ArrayList<Character> result;

    private ArrayList<Character> incorrectLetters;

    private String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.pirate_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        turns = 6;
        word = getWord();

        letters = new ArrayList<>();
        result = new ArrayList<>();
        incorrectLetters = new ArrayList<>();

        for (char letter : word.toCharArray()) {
            letters.add(letter);
            result.add('_');
        }

        updateDisplay();

        EditText guessLetter = findViewById(R.id.guessLetter);
        guessLetter.setOnClickListener(v -> guessLetter.setText(""));
        Button submitGuess = findViewById(R.id.submitGuess);
        submitGuess.setOnClickListener(unused -> {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            if (guessLetter.getText().length() != 1 || !Character.isLetter(guessLetter.getText().charAt(0))) {
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid input", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 25);
                toast.show();
            } else if (!checkLetter(guessLetter.getText().charAt(0))){
                if (turns == 0) {
                    mediaPlayer.stop();
                    Intent intent = new Intent(this, EndActivity.class);
                    intent.putExtra("won", false);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Try again.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 25);
                    toast.show();
                }
            }
            guessLetter.setText("");
            updateDisplay();
            if (!result.contains('_')) {
                mediaPlayer.stop();
                Intent intent = new Intent(this, EndActivity.class);
                intent.putExtra("won", true);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateDisplay() {
        TextView displayWord = findViewById(R.id.displayWord);
        StringBuilder sb = new StringBuilder();
        for (char letter : result) {
            sb.append(letter);
            sb.append(" ");
        }
        displayWord.setText(sb.toString());

        TextView incorrect = findViewById(R.id.incorrectLetters);
        StringBuilder sbs = new StringBuilder();
        for (char letter : incorrectLetters) {
            sbs.append(letter);
            sbs.append("  ");
        }
        incorrect.setText(sbs.toString());
    }

    private boolean checkLetter(char letter) {
        if (letters.contains(Character.toLowerCase(letter))) {
            for (int i = 0; i < letters.size(); i++) {
                if (letters.get(i) == Character.toLowerCase(letter)) {
                    result.set(i, letter);
                }
            }
            return true;
        } else if (!incorrectLetters.contains(letter)) {
            turns--;
            ImageView geoff = findViewById(R.id.geoff);
            ObjectAnimator animation = ObjectAnimator.ofFloat(geoff,
                    "translationX", geoff.getX() - 25f);
            animation.setDuration(500);
            animation.start();
            incorrectLetters.add(letter);
        }
        return false;
    }

    private String getWord() {
        int number = (int)(Math.random() * 524);
        String[] words = getResources().getStringArray(R.array.words);
        return words[number];
    }
}
