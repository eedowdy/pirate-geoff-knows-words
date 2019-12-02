package com.example.geoffandfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        word = "geoffrey";

        char[] letters = word.toCharArray();

        TextView displayWord = findViewById(R.id.displayWord);
        StringBuilder sb = new StringBuilder();
        for (char letter : letters) {
            sb.append("_ ");
        }
        displayWord.setText(sb.toString());

        
    }
}
