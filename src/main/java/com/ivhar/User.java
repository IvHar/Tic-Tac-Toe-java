package com.ivhar;

import java.util.InputMismatchException;
import java.util.Scanner;

public class User extends Player {
    private final Scanner in;
    public User(char mark, Scanner in) {
        super(mark);
        this.in = in;
    }

    @Override
    public final void makeTurn(Field field) {
        char current = getMark();
        int x, y;
        while (true) {
            System.out.print("Enter the coordinates: ");
            try {
                x = in.nextInt() - 1;
                y = in.nextInt() - 1;
            } catch (InputMismatchException ex) {
                System.out.println("You should enter numbers!");
                in.nextLine();
                continue;
            }
            if (x < 0 || x > 2 || y < 0 || y > 2) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (field.getCell(x, y) != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                field.setCell(current, x, y);
                field.printField();
                field.checkState(Game.getMoveCount());
                return;
            }
        }
    }
}