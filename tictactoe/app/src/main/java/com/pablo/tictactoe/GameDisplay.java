package com.pablo.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameDisplay extends AppCompatActivity {
    private TicTacToeBoard ticTacToeBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.dark_nav));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        }
        setContentView(R.layout.game_display);
        Button playAgainBTN = findViewById(R.id.play_again_button);
        Button homeBTN = findViewById(R.id.home_button);
        TextView playerTurn = findViewById(R.id.info_line);
        Resources resources = getResources();

        String turn = resources.getString(R.string.turn);
        String won = resources.getString(R.string.won);
        String tie = resources.getString(R.string.tie);
        String[] strings = new String[5];
        String[] names = getIntent().getStringArrayExtra("PLAYER_NAMES");
        strings[0] = names[0];
        strings[1] = names[1];
        strings[2] = turn;
        strings[3] = won;
        strings[4] = tie;

        TextView playerOneName = findViewById(R.id.player_one_name);
        TextView playerTwoName = findViewById(R.id.player_two_name);
        TextView playerOneScore = findViewById(R.id.player_one_score);
        TextView playerTwoScore = findViewById(R.id.player_two_score);

        playerTurn.setText((strings[0] + turn));

        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        ticTacToeBoard = findViewById(R.id.ticTacToeBoard);
        ticTacToeBoard.setUpGame(playAgainBTN, homeBTN, playerTurn, strings, playerOneScore, playerTwoScore);
        playerOneName.setText(strings[0]);
        playerTwoName.setText(strings[1]);
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