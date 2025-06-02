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
    private Bao bao; 
    private int currLevel = 0;
    private boolean showInfo = false;
    private String userInput = "";
    private Level level;

    public void settings() {
      size(400, 400);
    }

    public void setup() {
      background(255);
      textSize(20);
      level = new Level(0);
      
      bao = new Bao(this, 100, 100, ""); 
    }

    public void draw() {
      switch (currLevel) {
        case 0:
            System.out.println("0");
            background(255);
            fill(0);
            text("Enter text: ", 20, 50);
            text(userInput, 20, 100);
            break;
        case 1:
            System.out.println("1");
            background(255);
            level.loadPrelude();
            break;
      }
      
      
//      Select Levels
        

//      bao.draw();


//      if (keyPressed) {
//          if (keyCode == LEFT) {
//            bao.move(-5, 0);
//          } else if (keyCode == RIGHT) {
//            bao.move(5, 0);
//          } else if (keyCode == UP) {
//            bao.move(0, -5);
//          } else if (keyCode == DOWN) {
//            bao.move(0, 5);
//          } else {
//            bao.move(0, 0);
//          }
//      }
//      bao.draw();
//      drawCollisions();
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
  }
//
//    public void drawCollisions(){
//        if (bao.isCollidingWith(car2)){
//            fill(255, 0,0);
//            this.text("ouch", car1.x, car1.y);
//            this.text("ouch", car2.x, car2.y);
//        }
//    }
//

//    public void mousePressed(){
//        if (car1.isClicked(mouseX, mouseY)){
//            showInfo = !showInfo;
//        } else{
//            showInfo = false;
//        }
//    }
    
    
}
