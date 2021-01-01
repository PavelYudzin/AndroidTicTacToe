package com.pablo.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {
    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;
    private final Paint paint = new Paint();
    private final PVPLogic game;
    private int cellSize = getWidth() / 3;

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        drawGameBoard(canvas);
        drawMarkers(canvas);
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
            if (game.updateGameBoard(row, column)) {
                invalidate();

                // updating the players turn
                if (game.getPlayer() == 2) {
                    game.setPlayer(1);
                } else {
                    game.setPlayer(2);
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
        float correction = cellSize * 0.15f;
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
        float correction = cellSize * 0.15f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(column * cellSize + correction,
                    row * cellSize + correction,
                    (column * cellSize + cellSize) - correction,
                    (row * cellSize + cellSize) - correction,
                    paint);
        }

    }
}
