package com.ivhar;

public class Field {
    private final char[][] field;
    private String state;
    public final static int SIZE = 3;
    private final static int MOVE_LIMIT = 9;

    public Field() {
        field = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = ' ';
            }
        }
    }

    public final char[][] getField() {
        return field;
    }

    public final String getState() {
        return state;
    }

    public final char getCell(int x, int y) {
        return field[x][y];
    }

    public final void setCell(char side, int x, int y) {
        field[x][y] = side;
    }

    public final void printField() {
        System.out.println("---------");
        for (int i = 0; i < SIZE; i++) {
            System.out.println("| " + field[i][0] + " " + field[i][1] + " " + field[i][2] + " |");
        }
        System.out.println("---------");
    }

    public final boolean checkResult(char side) {
        for (int i = 0; i < SIZE; i++) {
            if (field[i][0] == field[i][1] && field[i][0] == field[i][2] && field[i][0] == side) {
                return true;
            } else if (field[0][i] == field[1][i] && field[0][i] == field[2][i] && field[0][i] == side) {
                return true;
            }
        }
        return (field[0][0] == field[1][1] && field[0][0] == field[2][2]
                || field[0][2] == field[1][1] && field[0][2] == field[2][0]) && field[1][1] == side;
    }

    public final void checkState(int moveCount) {
        if (checkResult('X')) {
            state = "X wins";
        } else if (checkResult('O')) {
            state = "O wins";
        } else if (moveCount == MOVE_LIMIT) {
            state = "Draw";
        } else {
            state = "Not finished";
        }
    }
}