package com.pablo.tictactoe;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PVPLogic {
    private int[][] gameBoard;
    private int player = 1;
    private int playerWasFirst = 1;
    private Button playAgainBTN;
    private Button homeBTN;
    private TextView playerTurn;
    private String[] playerNames = {"Player 1", "Player 2"};
    private int boardFilled;
    private TextView showPlayerOneScore;
    private TextView showPlayerTwoScore;
    private int playerOneScore;
    private int playerTwoScore;

    //1st element --> row,
    // 2nd element --> column,
    // 3rd element --> line type (1 - horizontal, 2 - vertical, 3 - diagonal negative , 4 - diagonal positive)
    private int[] winType = {-1, -1, -1};

    public PVPLogic() {
        gameBoard = new int[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
        }
        boardFilled = 0;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayAgainBTN(Button playAgainBTN) {
        this.playAgainBTN = playAgainBTN;
    }

    public void setHomeBTN(Button homeBTN) {
        this.homeBTN = homeBTN;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public int[] getWinType() {
        return winType;
    }

    public void setPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public void setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }

    public void setShowPlayerOneScore(TextView showPlayerOneScore) {
        this.showPlayerOneScore = showPlayerOneScore;
    }

    public void setShowPlayerTwoScore(TextView showPlayerTwoScore) {
        this.showPlayerTwoScore = showPlayerTwoScore;
    }

    public boolean updateGameBoard(int row, int column) {
        if (gameBoard[row - 1][column - 1] == 0) {
            gameBoard[row - 1][column - 1] = player;
            boardFilled++;
            if (player == 1) {
                playerTurn.setText((playerNames[1] + "'s turn"));
            } else {
                playerTurn.setText((playerNames[0] + "'s turn"));
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean winnerCheck() {
        boolean isWinner = false;

        // horizontal check
        for (int r = 0; r < 3; r++) {
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] &&
                    gameBoard[r][0] != 0) {
                winType = new int[]{r, 0, 1};
                isWinner = true;
            }
        }

        // vertical check
        for (int c = 0; c < 3; c++) {
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[0][c] == gameBoard[2][c] &&
                    gameBoard[0][c] != 0) {
                winType = new int[]{0, c, 2};
                isWinner = true;
            }
        }

        // diagonal negative check
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] &&
                gameBoard[0][0] != 0) {
            winType = new int[]{0, 2, 3};
            isWinner = true;
        }

        // diagonal positive check
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] &&
                gameBoard[2][0] != 0) {
            winType = new int[]{2, 2, 4};
            isWinner = true;
        }

        if (isWinner) {
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            String won = " Won!!!";
            playerTurn.setText((playerNames[player - 1] + won));
            if(player == 1) {
                playerOneScore++;
            } else {
                playerTwoScore++;
            }
            showScore();
        } else if (boardFilled == 9) {
            String tie = "Tie Game!!!";
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText(tie);
            showScore();
        }
        return isWinner;
    }

    public void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
        }
        boardFilled = 0;
        if (playerWasFirst == 1) {
            player = 2;
            playerWasFirst = 2;
        } else {
            player = 1;
            playerWasFirst = 1;
        }
        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);
        playerTurn.setText((playerNames[player -1] + "'s turn"));
        winType = new int[]{-1, -1, -1};
    }

    private void showScore() {
        String oneScoreText = Integer.toString(playerOneScore);
        String twoScoreText = Integer.toString(playerTwoScore);
        showPlayerOneScore.setText(oneScoreText);
        showPlayerTwoScore.setText(twoScoreText);
    }
}
