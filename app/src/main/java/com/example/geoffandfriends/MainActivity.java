package com.example.geoffandfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
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
        Button submitGuess = findViewById(R.id.submitGuess);
        submitGuess.setOnClickListener(unused -> {
            if (guessLetter.getText().length() != 1 || !Character.isLetter(guessLetter.getText().charAt(0))) {
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid input", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 25);
                toast.show();
            } else if (!checkLetter(guessLetter.getText().charAt(0))){
                if (turns == 0) {
                    Intent intent = new Intent(this, EndActivity.class);
                    intent.putExtra("won", false);
                    startActivity(intent);
                } else {
                    guessLetter.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast toast = Toast.makeText(getApplicationContext(), "Try again.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 25);
                    toast.show();
                }
            }
            updateDisplay();
            guessLetter.setText("");
            guessLetter.onEditorAction(EditorInfo.IME_ACTION_DONE);
            if (!result.contains('_')) {
                Intent intent = new Intent(this, EndActivity.class);
                intent.putExtra("won", true);
                startActivity(intent);
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
