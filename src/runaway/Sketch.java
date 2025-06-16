/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runaway;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.*;
import java.util.Scanner;

import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.UP;

/**
 * The main game window and controller class.
 * This class handles the game's main menu, level selection, and game state management.
 * It extends PApplet to provide the game's rendering and input handling capabilities.
 *
 * @author linhong
 */
public class Sketch extends PApplet {
    /** The current level instance */
    private Level level;
    /** The current level number (0 for menu) */
    private int currLevel = 0;
    /** User input for level selection */
    private String userInput = "";
    /** Whether the current level has been initialized */
    private boolean levelInited = false;
    /** Whether the continue prompt is active */
    private boolean continueActivated = false;
    /** Whether the level is in prelude state */
    private boolean inPrelude = true;
    /** Whether to show high scores */
    private boolean showScore = false;

    // Button positions
    /** Width of menu buttons */
    private int buttonWidth = 200;
    /** Height of menu buttons */
    private int buttonHeight = 60;
    /** Spacing between menu buttons */
    private int buttonSpacing = 80;
    /** Starting X position of menu buttons */
    private int buttonStartX = 80;
    /** Starting Y position of menu buttons */
    private int buttonStartY = 200;

    /**
     * Sets up the window size and other Processing settings.
     */
    public void settings() {
        size(1200, 800);
    }

    /**
     * Initializes the game state and loads initial resources.
     */
    public void setup() {
        background(255);
        textSize(20);
        level = new Level(this, 0);
        
//      bao = new Bao(this, 100, 100, ""); 
    }

    /**
     * Main game loop. Handles rendering of the current game state.
     */
    public void draw() {
        switch (currLevel) {
            case 0:
                drawMainMenu();
                if (showScore){
                    showHighScore();
                }
                break;
            case 1:
                runLevel(new Level1(this));
                break;
            case 2:
                runLevel(new Level2(this));
                break;
            case 3:
                runLevel(new Level3(this));
                break;
        }
    }
      
    /**
     * Runs a specific level instance.
     * Handles both prelude and gameplay states.
     *
     * @param newLevel The level instance to run
     */
    private void runLevel(Level newLevel) {
        textAlign(TOP, LEFT);
        background(255);
        if (inPrelude) {
            level = newLevel;
            level.loadPrelude();
            levelInited = true;
            continueActivated = true;
        } else {
            level.loadLevel();
        }
    }

    /**
     * Renders the main menu with level selection buttons and high score option.
     */
    private void drawMainMenu() {
        background(255);
        fill(0);
        textAlign(CENTER, CENTER);
        textSize(36);
        text("Choose a Level", width / 2, 100);

        for (int i = 1; i <= 3; i++) {
            int x = buttonStartX + (i - 1) * (buttonWidth + buttonSpacing);
            int y = buttonStartY;
            fill(200);
            rect(x, y, buttonWidth, buttonHeight, 10);
            fill(0);
            textSize(20);
            text("Level " + i, x + buttonWidth / 2, y + buttonHeight / 2);
        }
        
        fill(200);
        rect(80, 400, buttonWidth, buttonHeight, 10);
        fill(0);
        textSize(20);
        text("View HighScores", 80 + buttonWidth / 2, 400+buttonHeight / 2);
                
    }
    
    /**
     * Handles keyboard input for level selection and game control.
     */
    public void keyPressed(){
    
    if (currLevel==0){
        if (keyCode == ENTER){
            currLevel= Integer.parseInt(userInput);
        }   
        else if (keyCode == BACKSPACE) {
            if (userInput.length() > 0) {
                userInput = userInput.substring(0, userInput.length() - 1);
            }
        } 
        else if (keyCode != CODED){
           userInput+=key;
        }
    } 
    
    if (continueActivated && keyCode == ENTER){
        continueActivated =false;
        inPrelude = false;
        level.loadLevel();
    }
    
    if (level != null){
    level.keyPressed();
        
    }
  }
    
    /**
     * Handles mouse input for menu button interaction.
     */
    public void mousePressed() {
        if (currLevel == 0) {
            for (int i = 1; i <= 3; i++) {
                int x = buttonStartX + (i - 1) * (buttonWidth + buttonSpacing);
                int y = buttonStartY;
                if (mouseX >= x && mouseX <= x + buttonWidth &&
                    mouseY >= y && mouseY <= y + buttonHeight) {
                    currLevel = i;
                }
            }
            if (mouseX >= 80 && mouseX <= 80 + buttonWidth &&
                mouseY >= 400 && mouseY <= 400 + buttonHeight) {
                showScore=!showScore;
            }
        }
    }
    
    /**
     * Displays the high scores loaded from score.txt.
     */
    public void showHighScore(){
        try{
            Scanner file = new Scanner(new File("score.txt"));
            int y = 500;
            while(file.hasNext()){
                textAlign(TOP,LEFT);
                text(file.nextLine(), 80, y);
                y+=50;
            }
        } catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * Returns to the main menu and resets game state.
     */
    public void returnToMenu() {
        System.out.println("Returning to main menu");
        currLevel = 0;
        level = new Level(this, 0);
        levelInited = false;
        continueActivated = false;
        inPrelude = true;
        
        // Reset obstacle image for next round
        Obstacle.resetImage();
    }
    
    /**
     * Handles key release events and passes them to the current level.
     */
    @Override
    public void keyReleased() {
        if (level != null) {
            level.keyReleased();
        }
    }
}
