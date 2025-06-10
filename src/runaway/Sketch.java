/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runaway;
import processing.core.PApplet;
import processing.core.PImage;

import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.UP;

/**
 *
 * @author linhong
 */
public class Sketch extends PApplet{
    private Level level;
    private int currLevel = 0;
    private String userInput = "";
    private boolean levelInited = false;
    private boolean continueActivated = false;
    private boolean inPrelude = true;

    // Button positions
    private int buttonWidth = 200;
    private int buttonHeight = 60;
    private int buttonSpacing = 80;
    private int buttonStartX = 120;
    private int buttonStartY = 200;

    public void settings() {
      size(1200,800);
    }

    public void setup() {
      background(255);
      textSize(20);
      level = new Level(this,0);
      
//      bao = new Bao(this, 100, 100, ""); 
    }

    public void draw() {
        switch (currLevel) {
            case 0:
                drawMainMenu();
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
            case 4:
                runLevel(new Level4(this));
                break;
        }
    }
      
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
        private void drawMainMenu() {
            background(255);
            fill(0);
            textAlign(CENTER, CENTER);
            textSize(36);
            text("Choose a Level", width / 2, 100);

            for (int i = 1; i <= 4; i++) {
                int x = buttonStartX + (i - 1) * (buttonWidth + buttonSpacing);
                int y = buttonStartY;
                fill(200);
                rect(x, y, buttonWidth, buttonHeight, 10);
                fill(0);
                textSize(20);
                text("Level " + i, x + buttonWidth / 2, y + buttonHeight / 2);
            }
        }
    
    
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
    
    public void mousePressed() {
        if (currLevel == 0) {
            for (int i = 1; i <= 4; i++) {
                int x = buttonStartX + (i - 1) * (buttonWidth + buttonSpacing);
                int y = buttonStartY;
                if (mouseX >= x && mouseX <= x + buttonWidth &&
                    mouseY >= y && mouseY <= y + buttonHeight) {
                    currLevel = i;
                }
            }
        }
    }
    
   public void returnToMenu() {
        System.out.println("Returning to main menu");
        currLevel = 0;
        level = new Level(this, 0);
        levelInited = false;
        continueActivated = false;
        inPrelude = true;
    }
    
}
