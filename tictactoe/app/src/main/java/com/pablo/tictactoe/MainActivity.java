package com.pablo.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button pve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.dark_nav));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.status_bar));
        }

        pve = findViewById(R.id.pve_button);
        pve.setVisibility(View.GONE);
    }

    public void pvpButtonClick(View view) {
        Intent intent = new Intent(this, PlayerSetup.class);
        startActivity(intent);
    }

    public void pveButtonClick(View view) {
        Intent intent = new Intent(this, GameDisplay.class);
    }
}