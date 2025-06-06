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

public class Level1 extends Level {
    private Bao bao;
    private Obstacle[] o = new Obstacle[12];
    private Goal goal;
    private PApplet app;
    private boolean gameOver =false;
    public Level1(PApplet app){
        super(app, 1);
        this.app = app;
        bao = new Bao(app, 100, 100, 5, ""); 
        goal = new Goal(app, 400, 400, "");
        o[0] = new Obstacle(app, 200, 200, "");
        o[1] = new Obstacle(app, 100, 200, "");
        o[2] = new Obstacle(app, 200, 100, "");
        o[3] = new Obstacle(app, 200, 300, "");
    }
    
    @Override
    public void loadLevel(){
//        super.loadLevel();
//        System.out.println("Loading level");
        app.fill(100);
        bao.draw();
        o[0].draw();
        o[1].draw();
        o[2].draw();
        o[3].draw();
        goal.draw();
        drawCollisions();
    }
    
    public void keyPressed(){
        if (app.keyPressed) {
            switch (app.keyCode) {
                case LEFT:
                    bao.move(-1, 0);
                    break;
                case RIGHT:
                    bao.move(1, 0);
                    break;
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
