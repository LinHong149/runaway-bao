/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runaway;

/**
 *
 * @author linhong
 */

import java.io.*;
import java.util.Scanner;
import processing.core.PApplet;


import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.UP;
import static processing.core.PConstants.ENTER;

public class Level3 extends Level {
    private Bao bao;
    private Goal[] g = new Goal[4];
    private PApplet app;
    private boolean gameOver =false;
    private int startTime = -1;
    private int elapsedTime = 0;
    private final int TOTAL_DURATION = 60_000;
    private int goToX = 100, goToY= 100;
    
    public Level3(PApplet app){
        super(app, 3);
        this.app = app;
        bao = new Bao(app, 100, 100, 5, ""); 
        g[0] = new Goal(app, 200, 200, "");
        g[1] = new Goal(app, 200, 200, "");
        g[2] = new Goal(app, 200, 200, "");
        g[3] = new Goal(app, 200, 200, "");
    }
    
    @Override
    public void loadLevel(){
        if (startTime == -1){
            startTime = app.millis();
        }
        
        elapsedTime = app.millis() - startTime;
        if (!gameOver && elapsedTime >= TOTAL_DURATION){
            endScreen();
            return;
                   }
        
        app.fill(100);
        app.text("Time Left: "+Math.max(0, (TOTAL_DURATION-elapsedTime)/1000), 1050, 50);
        
        
        bao.draw();
        g[0].draw();
        g[1].draw();
        g[2].draw();
        g[3].draw();
        mousePressed();
        
        bao.moveTo(goToX, goToY);
//        drawCollisions();
    }
    
    public void keyPressed(){
        if (app.keyPressed) {
          if (gameOver && app.keyCode == ENTER){
              ((Sketch)app).returnToMenu();
          }
      }
    }
    
//    public void drawCollisions(){
//        if (bao.isCollidingWith(goal)){
//            winScreen();
//        }
//        for (Obstacle ob : o){
//            if (ob != null && bao.isCollidingWith(ob)){
//                endScreen();
//            }
//        }
//    }
    
    public void mousePressed(){
        if (app.mousePressed){
            for (Goal goal:g){
                if (goal.isClicked(app.mouseX, app.mouseY)){
                    goToX = app.mouseX;
                    goToY = app.mouseY;
                }
            }
        }
        
        
            
    }
    
    public void winScreen(){
        gameOver = true;
        app.background(255);
        app.fill(100);
        
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        
        app.textSize(40);
        app.text("You Win!", app.width/2, 100);
        
        app.textSize(20);
        app.text("After eating the dumpling, Bao is satisfied and goes back to bed.", app.width/2, 200);
        
        app.textSize(20);
        app.text("Press Enter to exit", app.width/2, 800-100);
    }
    
    
    public void endScreen(){
        gameOver = true;
        app.background(255);
        app.fill(100);
        
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.textSize(40);
        app.text("Oh no!", app.width/2, 100);
        
        app.textSize(20);
        app.text("How clumsy Bao is! Mom woke up and is coming down now...", app.width/2, 200);
        
        app.textSize(20);
        app.text("Press Enter to exit", app.width/2, 800-100);
    }
}
