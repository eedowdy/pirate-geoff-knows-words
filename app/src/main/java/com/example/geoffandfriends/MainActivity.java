package com.example.geoffandfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int turns;

    private String word;

    private ArrayList<Character> letters;

    private ArrayList<Character> result;

    private ArrayList<Character> incorrectLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        turns = 6;

        word = "geoffrey";

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
            if (guessLetter.getText().length() != 1) {
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid input", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 25);
                toast.show();
            } else if (checkLetter(guessLetter.getText().charAt(0))){
                updateDisplay();
            } else {
                incorrectLetters.add(guessLetter.getText().charAt(0));
                updateDisplay();
                guessLetter.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast toast = Toast.makeText(getApplicationContext(), "Try again.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 25);
                toast.show();
            }
            guessLetter.setText("");
            guessLetter.onEditorAction(EditorInfo.IME_ACTION_DONE);
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
            sb.append(letter);
            sb.append("  ");
        }
        incorrect.setText(sbs.toString());
    }

    private boolean checkLetter(char letter) {
        if (letters.contains(letter)) {
            for (int i = 0; i < letters.size(); i++) {
                if (letters.get(i) == letter) {
                    result.set(i, letter);
                }
            }
            return true;
        }
        return false;
    }
}
