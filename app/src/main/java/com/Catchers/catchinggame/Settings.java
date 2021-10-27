package com.Catchers.catchinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {
    private Button ChangeBG;
    private Button ReturnToMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ChangeBG = findViewById(R.id.ChangeView);
        ReturnToMain = findViewById(R.id.ReturnButton);

        ReturnToMain.setOnClickListener(view -> finish());
    }

}