/**
 * *************************************************************
 * file: HighScoreManager.java
 * authors: Colin Trotter, Andrew Trang, Jaeseung Lee
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: qtrProject_1.3
 * date last modified: 10/17/2016
 *
 * purpose: Class to handle saving and loading high scores from a file.
 *
 ***************************************************************
 */
package hangman;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HighScoreManager {
    private URL path;
    private File f;
    private final String errorMessage = "Error: highscores file not found.";
    private GUI gui;
    private ArrayList<HighScore> currentScores = new ArrayList<>();
    
    

    public HighScoreManager(String localFilePath){
        path = getClass().getResource(localFilePath);
        f = new File(path.getFile());
        readScores();
        sortScores();
    }
    
    
    /*
    * FUNCTION: setGUI()
    *
    * Sets the GUI object for this instance of HighScoreManager.
    */
    public void setGUI(GUI gui){
        this.gui = gui;
    }
    
    /*
    * FUNCTION: readScores()
    *
    * Reads the scores from the given file, into the currentScores ArrayList.
    */
    private void readScores(){
        try {
            Scanner read = new Scanner (f);
            read.useDelimiter(",");
            while (read.hasNext()){
                currentScores.add(new HighScore(read.next(), Integer.parseInt(read.next())));
            }
            read.close();
        } catch(FileNotFoundException e){
            System.out.println(errorMessage);
        }
    }
    
    
    /*
    * FUNCTION: sortScores()
    *
    * Sorts the HighScores in currentScores in descending order.
    */
    private void sortScores(){
        currentScores.sort(Collections.reverseOrder());
    }
    
    
    /*
    * FUNCTION: writeScores()
    *
    * Writes the scores from currentScores ArrayList, into the File f.
    */
    private void writeScores(){      
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "utf-8"))) {
            for (HighScore h : currentScores){
                writer.write(h.toString() + ",");
            }
        } catch (IOException e){
            System.out.println(errorMessage);
        }  
    }
    
    /*
    * FUNCTION: addHighScore()
    *
    * Adds a new high score to the currentScores ArrayList, places it in the correct position, sorts, and saves the list to the file.
    */
    public void addHighScore(HighScore score){
        currentScores.add(score);
        sortScores();
        currentScores.remove(currentScores.size() -1); //remove lowest score
        writeScores();
        updateScores();
    }
    
    /*
    * FUNCTION: isHighScore()
    *
    * Returns if the given score is high enough to make it onto the high scores list.
    */
    public boolean isHighScore(int score){
        boolean result = false;
        
        for (HighScore h : currentScores){
            if ((score) > h.getScore()){
                result = true;
            }
        }
        
        return result;
    }
    
    /*
    * FUNCTION: updateScores()
    *
    * Sends the High Scores to the GUI to be displayed on the high scores panel.
    */
    public void updateScores(){
        HighScore[] scores = currentScores.toArray(new HighScore[currentScores.size()]);
        gui.updateHighScores(scores);
    }

}
