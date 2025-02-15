package com.ivhar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {
    private Game game;
    private Scanner scanner;
    private Field field;

    @BeforeEach
    void beforeEach() {
        scanner = mock(Scanner.class);
        game = new Game(scanner);
        field = new Field();
    }

    @ParameterizedTest
    @CsvSource({
            "'start easy easy', true",
            "'start easy medium', true",
            "'start easy hard', true",
            "'start medium easy', true",
            "'start medium medium', true",
            "'start medium hard', true",
            "'start hard easy', true",
            "'start hard medium', true",
            "'start hard hard', true",
            "'exit', false",
            "'start user exit', true"
    })
    void testPlayGame(String command, boolean result) {
        when(scanner.nextLine()).thenReturn(command);
        assertEquals(result, game.playGame());
    }

    @Test
    void testUserMakeValidTurn() {
        User user = new User('X', scanner);
        when(scanner.nextInt()).thenReturn(2).thenReturn(2);
        user.makeTurn(field);
        verify(scanner, times(2)).nextInt();
        assertEquals('X', field.getCell(1, 1));
    }

    @Test
    void testMakeTurnNonExistentCell() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        when(scanner.nextInt())
                .thenReturn(4).thenReturn(4)
                .thenReturn(2).thenReturn(2);

        User user = new User('X', scanner);
        user.makeTurn(field);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Coordinates should be from 1 to 3!"));

        verify(scanner, times(4)).nextInt();
        assertEquals('X', field.getCell(1, 1));
    }

    @Test
    void testMakeTurnOccupiedCell() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        when(scanner.nextInt())
                .thenReturn(1).thenReturn(1)
                .thenReturn(2).thenReturn(2);
        field.setCell('O', 0, 0);
        User user = new User('X', scanner);
        user.makeTurn(field);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("This cell is occupied! Choose another one!"));

        verify(scanner, times(4)).nextInt();
        assertEquals('X', field.getCell(1, 1));
    }

    @Test
    void testMakeTurnInputMismatch() {
        when(scanner.nextInt())
                .thenThrow(new InputMismatchException())
                .thenReturn(1).thenReturn(1);
        User user = new User('X', scanner);
        user.makeTurn(field);
        verify(scanner, times(3)).nextInt();
        assertEquals('X', field.getCell(0, 0));
    }
}