/**
 * *************************************************************
 * file: HighScore.java
 * authors: Colin Trotter, Andrew Trang, Jaeseung Lee
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: qtrProject_1.3
 * date last modified: 10/17/2016
 *
 * purpose: Container class to hold a name and score to represent a high score.
 *
 ***************************************************************
 */
package hangman;

public class HighScore implements Comparable<HighScore> {
    private String name;
    private int score;
    
    public HighScore(String name, int score){
        this.name = name;
        this.score = score;
    }
    
    /*
    * FUNCTION: getName()
    *
    * Returns the initials associated with this high score.
    */
    public String getName(){
        return name;
    }
    
    /*
    * FUNCTION: getScore()
    *
    * Returns the score associated with this high score.
    */
    public int getScore(){
        return score;
    }
    
    /*
    * FUNCTION: compareTo()
    *
    * Compares this instance of HighScore to another HighScore instance.
    */
    public int compareTo(HighScore other){
        if (score < other.getScore()){
            return -1;
        }
        else if (score == other.getScore()){
            return 0;
        }
        else {
            return 1;
        }
    }
    
    /*
    * FUNCTION: toString()
    *
    * Returns a string representation of this instance of HighScore.
    */
    @Override
    public String toString(){
        String result;
        result = getName() + "," + getScore();
        return result;
    }
}
