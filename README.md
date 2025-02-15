# Tic Tac Toe Game

## Overview
This project is a console implementation of the Tic Tac Toe. It allows players to choose between single-player and two-player modes, with different difficulty levels available for singleplayer

## Features
- **Game Modes:** Play against another user or the computer
- **Difficulty:** `easy`, `medium`, and `hard`
- **Input validation:** Ensures moves are valid and prevents errors
- **Hard difficulty:** The hard mode uses the minimax algorithm for impossible user victory

## Classes
### 1. Main
- **Purpose:** Manages the main loop to start new games
- **Key Method:**
    - `main(String[] args)`: Initializes new games until the user exits

### 2. Game
- **Purpose:** Manages game setup and input handling
- **Key Methods:**
    - `playGame()`: Reads user commands and creates the game
    - `chooseMode(String param1, String param2)`: Configures gamemodes and difficulty levels
    - `start(Player p1, Player p2)`: Starts the game loop and alternates turns
    - `printState()`: Prints the current status of the game

### 3. Field
- **Purpose**: Represents the game board
- **Key Methods**:
    - `printField()`: Prints the game board in the console
    - `checkResult(char side)`: Checks if a player has won
    - `checkState(int moveCount)`: Updates the game's state (e.g. "Draw" or "Not finished")

### 4. Player
- **Purpose:** Abstraction over user and machine
- **Key Method:**
    - `makeTurn(Field field)`: Executes a player's move

### 5. User
- **Purpose:** Represents a human player
- **Key Methods:**
    - `makeTurn(Field field)`: Reads user input for the move

### 6. Machine
- **Purpose:** Represents an AI player
- **Key Methods:**
    - `makeTurn(Field field)`: Makes moves based on the selected difficulty
    - `makeRandomMove(Field field)`: Makes a random move (easy)
    - `optimalMove(Field field)`: Looks for the opportunity to win or block the opponent (medium)
    - `bestMove(Field field)`: Uses the minimax algorithm to make the best possible move, not allowing the user to win (hard)

## How to Play
1. Run the Main class to start the program.
2. Enter commands to control the game:
    - **Start a game:** Use `start <player1_mode> <player2_mode>`
        - Modes: `user`, `easy`, `medium`, `hard`
        - Example: `start user medium`
    - **Exit the game:** Enter `exit`
3. For user turns:
    - Enter row and column to make a move and write the mark into the blank cell (e.g., `1 3`)