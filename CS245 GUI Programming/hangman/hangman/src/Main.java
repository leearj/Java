/**
 * *************************************************************
 * file: Main.java
 * authors: Colin Trotter, Andrew Trang, Jaeseung Lee
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: qtrProject_1.3
 * date last modified: 10/24/2016
 *
 * purpose: GUI for the game. Displays information about the game to the player.
 *
 ***************************************************************
 */
package hangman;

public class Main {

    public static void main(String[] args) {
        HangmanGame hangmanGame = new HangmanGame();
        ColorGame colorGame = new ColorGame();
        HighScoreManager highScores = new HighScoreManager("/resources/highscores.txt");
        SudokuGame sudokuGame = new SudokuGame();
        
        GUI g = new GUI();
        hangmanGame.setGUI(g);
        colorGame.setGUI(g);
        highScores.setGUI(g);
        sudokuGame.setGUI(g);
        
        g.setHangmanGame(hangmanGame);
        g.setColorGame(colorGame);
        g.setHighScoreManager(highScores);
        g.setSudokuGame(sudokuGame);
        g.init();

    }
}
