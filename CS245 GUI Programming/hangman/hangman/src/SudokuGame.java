/**
 * *************************************************************
 * file: SudokuGame.java
 * authors: Colin Trotter, Andrew Trang, Jaeseung Lee
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: qtrProject_1.3
 * date last modified: 10/20/2016
 *
 * purpose: Represents a game of Sudoku. Player wins when they have inputted numbers matching the solution.
 *
 ***************************************************************
 */
package hangman;

public class SudokuGame {
    
    private GUI gui;
    private int score;
    
    //0's represent empty cell
    final int[][] initNums = {{8,0,0,4,0,6,0,0,7},
                              {0,0,0,0,0,0,4,0,0},
                              {0,1,0,0,0,0,6,5,0},
                              {5,0,9,0,3,0,7,8,0},
                              {0,0,0,0,7,0,0,0,0},
                              {0,4,8,0,2,0,1,0,3},
                              {0,5,2,0,0,0,0,9,0},
                              {0,0,1,0,0,0,0,0,0},
                              {3,0,0,9,0,2,0,0,5}};
    
    final int[][] solutionNums = {{8,3,5,4,1,6,9,2,7},
                                  {2,9,6,8,5,7,4,3,1},
                                  {4,1,7,2,9,3,6,5,8},
                                  {5,6,9,1,3,4,7,8,2},
                                  {1,2,3,6,7,8,5,4,9},
                                  {7,4,8,5,2,9,1,6,3},
                                  {6,5,2,7,8,1,3,9,4},
                                  {9,8,1,3,4,5,2,7,6},
                                  {3,7,4,9,6,2,8,1,5}};
    
    //Used to keep track of which cells the player has already lost points for.
    //Will not lose points again for same cell being wrong again.
    boolean[][] incorrectNums = new boolean[9][9];
    
    public void start(){
        setInitialNumbers();
        score = 540;
        gui.updateScore(score);
    }
    
    public void setGUI(GUI gui){
        this.gui = gui;
    }
    
    private void setInitialNumbers(){
        for (int i = 0; i < initNums.length; i++){
            for (int j = 0; j < initNums[0].length; j++){
                if (initNums[i][j] != 0){
                   gui.setInitialNumber(i, j, initNums[i][j]);
                }
                else {
                   gui.clearSudokuCell(i, j);
                }
            } 
        }
    }
    
    public boolean checkSolution(){
        boolean allCorrect = true;
        
        for (int i = 0; i < solutionNums.length; i++){
           for (int j = 0; j < solutionNums[0].length; j++){
                if (gui.getSudokuCell(i, j) == solutionNums[i][j]){
                   gui.correctCell(i,j,true);
                }
                else {
                    allCorrect = false;
                    gui.correctCell(i,j,false);
                    if (!incorrectNums[i][j]){
                        score -= 10;
                        gui.updateScore(score);
                        incorrectNums[i][j] = true;
                    }
                }
           } 
        }
        
        //updateScore
        
        return allCorrect;
    }
    
    public void endGame(boolean won){
        if (!won){
            score = 0;
        }
        gui.addScore(score);
        gui.setPanel(7);
    }
    
}
