package com.ivhar;
import java.util.Random;

import static com.ivhar.Field.SIZE;

public class Machine extends Player {
    private final String intelligence;
    private final Random random = new Random();
    public Machine(char mark, String difficulty) {
        super(mark);
        intelligence = difficulty;
    }

    @Override
    public final void makeTurn(Field field) {
        System.out.println("Making move level '" + intelligence + "'");
        switch (intelligence) {
            case "easy":
                makeRandomMove(field);
                break;
            case "medium":
                if (!optimalMove(field)) {
                    makeRandomMove(field);
                }
                break;
            case "hard":
                bestMove(field);
                break;
            default: break;
        }
        field.checkState(Game.getMoveCount());
        field.printField();
    }

    private void makeRandomMove(Field field) {
        int x, y;
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (field.getCell(x, y) != ' ');
        field.setCell(getMark(), x, y);
    }

    private boolean optimalMove(Field field) {
        char opponent = getMark() == 'O' ? 'X' : 'O';
        if (winOrBlock(getMark(), field)) {
            return true;
        }
        return winOrBlock(opponent, field);
    }

    private boolean winOrBlock(char side, Field field) {
        for (int i = 0; i < SIZE; i++) {
            if (checkLine(i, 0, i, 1, i, 2, side, field)
                    || checkLine(0, i, 1, i, 2, i, side, field)) {
                return true;
            }
        }
        return checkLine(0, 0, 1, 1, 2,  2, side, field)
                || checkLine(0, 2, 1, 1, 2, 0, side, field);
    }

    private boolean checkLine(int x1, int y1, int x2, int y2, int x3, int y3, char side, Field field) {
        if (field.getCell(x1, y1) == side && field.getCell(x2, y2) == side && field.getCell(x3, y3) == ' ') {
            field.setCell(getMark(), x3, y3);
            return true;
        }
        if (field.getCell(x1, y1) == side && field.getCell(x3, y3) == side && field.getCell(x2, y2) == ' ') {
            field.setCell(getMark(), x2, y2);
            return true;
        }
        if (field.getCell(x2, y2) == side && field.getCell(x3, y3) == side && field.getCell(x1, y1) == ' ') {
            field.setCell(getMark(), x1, y1);
            return true;
        }
        return false;
    }

    private void bestMove(Field field) {
        char[][] fieldCopy = field.getField();
        int x = -1, y = -1;
        int bestValue = Integer.MIN_VALUE;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (fieldCopy[i][j] == ' ') {
                    fieldCopy[i][j] = getMark();
                    int moveValue = minimax(field, fieldCopy, 0, false);
                    fieldCopy[i][j] = ' ';

                    if (moveValue > bestValue) {
                        x = i;
                        y = j;
                        bestValue = moveValue;
                    }
                }
            }
        }
        field.setCell(getMark(), x, y);
    }

    private int minimax(Field field, char[][] board, int depth, boolean isMaximizing) {
        if (field.checkResult(getMark())) {
            return 1;
        } else if (field.checkResult(getMark() == 'X' ? 'O' : 'X')) {
            return -1;
        } else if (!isAvailableMove(board)) {
            return 0;
        }

        int best;
        if (isMaximizing) {
            best = Integer.MIN_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = getMark();
                        best = Math.max(best, minimax(field, board, depth + 1, false));
                        board[i][j] = ' ';
                    }
                }
            }
        } else {
            best = Integer.MAX_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = getMark() == 'X' ? 'O' : 'X';
                        best = Math.min(best, minimax(field, board, depth + 1, true));
                        board[i][j] = ' ';
                    }
                }
            }
        }
        return best;
    }

    private boolean isAvailableMove(char[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }
}