package com.pablo.tictactoe;

public class PVPLogic {
    private int[][] gameBoard;
    private int player = 1;

    public PVPLogic() {
        gameBoard = new int[3][3];
        for(int r = 0; r < 3; r++) {
            for(int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
        }
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer () {
        return player;
    }

    public boolean updateGameBoard(int row, int column) {
        if (gameBoard[row - 1][column - 1] == 0) {
            gameBoard[row - 1][column - 1] = player;
            return true;
        } else {
            return false;
        }
    }
}
