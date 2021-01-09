package com.pablo.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {
    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;
    private boolean winningLine = false;
    private final Paint paint = new Paint();
    private final PVPLogic game;
    private int cellSize = getWidth() / 3;
    private float correction;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        game = new PVPLogic();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard,
                0, 0);

        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        int boardWidth = (int) (getMeasuredWidth() * 0.85);
        int boardHeight = (int) (getMeasuredHeight() * 0.85);
        int dimension = Math.min(boardWidth, boardHeight);
        cellSize = dimension / 3;
        setMeasuredDimension(dimension, dimension);
        correction = cellSize * 0.15f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        drawGameBoard(canvas);
        drawMarkers(canvas);

        if(winningLine) {
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            int row = (int) Math.ceil(y / cellSize);
            int column = (int) Math.ceil(x / cellSize);

            if (!winningLine) {
                if (game.updateGameBoard(row, column)) {
                    invalidate();

                    if (game.winnerCheck()) {
                        winningLine = true;
                        invalidate();
                    }

                    // updating the players turn
                    if (game.getPlayer() == 2) {
                        game.setPlayer(1);
                    } else {
                        game.setPlayer(2);
                    }
                }
            }
            invalidate();
            return true;
        }
        return false;
    }

    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(12);

        for (int c = 1; c < 3; c++) {
            canvas.drawLine(cellSize * c, 0, cellSize * c, canvas.getWidth(), paint);
            canvas.drawLine(0, cellSize * c, canvas.getWidth(), cellSize * c, paint);
        }
    }

    private void drawMarkers(Canvas canvas) {
        paint.setStrokeWidth(12);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (game.getGameBoard()[r][c] != 0) {
                    if (game.getGameBoard()[r][c] == 1) {
                        drawX(canvas, r, c);
                    } else {
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }

    private void drawX(Canvas canvas, int row, int column) {
        paint.setColor(XColor);
        canvas.drawLine((column + 1) * cellSize - correction,
                row * cellSize + correction,
                column * cellSize + correction,
                (row + 1) * cellSize - correction,
                paint);
        canvas.drawLine(column * cellSize + correction,
                row * cellSize + correction,
                (column + 1) * cellSize - correction,
                (row + 1) * cellSize - correction,
                paint);
    }

    private void drawO(Canvas canvas, int row, int column) {
        paint.setColor(OColor);
        canvas.drawCircle(column * cellSize + cellSize / 2f, row * cellSize + cellSize / 2f,
                cellSize / 2f - correction, paint);

        /*
        canvas.drawOval(column * cellSize + correction,
                row * cellSize + correction,
                (column * cellSize + cellSize) - correction,
                (row * cellSize + cellSize) - correction,
                paint);
        */
    }

    private void drawHorizontalLine(Canvas canvas, int row, int column) {
        canvas.drawLine(column, row * cellSize + cellSize / 2f,
                cellSize * 3, row * cellSize + cellSize / 2f,
                paint);
    }

    private void drawVerticalLine(Canvas canvas, int row, int column) {
        canvas.drawLine(column * cellSize + cellSize / 2f, row,
                column * cellSize + cellSize / 2f, cellSize * 3,
                paint);
    }

    private void drawDiagonalLinePositive(Canvas canvas) {
        canvas.drawLine(0, cellSize * 3 - cellSize / 2f,
                cellSize, cellSize * 3 - cellSize / 2f,
                paint);
        canvas.drawLine(cellSize, cellSize + cellSize / 2f,
                cellSize * 2, cellSize + cellSize / 2f,
                paint);
        canvas.drawLine(cellSize * 2, cellSize / 2f,
                cellSize * 3, cellSize / 2f,
                paint);
    }

    private void drawDiagonalLineNegative(Canvas canvas) {
        canvas.drawLine(0, cellSize / 2f,
                cellSize, cellSize / 2f,
                paint);
        canvas.drawLine(cellSize, cellSize + cellSize / 2f,
                cellSize * 2, cellSize + cellSize / 2f,
                paint);
        canvas.drawLine(cellSize * 2, cellSize * 2 + cellSize / 2f,
                cellSize * 3, cellSize * 2 + cellSize / 2f,
                paint);
    }

    public void drawWinningLine(Canvas canvas) {
        paint.setStrokeWidth(cellSize);
        paint.setAlpha(70);
        int row = game.getWinType()[0];
        int column = game.getWinType()[1];

        switch (game.getWinType()[2]) {
            case 1:
                drawHorizontalLine(canvas, row, column);
                break;
            case 2:
                drawVerticalLine(canvas, row, column);
                break;
            case 3:
                drawDiagonalLineNegative(canvas);
                break;
            case 4:
                drawDiagonalLinePositive(canvas);
                break;
        }
    }

    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] strings,
                          TextView playerOneScore, TextView playerTwoScore) {
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(strings[0], strings[1]);
        game.setTurn(strings[2]);
        game.setWon(strings[3]);
        game.setTie(strings[4]);
        game.setShowPlayerOneScore(playerOneScore);
        game.setShowPlayerTwoScore(playerTwoScore);
        game.setPlayerOneScore(0);
        game.setPlayerTwoScore(0);
    }

    public void resetGame() {
        game.resetGame();
        winningLine = false;
    }
}
