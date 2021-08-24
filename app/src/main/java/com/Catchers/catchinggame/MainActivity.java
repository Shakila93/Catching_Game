package com.Catchers.catchinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button PlayButton;
    private Button LeaderboardButton;
    private Button SettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve all View elements and store in variables
        PlayButton = findViewById(R.id.PlayButton);
        LeaderboardButton = findViewById(R.id.LeaderboardButton);
        SettingsButton = findViewById(R.id.SettingsButton);

        final MainActivity mainActivity = this;
        // Setup Activities
        PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, GameActivity.class);
                startActivity(intent);
            }
        });
        LeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, Leaderboard.class);
                startActivity(intent);
            }
        });
        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, Settings.class);
                startActivity(intent);
            }
        });
    }
}