/**
 * *************************************************************
 * file: ColorGame.java
 * authors: Colin Trotter, Andrew Trang, Jaeseung Lee
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: qtrProject_1.3
 * date last modified: 10/24/2016
 *
 * purpose: Represents a Color Game. The objective is to click the button of the actual color of the text, not the name of the color the
 * text specifies.
 *
 ***************************************************************
 */
package hangman;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class ColorGame {

    private GUI gui;
    final Color[] possibleColors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.MAGENTA};
    final String[] possibleColorNames = {"RED", "BLUE", "YELLOW", "GREEN", "MAGENTA"};
    Color answer;
    int rounds;
    Random rand = new Random();
    ArrayList<Point> invalidPoints = new ArrayList<Point>();
    int score;

    /*
    * FUNCTION: setGUI()
    *
    * Sets the GUI object for this instance of the game.
     */
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    /*
    * FUNCTION: start()
    *
    * Sets the game up for a new game by settting all components of the game to their initial state, 
    * and randomizing the location of each button.
     */
    public void start() {
        score = 0;
        gui.updateScore(score);
        rounds = 5;
        gui.generateColors();
    }

    /*
    * FUNCTION: spawn()
    *
    * Returns a Point where a color button can be placed without overlapping other buttons.
     */
    public Point spawn() {
        boolean validLocation = false;
        Point result = null;
        while (!validLocation) {
            result = new Point(rand.nextInt(545), rand.nextInt(270) + 50);
            if (invalidPoints.isEmpty()) {
                validLocation = true;
            } else {
                validLocation = true;
            }
            for (int i = 0; i < invalidPoints.size(); i++) {
                if (Math.abs(result.x - invalidPoints.get(i).x) < 50 && Math.abs(result.y - invalidPoints.get(i).y) < 50) {
                    validLocation = false;
                    System.out.println("Game State: Collision Found/Fixed");
                    break;
                }
            }
        }
        invalidPoints.add(result);
        return result;
    }

    /*
    * FUNCTION: randomColor()
    *
    * Returns a random Color from the array possibleColors.
     */
    public Color randomColor() {
        int index = rand.nextInt(possibleColorNames.length);
        answer = possibleColors[index];
        return getColor(index);
    }

    /*
    * FUNCTION: randomColorName()
    *
    * Returns a random String from the array possibleColorNames.
     */
    public String randomColorName() {
        int index = rand.nextInt(possibleColorNames.length);
        return possibleColorNames[index];
    }

    /*
    * FUNCTION: getColor()
    *
    * Returns the Color corresponding to the given index in the array possibleColors.
     */
    public Color getColor(int i) {
        return possibleColors[i];
    }
    
    public String getColorName(int i) {
        return possibleColorNames[i];
    }

    /*
    * FUNCTION: endGame()
    *
    * Checks whether there are any remaining rounds.
    * Game should end after 5 rounds.
    */
    public boolean endGame() {
        System.out.println("Game State: " + rounds + " rounds remaining");
        if (rounds > 0) {
            rounds--;
            return false;
        } else {
            gui.addScore(score);
            gui.setPanel(6);
            return true;
        }
    }
    
    /*
    * FUNCTION: getScore()
    *
    * Returns the score for this game.
    */
    public int getScore() {
        return score;
    }
    
    /*
    * FUNCTION: setScore()
    *
    * Sets the score for this game.
    */
    public void setScore(int score) {
        this.score = score;
    }
}
