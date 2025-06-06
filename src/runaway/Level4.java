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
import processing.core.PGraphics;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.Arrays;


import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.UP;
import static processing.core.PConstants.ENTER;

public class Level4 extends Level {
    private Bao bao;
    private Obstacle[] o = new Obstacle[4];
    private PApplet app;
    private int speed = 5;
    private boolean gameOver =false;
    private int startTime = -1;
    private int elapsedTime = 0;
    private final int TOTAL_DURATION = 60_000;
    private int goToX = 100, goToY= 100;
    private ArrayList<Obstacle> toDisplay;
    PGraphics maskLayer;
    private int confidenceL = 200;
    
    public Level4(PApplet app){
        super(app, 4);
        this.app = app;
        bao = new Bao(app, 100, 100, speed, ""); 
        o[0] = new Obstacle(app, 200, 200, "");
        o[1] = new Obstacle(app, 200, 200, "");
        o[2] = new Obstacle(app, 200, 200, "");
        o[3] = new Obstacle(app, 200, 200, "");
        toDisplay = new ArrayList<>(Arrays.asList(o));
        maskLayer = app.createGraphics(1600, 1200);
    }
    
    @Override
    public void loadLevel(){
        if (startTime == -1){
            startTime = app.millis();
        }
        elapsedTime = app.millis() - startTime;
        confidenceL = 200 - 2*(elapsedTime)/1000;
        if (!gameOver && elapsedTime >= TOTAL_DURATION){
            endScreen();
            return;
        }
        app.fill(100);
        app.text("Time Left: "+Math.max(0, (TOTAL_DURATION-elapsedTime)/1000), 1050, 50);
        
        
        
        bao.draw();
        for (Obstacle obs: toDisplay){
            obs.draw();
        }
        
        
        app.fill(255,0,0);
        maskLayer.beginDraw();
        maskLayer.background(0);
        maskLayer.noStroke();
        
        float steps = 100;
        for (int i = 100; i > 0; i--) {
          float alpha = app.map(i, 0, steps, 255, 0); // more transparent on outside
          float r = app.map(i, 0, steps, 0, confidenceL);
          maskLayer.fill(alpha);
          maskLayer.ellipse(goToX, goToY, r * 2, r * 2);
        }

        maskLayer.endDraw();
        PImage maskImg = maskLayer.get();
        maskImg.filter(app.INVERT);
//        
        PGraphics blackScreen = app.createGraphics(1600, 1200);
        blackScreen.beginDraw();
        blackScreen.background(0);
        blackScreen.endDraw();
        
        blackScreen.mask(maskImg);  
        app.image(blackScreen,0,0);
        
        drawCollisions();
//        drawCollisions();
    }

    
    public void keyPressed(){
        if (app.keyPressed) {
            switch (app.keyCode) {
                case LEFT:
                    bao.move(-1, 0);
                    goToX -=speed;
                    break;
                case RIGHT:
                    bao.move(1, 0);
                    goToX+=speed;
                    break;
                case UP:
                    bao.move(0, -1);
                    goToY-=speed;
                    break;
                case DOWN:
                    bao.move(0, 1);
                    goToY+=speed;
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
        for (Obstacle obs : toDisplay){
            if (bao.isCollidingWith(obs)){
//                play animation of collecting goal
                toDisplay.remove(obs);
                return;
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
        app.text("Bao collected all of his things and is leaving for his friend's house!", app.width/2, 200);
        
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
        app.text("Bao took too long to pack his things. Mom found out.", app.width/2, 200);
        
        app.textSize(20);
        app.text("Press Enter to exit", app.width/2, 800-100);
    }
}
