package com.pablo.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PlayerSetup extends AppCompatActivity {
    private EditText player1;
    private EditText player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_setup);
        player1 = findViewById(R.id.playerOneName);
        player2 = findViewById(R.id.playerTwoName);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.dark_nav));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        }
    }

    public void submitButtonClick(View view) {
        Resources resources = getResources();
        String player1Default = resources.getString(R.string.player1);
        String player2Default = resources.getString(R.string.player2);
        String playerOneName = player1.getText().toString();
        String playerTwoName = player2.getText().toString();
        if (playerOneName.trim().equals("")) {
            playerOneName = player1Default;
        }
        if (playerTwoName.trim().equals("")) {
            playerTwoName = player2Default;
        }
        Intent intent = new Intent(this, GameDisplay.class);
        intent.putExtra("PLAYER_NAMES", new String[]{playerOneName, playerTwoName});
        startActivity(intent);
    }
}