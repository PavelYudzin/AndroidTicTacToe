package com.pablo.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameDisplay extends AppCompatActivity {
    private TicTacToeBoard ticTacToeBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_display);
        Button playAgainBTN = findViewById(R.id.play_again_button);
        Button homeBTN = findViewById(R.id.home_button);
        TextView playerTurn = findViewById(R.id.player_display);
        String[] playerNames = getIntent().getStringArrayExtra("PLAYER_NAMES");
        TextView playerOneName = findViewById(R.id.player_one_name);
        TextView playerTwoName = findViewById(R.id.player_two_name);
        TextView playerOneScore = findViewById(R.id.player_one_score);
        TextView playerTwoScore = findViewById(R.id.player_two_score);

        if(playerNames != null) {
            playerTurn.setText((playerNames[0] + "'s turn"));
        }

        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        ticTacToeBoard = findViewById(R.id.ticTacToeBoard);
        ticTacToeBoard.setUpGame(playAgainBTN, homeBTN, playerTurn, playerNames, playerOneScore, playerTwoScore);
        playerOneName.setText(playerNames[0]);
        playerTwoName.setText(playerNames[1]);
        playerOneScore.setText("0");
        playerTwoScore.setText("0");
    }

    public void playAgainButtonClick(View view) {
        ticTacToeBoard.resetGame();
        ticTacToeBoard.invalidate();
    }

    public void homeButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}