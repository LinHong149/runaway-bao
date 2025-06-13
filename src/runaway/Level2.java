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
import processing.core.PImage;


import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.UP;
import static processing.core.PConstants.ENTER;

public class Level2 extends Level {
    private Bao bao;
    private Obstacle[] o = new Obstacle[12];
    private PApplet app;
    private static PImage image;
    private boolean gameOver =false;
    private int startTime = -1;
    private int elapsedTime = 0;
    private final int WIN_DURATION = 60_000;
    private boolean movingUp = false;
    private boolean movingDown = false;
    public static boolean img = false;
    
    public Level2(PApplet app) {
        super(app, 2);
        this.app = app;
        bao = new Bao(app, 100, 100, 6, "");
        
        if (!img){
            image = app.loadImage("images/Level2BG.png");
            img = true;
        }

        int spacing = 250; // horizontal spacing between projectiles
        for (int i = 0; i < 12; i++) {
            int x = app.width + i * spacing;
            int y = (int) app.random(50, app.height - 50);
            o[i] = new Obstacle(app, x, y, "");
            o[i].speed = (int) app.random(6, 13); // speed 6–12
            
        }

    }
    
    @Override
    public void loadLevel() {
        if (startTime == -1) {
            startTime = app.millis();
        }

        elapsedTime = app.millis() - startTime;
        if (!gameOver && elapsedTime >= WIN_DURATION) {
            winScreen();
            return;
        }

        app.image(image,-400,-250, 1600, 1600);
        app.textSize(20);
        app.text("Time Left: " + Math.max(0, (WIN_DURATION - elapsedTime) / 1000), 1050, 50);

        bao.draw();
        if (movingUp) bao.move(0, -1);
        if (movingDown) bao.move(0, 1);

        if (!gameOver) {
            for (int i = 0; i < o.length; i++) {
                o[i].fly(o[i].speed);
                o[i].draw();

                if (o[i].x < -o[i].width) {
                    o[i].x = app.width + (int) app.random(50, 200);
                    o[i].y = (int) app.random(50, app.height - 50);
                    o[i].speed = (int) app.random(6, 10);
                }
            }
        }

        drawCollisions();
    }
    
    

    @Override
    public void keyPressed() {
        if (gameOver && app.keyCode == ENTER) {
            ((Sketch) app).returnToMenu();
            return;
        }

        if (app.keyCode == UP) movingUp = true;
        else if (app.keyCode == DOWN) movingDown = true;
    }
    
    @Override
    public void keyReleased() {
        if (app.keyCode == UP) movingUp = false;
        else if (app.keyCode == DOWN) movingDown = false;
    }

    public void drawCollisions(){
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
