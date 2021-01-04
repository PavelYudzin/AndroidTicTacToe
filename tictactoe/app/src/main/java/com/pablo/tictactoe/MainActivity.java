package com.pablo.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button pve = findViewById(R.id.pve_button);
        pve.setVisibility(View.INVISIBLE);
    }

    public void playButtonClick(View view) {
        Intent intent = new Intent(this, PlayerSetup.class);
        startActivity(intent);
    }
}