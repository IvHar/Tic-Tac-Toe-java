package com.ivhar;

import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private final Field field;
    private final Scanner in;
    private final String[] modes = {"user", "easy", "medium", "hard"};
    private static int moveCount;
    private final static int COMMAND_WORDS_COUNT = 3;

    public Game(Scanner in) {
        moveCount = 0;
        field = new Field();
        this.in = in;
    }

    public static int getMoveCount() {
        return moveCount;
    }

    public final boolean playGame() {
        String command;
        System.out.print("Input command: ");
        command = in.nextLine();
        String[] commandParts = command.split(" ");
        if (commandParts.length == COMMAND_WORDS_COUNT && commandParts[0].equals("start")
                && Arrays.stream(modes).anyMatch(p -> p.equals(commandParts[1]))
                && Arrays.stream(modes).anyMatch(p -> p.equals(commandParts[2]))) {
            chooseMode(commandParts[1], commandParts[2]);
            return true;
        } else if (commandParts[0].equals("exit")) {
            return false;
        } else {
            System.out.println("Bad parameters!");
            return true;
        }
    }

    private void chooseMode(String param1, String param2) {
        Player p1, p2;
        if (param1.equals("user")) {
            p1 = new User('X', in);
        } else {
            p1 = new Machine('X', param1);
        }

        if (param2.equals("user")) {
            p2 = new User('O', in);
        } else {
            p2 = new Machine('O', param2);
        }
        start(p1, p2);
    }

    private void start(Player p1, Player p2) {
        field.printField();
        moveCount++;
        p1.makeTurn(field);
        while (field.getState().equals("Not finished")) {
            moveCount++;
            p2.makeTurn(field);
            if (!field.getState().equals("Not finished")) {
                printState();
                return;
            }
            moveCount++;
            p1.makeTurn(field);
        }
        printState();
    }

    public final void printState() {
        System.out.println(field.getState());
    }
}
