/**
 * *************************************************************
 * file: HangmanGame.java
 * authors: Colin Trotter, Andrew Trang, Jaeseung Lee
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: qtrProject_1.3
 * date last modified: 10/24/2016
 *
 * purpose: Represents a game of Hangman. The objective is for the player to correctly guess all of the
 * letters in a word. Uses a GUI object to display the game to the player.
 *
 ***************************************************************
 */
package hangman;

import java.util.Random;

public class HangmanGame extends Sound {

    private int remainingGuesses; // Guesses remaining until lose
    private char[] displayedLetters; // Letters that have been guessed and are currently displayed to the player
    private String word; // Word to guess
    private GUI gui; // GUI
    private int score;

    /*
    * FUNCTION: start()
    *
    * Sets the game up for a new game by settting all components of the game to their initial state, 
    * and picking a random word to guess
     */
    public void start() {
        playVoice(getClass().getResource("/resources/play.wav"));

        score = 100;
        gui.updateScore(score);
        remainingGuesses = 6;
        word = randomWord();
        displayedLetters = new char[word.length()];
        for (int i = 0; i < displayedLetters.length; i++) {
            displayedLetters[i] = '_';
        }
        gui.resetAlphabet();
        gui.updateWord(displayedLetters);
        gui.updateHangman(0);
        System.out.println("Game State: Generated word \"" + word.toUpperCase() + "\"");
    }

    /*
    * FUNCTION: setGUI()
    *
    * Sets the GUI object for this instance of the game.
     */
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    /*
    * FUNCTION: randomWord()
    *
    * Returns a random word from the provided list of words.
     */
    private String randomWord() {
        Random rand = new Random();
        String[] possibleWords = {"abstract", "cemetary", "nurse", "pharmacy", "climbing"};
        return possibleWords[rand.nextInt(possibleWords.length)];
    }

    /*
    * FUNCTION: guessLetter()
    *
    * Guesses a letter in the word. 
    * If a correct letter is guessed, the gui is updated to show this letter.
    * If an incorrect letter is guessed, the player loses 10 points, and the gui displays a new body part on the hangman.
     */
    public void guessLetter(String guess) {
        char letter = guess.toLowerCase().charAt(0);
        boolean found = false;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                System.out.println("Game State: Letter " + Character.toUpperCase(letter) + " found");
                displayedLetters[i] = letter;
                found = true;
                gui.updateWord(displayedLetters);
            }
        }

        if (found == false) {
            playVoice(getClass().getResource("/resources/bullet.wav"));
            System.out.println("Game State: Letter " + Character.toUpperCase(letter) + " not found");
            score -= 10;
            remainingGuesses--;
            gui.updateScore(score);
            gui.updateHangman(6 - remainingGuesses);
        }

        System.out.println("Game State: " + remainingGuesses + " guesses remaining");
        checkWinLose();
    }

    /*
    * FUNCTION: checkWinLose()
    *
    * Checks to see if the game is in a winning or losing state.
    * The player wins if all letters have been correctly guessed.
    * The player loses if they have made 6 incorrect guesses.
     */
    public void checkWinLose() {
        if (new String(displayedLetters).equals(word)) {
            playVoice(getClass().getResource("/resources/win.wav"));
            System.out.println("Game State: Completed Hangman");
            gui.addScore(score);
            gui.skip(true);
        } else if (remainingGuesses <= 0) {
            playVoice(getClass().getResource("/resources/lose.wav"));
            System.out.println("Game State: Failed Hangman");
            gui.skip(true);
        }
    }
   
    /*
    * FUNCTION: checkWinLose()
    *
    * Returns the score for Hangman.
     */
    public int getScore(){
        return score;
    }
}
