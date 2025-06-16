/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runaway;

/**
 * Represents the first level of the game.
 * This level features Bao trying to reach a pizza while avoiding obstacles.
 * The level includes both static and moving obstacles, and tracks completion time.
 *
 * @author linhong
 */

import processing.core.PApplet;
import processing.core.PImage;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.UP;
import static processing.core.PConstants.ENTER;
import static runaway.Level2.img;

public class Level1 extends Level {
    /** The player character */
    private Bao bao;
    /** Array of moving obstacles (bells) */
    private static Obstacle[] o = new Obstacle[12];
    /** Array of static obstacles (walls) */
    private Obstacle[] b = new Obstacle[9];
    /** The goal object (pizza) */
    private Goal goal;
    /** Reference to the Processing applet for rendering */
    private PApplet app;
    /** Background image for the level */
    private static PImage image;
    /** Time when the level started */
    private int startTime = -1;
    /** Time elapsed since level start */
    private int elapsedTime = 0;
    /** Whether the game is over */
    private boolean gameOver = false;
    /** Whether the score has been written */
    private boolean wroteScore = false;
    /** Flag for background image loading */
    public static boolean img = false;
    /** Flag for obstacle images loading */
    public static boolean oimg = false;

    /**
     * Creates a new instance of Level 1.
     * Initializes Bao, obstacles, and the goal.
     *
     * @param app The Processing applet instance
     */
    public Level1(PApplet app){
        super(app, 1);
        this.app = app;
        bao = new Bao(app, 1000, 750, 5, ""); 
        goal = new Goal(app, 320, 200, "images/pizza.png");   
        
        if(!oimg){
        o[0] = new Obstacle(app, 100,320, "images/bell.png");
        o[1] = new Obstacle(app, 450, 220, "images/bell.png");
        o[2] = new Obstacle(app, 400, 320, "images/bell.png");
        o[3] = new Obstacle(app, 750, 310, "images/bell.png");
        o[4] = new Obstacle(app, 700, 200, "images/bell.png");
        o[5] = new Obstacle(app, 850, 460, "images/bell.png");
        o[6] = new Obstacle(app, 1000, 260, "images/bell.png");
        o[7] = new Obstacle(app, 150, 480, "images/bell.png");
        o[8] = new Obstacle(app, 120, 620, "images/bell.png");
        o[9] = new Obstacle(app, 500, 540, "images/bell.png");
        o[10] = new Obstacle(app, 750, 690, "images/bell.png");
        o[11] = new Obstacle(app, 950, 610, "images/bell.png");
        oimg = true;
        }
        
        b[0] = new Obstacle(app, 1100, 0,200, 400);
        b[1] = new Obstacle(app, 00, 400,400, 600);
        b[2] = new Obstacle(app, 670, 500,210, 120);
        b[3] = new Obstacle(app, 450, 300,220, 70);
        b[4] = new Obstacle(app, 0, 0,1200, 180);
        b[5] = new Obstacle(app, 270, 0,40, 420);
        b[6] = new Obstacle(app, 800, 0,110, 420);
        b[7] = new Obstacle(app, 1200, 0,1, 900);
        b[8] = new Obstacle(app, 0, 800,1300, 1);
        if (!img){
            image = app.loadImage("images/Level1BG.png");
            img = true;
        }
    }
    
    /**
     * Loads and updates the level content.
     * Handles player movement, collision detection, and rendering.
     */
    @Override
    public void loadLevel(){
//        super.loadLevel();
//        System.out.println("Loading level");
        bao.setWalking(false);
        if (startTime == -1) {
            startTime = app.millis();
        }

        elapsedTime = app.millis() - startTime;
        
        app.image(image,0,-200, 1200, 1409);
        
        boolean moved = false;

        if (!gameOver && app.keyPressed) {
            switch (app.keyCode) {
                case LEFT:
                    bao.move(-1, 0,b);
                    moved = true;
                    break;
                case RIGHT:
                    bao.move(1, 0,b);
                    moved = true;
                    break;
                case UP:
                    bao.move(0, -1,b);
                    moved = true;
                    break;
                case DOWN:
                    bao.move(0, 1,b);
                    moved = true;
                    break;
            }
        }
        bao.setWalking(moved);
        
        bao.draw();
        for (Obstacle obs : o){
            obs.draw();
        }
        goal.draw();
        drawCollisions();
    }
    
    /**
     * Handles key press events.
     * Manages level completion and menu navigation.
     */
    public void keyPressed(){
        if (app.keyPressed) {
            
          if (gameOver && app.keyCode == ENTER){
              ((Sketch)app).returnToMenu();
          }
        }
    }
    
    /**
     * Checks for collisions between Bao and other game objects.
     * Triggers win or lose conditions based on collisions.
     */
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
    
    /**
     * Displays the win screen when Bao reaches the goal.
     * Shows completion message and records the score.
     */
    public void winScreen(){
        gameOver = true;
        app.background(255);
        app.fill(100);
        
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        
        app.textSize(40);
        app.fill(0, 150, 0); // Green color for success
        app.text("Success!", app.width/2, 100);
        
        app.textSize(20);
        app.fill(100);
        app.text("Bao quietly munches on the dumpling...", app.width/2, 200);
        app.text("The taste reminds him of home.", app.width/2, 230);
        app.text("Maybe Mom's cooking isn't so bad after all.", app.width/2, 260);
        
        app.textSize(20);
        app.fill(50);
        app.text("Press Enter to continue your adventure", app.width/2, 800-100);
        
        if (!wroteScore){
            super.writeScore(1, elapsedTime);
        }
        wroteScore = true;
    }
    
    /**
     * Displays the game over screen when Bao collides with an obstacle.
     * Shows failure message and allows returning to menu.
     */
    public void endScreen(){
        gameOver = true;
        app.background(255);
        app.fill(100);
        
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.textSize(40);
        app.fill(150, 0, 0); // Red color for failure
        app.text("Oops!", app.width/2, 100);
        
        app.textSize(20);
        app.fill(100);
        app.text("The floor creaked under Bao's feet...", app.width/2, 200);
        app.text("Mom's eyes snap open!", app.width/2, 230);
        app.text("Looks like someone's getting a lecture about midnight snacks.", app.width/2, 260);
        
        app.textSize(20);
        app.fill(50);
        app.text("Press Enter to try again", app.width/2, 800-100);
    }
}
