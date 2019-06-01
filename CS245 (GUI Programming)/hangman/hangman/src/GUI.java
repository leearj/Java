/**
 * *************************************************************
 * file: GUI.java
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

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Date;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener, KeyListener {

    final Panel[] p = new Panel[8]; // GUI Panels
    private final Sound s = new Sound(); // Plays Audio
    private HangmanGame hangmanGame; // HangmanGame object
    private ColorGame colorGame; // HangmanGame object
    private HighScoreManager hs; // HighScoreManger object
    private SudokuGame sudokuGame;
    final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); // Date Format
    private Timer t; // GUI Clock

    public JButton play; // Starts the game
    private JButton skip; // Skips Current word
    private JButton hScores; // Displays High Scores
    private JButton credits; // Displays Credits
    private JButton back1; // Returns to Home
    private JButton back2; // Returns to Home
    private JButton end; // Returns to Home
    private JButton sudokuSubmit; // Submits results for Sudoku
    private JButton sudokuQuit; // Quits Sudoku
    private final JButton[] alphabet = new JButton[26]; // Guess letters A-Z
    private final JButton[] colorButtons = new JButton[5]; // Colored buttons for Color Game
    private JLabel haTitle; // Hangman
    private JLabel coTitle; // Color Matching
    private JLabel suTitle; //Sudoku
    private JLabel time1; // Current Time [Hangman]
    private JLabel time2; // Current Time [Color Matching]
    private JLabel time3; // Current Time [Sudoku]
    private JLabel splashText1; //Team Name
    private JLabel splashText2; //Project Name
    private JLabel logo; // Home Logo
    private JLabel sBG; // Splash Background
    private JLabel pBG; // Hangman Background
    private JLabel coBG; // Color Matching Background
    private JLabel suBG; // Sudoku Background
    private JLabel hBG; // High Scores Background
    private JLabel cBG; // Credits Background
    private JLabel eBG; // End Panel Background
    private JLabel hTitle; // High Scores
    private final JLabel hName[] = new JLabel[5]; // High Score Names
    private JLabel cTitle; // Credits
    private JLabel cName1; // Credits Name 1
    private JLabel cName2; // Credits Name 2
    private JLabel cName3; // Credits Name 3
    private JLabel word; // Word to Guess
    private int totalScore; // Score Value
    private JLabel score1; // Current Score [Hangman]
    private JLabel score2; // Current Score [Color Matching]
    private JLabel score3; // Current Score [Sudoku]
    private JLabel score4; // Final Score
    private JLabel gallows; // Hangman Gallows
    private final JLabel[] hangman = new JLabel[6]; // Hangman Body Parts
    private JLabel finalScore; // Final Score
    private JLabel colorText; // Random Color Game Answer
    private JTextField initials; // Textfield to enter initials for high score
    private JLabel initialsLabel; // Label telling the player to enter their initials
    private JTextField[][] sudokuNumbers; // Textfields to enter numbers into Sudoku game

    /*
    * FUNCTION: init()
    *
    * Creates every panel of the game.
    *
    *   p[index]
    *   =========
    *   0 - Home
    *   1 - Hangman
    *   2 - High Scores
    *   3 - Credits
    *   4 - Splash Panel
    *   5 - Color Matching
    *   6 - Sudoku
    *   7 - End Panel
     */
    public void init() {
        s.playVoice(getClass().getResource("/resources/intro.wav"));
       addKeyListener(this);

        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Operation Hangman");
        setSize(600, 400);
        setLocationRelativeTo(null);

        /////////////////////////////////////////////
        // Home
        // =========================================
        // Buttons: Play, High Scores, Credits
        // Labels: Logo
        /////////////////////////////////////////////
        p[0] = new Panel();
        p[0].setFocusable(true);
        
        play = new JButton("PLAY");
        hScores = new JButton("HIGH SCORES");
        credits = new JButton("CREDITS");
        logo = new JLabel();

        p[0].setLayout(null);
        p[0].setBackground(Color.BLACK);
        p[0].setBounds(0, 0, 600, 371);

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/home.png")));
        logo.setBounds(0, 0, 600, 400);

        buttonLayout(play, 455, 270, 120, 20, 0);
        buttonLayout(hScores, 455, 300, 120, 20, 0);
        buttonLayout(credits, 455, 330, 120, 20, 0);

        add(p[0]);
        p[0].add(logo);

        /////////////////////////////////////////////
        // Hangman
        // =========================================
        // Buttons: Skip, Alphabet
        // Labels: Ptitle, Time, Word, Score
        /////////////////////////////////////////////
        p[1] = new Panel();
        p[1].setFocusable(true);
        
        pBG = new JLabel();
        haTitle = new JLabel("HANGMAN");
        skip = new JButton("SKIP");
        time1 = new JLabel();
        word = new JLabel();
        score1 = new JLabel("Score: 100");
        gallows = new JLabel();

        for (int i = 0; i < hangman.length; i++) {
            hangman[i] = new JLabel();
            hangman[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/hangman" + i + ".png")));
            hangman[i].setBounds(191, 20, 218, 252);
            p[1].add(hangman[i]);
        }
        for (int i = 0; i < alphabet.length; i++) {
            int j = i;
            alphabet[i] = new JButton(Character.toString((char) (65 + i)));
            buttonLayout(alphabet[i], 11 + i * (22), 340, 20, 20, 1);
        }

        pBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bg1.png")));
        gallows.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/gallows.png")));

        p[1].setLayout(null);
        haTitle.setForeground(Color.WHITE);
        time1.setForeground(Color.WHITE);
        score1.setForeground(Color.WHITE);

        haTitle.setFont(new Font("SANS_SERIF", Font.BOLD, 25));
        time1.setFont(new Font("SANS_SERIF", Font.BOLD, 12));
        word.setFont(new Font("SANS_SERIF", Font.BOLD, 30));
        score1.setFont(new Font("SANS_SERIF", Font.BOLD, 15));

        p[1].setBounds(0, 0, 600, 371);
        haTitle.setBounds(10, 5, 150, 30);
        time1.setBounds(462, 5, 150, 20);
        pBG.setBounds(0, 0, 600, 400);
        word.setBounds(0, 280, 600, 40);
        score1.setBounds(10, 30, 150, 30);
        gallows.setBounds(191, 20, 218, 252);
        buttonLayout(skip, 490, 30, 60, 20, 1);

        word.setHorizontalAlignment(SwingConstants.CENTER);

        p[1].add(haTitle);
        p[1].add(gallows);
        p[1].add(time1);
        p[1].add(word);
        p[1].add(score1);
        p[1].add(pBG);
        add(p[1]);

        /////////////////////////////////////////////
        // High Scores
        // =========================================
        // Buttons: Back
        // Labels: Title, Names[1-5]
        /////////////////////////////////////////////
        p[2] = new Panel();
        p[2].setFocusable(true);
        
        hBG = new JLabel();
        back1 = new JButton("BACK");
        hTitle = new JLabel("HIGH SCORES");
        hBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bg2.png")));

        p[2].setLayout(null);
        hTitle.setForeground(Color.WHITE);
        hTitle.setFont(new Font("SANS_SERIF", Font.BOLD, 30));

        p[2].setBounds(0, 0, 600, 371);
        hTitle.setBounds(205, 10, 220, 50);
        hBG.setBounds(0, 0, 600, 400);
        buttonLayout(back1, 20, 330, 70, 20, 2);

        for (int i = 0; i < hName.length; i++) {
            hName[i] = new JLabel("AAA .... 000");
            hName[i].setFont(new Font("CONSOLAS", Font.BOLD, 14));
            hName[i].setForeground(Color.WHITE);
            hName[i].setBounds(255, 75 + i * 20, 100, 25);
            p[2].add(hName[i]);
        }
        hs.updateScores();

        p[2].add(hTitle);
        p[2].add(hBG);
        add(p[2]);

        /////////////////////////////////////////////
        // Credits
        // =========================================
        // Buttons: Back
        // Labels: Title, Names[1-3]
        /////////////////////////////////////////////
        p[3] = new Panel();
        p[3].setFocusable(true);
        
        cBG = new JLabel();
        back2 = new JButton("BACK");
        cTitle = new JLabel("CREDITS");
        cName1 = new JLabel("Andrew Trang, 009478404");
        cName2 = new JLabel("Jaeseung Lee, 009239191");
        cName3 = new JLabel("Colin Trotter, 009815195");

        cBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bg2.png")));

        cTitle.setFont(new Font("SANS_SERIF", Font.BOLD, 30));
        cName1.setFont(new Font("SANS_SERIF", Font.BOLD, 14));
        cName2.setFont(new Font("SANS_SERIF", Font.BOLD, 14));
        cName3.setFont(new Font("SANS_SERIF", Font.BOLD, 14));

        p[3].setLayout(null);
        cTitle.setForeground(Color.WHITE);
        cName1.setForeground(Color.WHITE);
        cName2.setForeground(Color.WHITE);
        cName3.setForeground(Color.WHITE);

        p[3].setBounds(0, 0, 600, 371);
        cTitle.setBounds(240, 24, 150, 25);
        cName1.setBounds(200, 75, 180, 25);
        cName2.setBounds(200, 95, 180, 25);
        cName3.setBounds(200, 115, 180, 25);
        cBG.setBounds(0, 0, 600, 400);
        buttonLayout(back2, 20, 330, 70, 20, 3);

        p[3].add(cTitle);
        p[3].add(cName1);
        p[3].add(cName2);
        p[3].add(cName3);
        p[3].add(cBG);
        add(p[3]);

        /////////////////////////////////////////////
        // Splash Screen
        // =========================================
        // Labels: Group Name, Project Name
        /////////////////////////////////////////////
        p[4] = new Panel();
        p[4].setFocusable(true);
        
        sBG = new JLabel();
        splashText1 = new JLabel("Team Overwatch");
        splashText2 = new JLabel("CS245 F2K16 Project");

        sBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/splash.png")));

        splashText1.setFont(new Font("SANS_SERIF", Font.BOLD, 30));
        splashText2.setFont(new Font("SANS_SERIF", Font.BOLD, 18));

        p[4].setLayout(null);
        p[4].setBackground(Color.BLACK);
        splashText1.setForeground(Color.WHITE);
        splashText2.setForeground(Color.WHITE);

        p[4].setBounds(0, 0, 600, 371);
        splashText1.setBounds(175, 20, 250, 25);
        splashText2.setBounds(208, 50, 250, 25);
        sBG.setBounds(0, 0, 600, 400);

        p[4].add(splashText1);
        p[4].add(splashText2);
        p[4].add(sBG);
        add(p[4]);

        /////////////////////////////////////////////
        // Color Matching
        // =========================================
        // Buttons: colorButtons[]
        // Labels: colorText, Time, Score, Title
        /////////////////////////////////////////////
        p[5] = new Panel();
        p[5].setFocusable(true);
        
        colorText = new JLabel();
        time2 = new JLabel();
        score2 = new JLabel();
        coBG = new JLabel();
        coTitle = new JLabel("COLOR MATCH");

        coTitle.setForeground(Color.WHITE);
        time2.setForeground(Color.WHITE);
        score2.setForeground(Color.WHITE);
        p[5].setLayout(null);
        p[5].setBounds(0, 0, 600, 371);

        coBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bg1.png")));
        colorText.setHorizontalAlignment(SwingConstants.CENTER);
        coTitle.setFont(new Font("SANS_SERIF", Font.BOLD, 25));
        colorText.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        score2.setFont(new Font("SANS_SERIF", Font.BOLD, 15));

        score2.setBounds(10, 30, 150, 30);
        coBG.setBounds(0, 0, 600, 400);
        colorText.setBounds(200, 20, 200, 40);
        time2.setBounds(462, 5, 150, 20);
        coTitle.setBounds(10, 5, 250, 30);

        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i] = new JButton();
            colorButtons[i].setBorder(null);
            colorButtons[i].setSize(50, 50);
            colorButtons[i].addActionListener(this);
            colorButtons[i].setBackground(colorGame.getColor(i));
            p[5].add(colorButtons[i]);
        }

        p[5].add(colorText);
        p[5].add(score2);
        p[5].add(time2);
        p[5].add(coTitle);
        p[5].add(coBG);
        add(p[5]);

        /////////////////////////////////////////////
        // Sudoku
        // =========================================
        // Buttons: 
        // Labels: 
        /////////////////////////////////////////////
        p[6] = new Panel();
        p[6].setFocusable(true);
        
        p[6].setLayout(null);
        p[6].setBounds(0, 0, 600, 371);
        sudokuSubmit = new JButton("Submit");
        sudokuQuit = new JButton("Quit");
        suBG = new JLabel();
        time3 = new JLabel();
        score3 = new JLabel();
        suTitle = new JLabel("SUDOKU");
        sudokuNumbers = new JTextField[9][9];
        int numSize = 35;
        for (int i = 0; i < sudokuNumbers.length; i++){
            for (int j = 0; j < sudokuNumbers[0].length; j++){
                sudokuNumbers[i][j] = new JTextField();
                sudokuNumbers[i][j].setBounds(143 + (numSize)*i, numSize + (numSize)*j, numSize, numSize);
                sudokuNumbers[i][j].setFont(new Font("SANS_SERIF", Font.BOLD, 20));
                sudokuNumbers[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                sudokuNumbers[i][j].setDocument(new SudokuDigitDocument());
                sudokuNumbers[i][j].setBorder(BorderFactory.createDashedBorder(Color.black));
                p[6].add(sudokuNumbers[i][j]);
            }
        }
        
        buttonLayout(sudokuSubmit, 20, 300, 100, 50, 6);
        buttonLayout(sudokuQuit, 480, 300, 100, 50, 6);
     
        suBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bg1.png")));
        score3.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
        suTitle.setFont(new Font("SANS_SERIF", Font.BOLD, 25));

        time3.setForeground(Color.WHITE);
        suTitle.setForeground(Color.WHITE);
        score3.setForeground(Color.WHITE);

        score3.setBounds(10, 30, 150, 30);
        time3.setBounds(462, 5, 150, 20);
        suBG.setBounds(0, 0, 600, 400);
        suTitle.setBounds(10, 5, 250, 30);

        p[6].add(score3);
        p[6].add(time3);
        p[6].add(suTitle);
        p[6].add(suBG);
        add(p[6]);

        /////////////////////////////////////////////
        // End
        // =========================================
        // Buttons: End
        // Labels: Score4, Winstate
        /////////////////////////////////////////////
        p[7] = new Panel();
        p[7].setFocusable(true);
        
        eBG = new JLabel();
        end = new JButton("END");
        finalScore = new JLabel("FINAL SCORE");
        score4 = new JLabel();
        
        initials = new JTextField();
        initials.setFont(new Font("SANS_SERIF", Font.BOLD, 30));
        initials.setBounds(350, 200, 100, 40);
        initials.setHorizontalAlignment(SwingConstants.CENTER);
        initials.setVisible(false);
        initials.setDocument(new LengthRestrictedDocument(3));
        
        initialsLabel = new JLabel("Enter initials:");
        initialsLabel.setFont(new Font("SANS_SERIF", Font.BOLD, 30));
        initialsLabel.setBounds(150, 200, 200, 40);
        initialsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        initialsLabel.setVisible(false);

        eBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bg1.png")));

        p[7].setLayout(null);
        p[7].setBounds(0, 0, 600, 371);

        finalScore.setFont(new Font("SANS_SERIF", Font.BOLD, 40));
        score4.setFont(new Font("SANS_SERIF", Font.BOLD, 30));

        eBG.setBounds(0, 0, 600, 400);
        finalScore.setBounds(165, 50, 270, 40);
        score4.setBounds(200, 150, 200, 40);
        buttonLayout(end, 250, 250, 100, 30, 7);

        finalScore.setHorizontalAlignment(SwingConstants.CENTER);
        score4.setHorizontalAlignment(SwingConstants.CENTER);

        p[7].add(finalScore);
        p[7].add(score4);
        p[7].add(initials);
        p[7].add(initialsLabel);
        p[7].add(eBG);
        add(p[7]);

        setPanel(4);
        setVisible(true);
        try {
            Thread.sleep(3000);
            setPanel(0);
            s.playBGM(getClass().getResource("/resources/bgm2.wav"));
        } catch (InterruptedException e) {
        }

        t = new Timer(500, (ActionEvent e) -> {
            time1.setText(dateFormat.format(new Date()));
            time2.setText(dateFormat.format(new Date()));
            time3.setText(dateFormat.format(new Date()));
        });
        t.start();
        
        /********** Implementing ToolTips ***************/
           play.setToolTipText("Starts the game.");
           skip.setToolTipText("Skips the Hangman game.");
           hScores.setToolTipText("Display High Scores.");
           credits.setToolTipText("Display Creidts.");
           back1.setToolTipText("Returns to Home.");
           back2.setToolTipText("Returns to Home.");
           end.setToolTipText("Returns to Home.");
           sudokuSubmit.setToolTipText("Submit results for Sudoku.");
           sudokuQuit.setToolTipText("Quits Sudoku.");
           for (JButton b:alphabet){
               b.setToolTipText(b.getText());
           }
           for (int i = 0; i < colorButtons.length; i++){
               colorButtons[i].setToolTipText(colorGame.getColorName(i));
           }
        /*********************************************/   
        
        
        /********** Key Bindings ************************/
 
       // component.getInputMap().put(KeyStroke.getKeyStroke("Esc"), "Closing the program.");
     //  component.getActionMap().put("doSomething    ", anAction);
       // KeyStroke.getKeyStroke("F1 ");
         /*********************************************/
    }

    /*
    * FUNCTION: actionPerformed()
    *
    * Handles each button's event. Atm, Only the back button is done.
    * Each button also prints its source in the console for debugging purposes.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == play) {
            totalScore = 0;
            updateScore(totalScore);
            setPanel(1);
            hangmanGame.start();
        } else if (ae.getSource() == hScores) {
            setPanel(2);
        } else if (ae.getSource() == credits) {
            setPanel(3);
        } else if (ae.getSource() == back1 || ae.getSource() == back2) {
            setPanel(0);
        } else if (ae.getSource() == end) {
            if (initials.isVisible()){
                hs.addHighScore(new HighScore(String.format("%1$-" + 3 + "s", initials.getText()).toUpperCase(), totalScore));
                initials.setVisible(false);
                initialsLabel.setVisible(false);
            }
            setPanel(0);
        } else if (ae.getSource() == skip) {
            skip(false);
        } else if (p[1].isVisible()) {
            for (JButton btn : alphabet) {
                if (btn.equals(ae.getSource())) {
                    String letter = btn.getText();
                    hangmanGame.guessLetter(letter);
                    btn.setEnabled(false);
                    btn.setBackground(new Color(220, 220, 220));
                    break;
                }
            }
        } else if (p[5].isVisible()) {
            for (JButton btn : colorButtons) {
                if (ae.getSource() == btn) {
                    if (btn.getBackground() == colorGame.answer) {
                        colorGame.setScore(colorGame.getScore() + 100);
                        System.out.println("Game State: Correct color selected");
                        updateScore(colorGame.getScore());
                    } else {
                        System.out.println("Game State: Incorrect color selected");
                    }
                    generateColors();
                }
            }
        } else if (ae.getSource() == sudokuSubmit){
            if (sudokuGame.checkSolution()){
                sudokuGame.endGame(true);
            }
            else{
                JOptionPane.showMessageDialog(this, "Incorrect Solution", "Incorrect", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == sudokuQuit){
            sudokuGame.endGame(false);
        }
    }
    //==========================================================================

    /*
    * FUNCTION: setHangmanGame()
    *
    * Sets the HangmanGame object for this instance of the GUI.
     */
    public void setHangmanGame(HangmanGame game) {
        hangmanGame = game;
    }

    /*
    * FUNCTION: updateWord()
    *
    * Updates the displayed letters of the word to represent what has been correctly guessed so far.
     */
    public void updateWord(char[] letters) {
        String newWord = "";
        for (char letter : letters) {
            newWord = newWord + letter + " ";
        }
        word.setText(newWord);
    }
    
    /*
    * FUNCTION: updateHighScores()
    *
    * Updates the lables on the high scores panel.
     */
    public void updateHighScores(HighScore[] scores){
        for (int i = 0; i < scores.length; i++){
            hName[i].setText(scores[i].getName() + "...." + scores[i].getScore());
        }
    }

    /*
    * FUNCTION: resetAlphabet()
    *
    * Resets all the alphabet buttons to be enabled and ready for a new game.
    */
    public void resetAlphabet() {
        for (JButton btn : alphabet) {
            btn.setEnabled(true);
            btn.setBackground(new Color(98, 190, 253));
        }
    }

    /*
    * FUNCTION: updateScore()
    *
    * Updates the score to represent the current score in the HangmanGame
     */
    public void updateScore(int score) {
        score1.setText("Score: " + score);
        score2.setText("Score: " + score);
        score3.setText("Score: " + score);
        score4.setText("Score: " + score);
    }

    /*
    * FUNCTION: updateHangman()
    *
    * Updates the displahed body parts of the hangman to represent how many incorrect guesses have been made.
     */
    public void updateHangman(int numBodyParts) {
        for (JLabel label : hangman) {
            label.setVisible(false);
        }
        for (int i = 0; i < numBodyParts; i++) {
            hangman[i].setVisible(true);
        }
    }

    //==========================================================================
    /*
    * FUNCTION: setColorGame()
    *
    * Sets the ColorGame object for this instance of the GUI.
     */
    public void setColorGame(ColorGame game) {
        colorGame = game;
    }
    
    //==========================================================================
    /*
    * FUNCTION: setColorGame()
    *
    * Sets the SudokuGame object for this instance of the GUI.
     */
    public void setSudokuGame(SudokuGame game){
        sudokuGame = game;
    }
    
    /*
    * FUNCTION: setHighScoreManager()
    *
    * Sets the HighScoreManager object for this instance of the GUI.
     */
    public void setHighScoreManager(HighScoreManager scores){
        hs = scores;
    }

    /*
    * FUNCTION: generateColors()
    *
    * Places color buttons at random positions.
     */
    public void generateColors() {
        if (!colorGame.endGame()) {
            colorGame.invalidPoints.clear();
            colorText.setText(colorGame.randomColorName());
            colorText.setForeground(colorGame.randomColor());

            for (JButton colorButton : colorButtons) {
                colorButton.setLocation(colorGame.spawn());
            }
        }
    }
    
    /*
    * FUNCTION: getSudokuCell()
    *
    * Returns the number in the cell represented by [i][j]. If blank, returns 0.
    */
    public int getSudokuCell(int j, int i){
        if (sudokuNumbers[i][j].getText().equals("")){
            return 0;
        }
        
        return Integer.parseInt(sudokuNumbers[i][j].getText());
    }
    
    /*
    * FUNCTION: clearSudokuCell()
    *
    * Clears the cell represented by [i][j].
    */
    public void clearSudokuCell(int j, int i){
        sudokuNumbers[i][j].setText("");
        sudokuNumbers[i][j].setBackground(Color.WHITE);
    }
    
    /*
    * FUNCTION: correctCell()
    *
    * Colors the specified cell white if it's correct, and red if it is incorrect.
    */
    public void correctCell(int j, int i, boolean correct){
        if (sudokuNumbers[i][j].isEditable()){
            if (correct){
                sudokuNumbers[i][j].setBackground(Color.WHITE);
            }
            else {
                sudokuNumbers[i][j].setBackground(Color.RED);
            }
        }
    }

    //==========================================================================

    /*
    * FUNCTION: setPanel()
    *
    * Hides all panels and sets to the desired panel.
     */
    public void setPanel(int focus) {
        System.out.print("Panel: ");
        switch (focus) {
            case 0:
                System.out.println("Home");
                break;
            case 1:
                System.out.println("Hangman");
                break;
            case 2:
                System.out.println("High Scores");
                break;
            case 3:
                System.out.println("Credits");
                break;
            case 4:
                System.out.println("Splash");
                break;
            case 5:
                System.out.println("Color Matching");
                break;
            case 6:
                System.out.println("Sudoku");
                sudokuGame.start();
                break;
            case 7:
                updateScore(totalScore);
                if (hs.isHighScore(totalScore)){
                    initials.setVisible(true);
                    initialsLabel.setVisible(true);
                }
                System.out.println("End");
                break;
            default:
                System.out.println("Invalid Panel");
        }

        for (Panel p1 : p) {
            p1.setVisible(false);
        }
        p[focus].setVisible(true);
        requestFocusInWindow();
    }

    /*
    * FUNCTION: skip()
    *
    * Skips the current word and moves to the Color Game.
    */
    public void skip(boolean completed) {
        if (!completed) {
            s.playVoice(getClass().getResource("/resources/skip.wav"));
            //JOptionPane.showMessageDialog(null, "How dare you skip !!   :'<");
            System.out.println("Game State: Skipped Hangman");
        }
        setPanel(5);
        colorGame.start();
    }
    
    public void setInitialNumber(int j, int i, int initNum){
        sudokuNumbers[i][j].setText(String.valueOf(initNum));
        sudokuNumbers[i][j].setEditable(false);
    }
    
    public void addScore(int score){
        totalScore+=score;
    }

    //==========================================================================
    /*
    * FUNCTION: buttonLayout()
    *
    * Modifies a button with the specified parameters.
     */
    private void buttonLayout(JButton b, int posX, int posY, int length, int height, int panel) {
        b.setBounds(posX, posY, length, height);
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setVerticalTextPosition(JButton.CENTER);
        b.setMargin(new Insets(0, 0, 0, 0));
        b.setBackground(new Color(98, 190, 253));
        b.setForeground(Color.WHITE);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
        b.setFont(new Font("SANS_SERIF", Font.BOLD, 12));

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(b, new Color(79, 184, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(b, new Color(98, 190, 253));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setBackground(b, new Color(251, 150, 82));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setBackground(b, new Color(251, 150, 82));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                setBackground(b, new Color(98, 190, 253));
            }

            void setBackground(JButton b, Color c) {
                if (b.isEnabled()) {
                    b.setBackground(c);
                }
            }
        ;
        });
        b.addActionListener(this);
        p[panel].add(b);
    }
    
    //When the key is pressed
    public void keyPressed (KeyEvent e){
       int keyCode = e.getKeyCode();
           if(keyCode == KeyEvent.VK_ESCAPE){
               System.exit(0);
               
               
           }else if(keyCode == KeyEvent.VK_F1){
               JOptionPane.showMessageDialog(null, "Andrew Trang, 009478404\nJaeseung Lee, 009239191\nColin Trotter,    009815195", "Help", JOptionPane.PLAIN_MESSAGE);
           }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
