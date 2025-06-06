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

public class Level2 extends Level {
    private Bao bao;
    private Obstacle[] o = new Obstacle[12];
    private Goal goal;
    private PApplet app;
    private boolean gameOver =false;
    private int startTime = -1;
    private int elapsedTime = 0;
    private final int WIN_DURATION = 60_000;
    
    public Level2(PApplet app){
        super(app, 2);
        this.app = app;
        bao = new Bao(app, 100, 100, 8 , ""); 
        o[0] = new Obstacle(app, 1800, 200, "");
        o[1] = new Obstacle(app, 1800, 200, "");
        o[2] = new Obstacle(app, 1800, 100, "");
        o[3] = new Obstacle(app, 1800, 300, "");
        goal = new Goal(app, 1150, 400, "");
    }
    
    @Override
    public void loadLevel(){
        if (startTime == -1){
            startTime = app.millis();
        }
        
        elapsedTime = app.millis() - startTime;
        if (!gameOver && elapsedTime >= WIN_DURATION){
            winScreen();
            return;
                   }
//        super.loadLevel();
//        System.out.println("Loading level");
        app.fill(100);
        app.textSize(20);
        app.text("Time Left: "+Math.max(0, (WIN_DURATION-elapsedTime)/1000), 1050, 50);
        
        
        bao.draw();
        if (!gameOver){
            
        o[0].fly(8);
        o[1].fly(6);
        o[2].fly(5);
        o[3].fly(7);
        
        }
        drawCollisions();
    }
    
    public void keyPressed(){
        if (app.keyPressed) {
            switch (app.keyCode) {
                case UP:
                    bao.move(0, -1);
                    break;
                case DOWN:
                    bao.move(0, 1);
                    break;
                default:
                    bao.move(0, 0);
                    break;
            }
          if (gameOver && app.keyCode == ENTER){
              ((Sketch)app).returnToMenu();
          }
      }
    }
    
    public void drawCollisions(){
        if (bao.isCollidingWith(goal)){
            winScreen();
        }
        for (Obstacle ob : o){
            if (ob != null && bao.isCollidingWith(ob)){
                endScreen();
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
        app.text("Phew, Bao escaped for another day!", app.width/2, 200);
        
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
        app.text("Bao got slippered", app.width/2, 200);
        
        app.textSize(20);
        app.text("Press Enter to exit", app.width/2, 800-100);
    }
}
