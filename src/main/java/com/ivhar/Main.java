package com.ivhar;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class Main {
    private Main() { }
    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in, StandardCharsets.UTF_8);
        Game game;
        do {
            game = new Game(in);
        } while (game.playGame());
    }
}