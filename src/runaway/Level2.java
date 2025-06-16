/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runaway;

/**
 * Represents the second level of the game.
 * This level features Bao trying to survive for a set duration while avoiding
 * flying slippers. The level includes moving obstacles and a time-based win condition.
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
    /** The player character */
    private Bao bao;
    /** Array of moving obstacles (slippers) */
    private Obstacle[] o = new Obstacle[4];
    /** Array of static obstacles (empty in this level) */
    private Obstacle[] b = new Obstacle[0];
    /** Reference to the Processing applet for rendering */
    private PApplet app;
    /** Background image for the level */
    private static PImage image;
    /** Whether the game is over */
    private boolean gameOver = false;
    /** Time when the level started */
    private int startTime = -1;
    /** Time elapsed since level start */
    private int elapsedTime = 0;
    /** Duration required to win the level (60 seconds) */
    private final int WIN_DURATION = 60_000;
    /** Whether Bao is moving upward */
    private boolean movingUp = false;
    /** Whether Bao is moving downward */
    private boolean movingDown = false;
    /** Flag for background image loading */
    public static boolean img = false;
    
    /**
     * Creates a new instance of Level 2.
     * Initializes Bao and flying slipper obstacles.
     *
     * @param app The Processing applet instance
     */
    public Level2(PApplet app) {
        super(app, 2);
        this.app = app;
        bao = new Bao(app, 100, 100,80,80, 6, "");
        
        if (!img){
            image = app.loadImage("images/Level2BG.png");
            img = true;
        }

        int spacing = 250; // horizontal spacing between projectiles
        for (int i = 0; i < 4; i++) {
            int x = app.width + i * spacing;
            int y = (int) app.random(50, app.height - 50);
            o[i] = new Obstacle(app, x, y, 80,80 ,"images/slipper.png");
            o[i].speed = (int) app.random(6, 13); // speed 6–12
        }

    }
    
    /**
     * Loads and updates the level content.
     * Handles player movement, obstacle movement, collision detection, and time tracking.
     */
    @Override
    public void loadLevel() {
        if (startTime == -1) {
            startTime = app.millis();
        }

        elapsedTime = app.millis() - startTime;
        if (elapsedTime >= WIN_DURATION) {
            winScreen();
            return;
        }

        app.image(image,-300,-250, 1500, 1500);
        app.textSize(20);
        app.fill(255);
        app.text("Time Left: " + Math.max(0, (WIN_DURATION - elapsedTime) / 1000), 1050, 50);
        app.fill(0);
        
        bao.draw();
        if (movingUp) bao.move(0, -1,b);
        if (movingDown) bao.move(0, 1,b);

        if (!gameOver) {
            for (int i = 0; i < o.length; i++) {
                o[i].fly(o[i].speed);
                o[i].draw();

                if (o[i].x < -o[i].width) {
                    o[i].x = app.width + (int) app.random(50, 200);
                    o[i].y = (int) app.random(50, app.height - 50);
                    o[i].speed = (int) app.random(18, 24);
                }
            }
        }

        drawCollisions();
    }
    
    /**
     * Handles key press events.
     * Manages player movement and level completion.
     */
    @Override
    public void keyPressed() {
        if (gameOver && app.keyCode == ENTER) {
            ((Sketch) app).returnToMenu();
            return;
        }

        if (app.keyCode == UP) movingUp = true;
        else if (app.keyCode == DOWN) movingDown = true;
    }
    
    /**
     * Handles key release events.
     * Stops player movement when movement keys are released.
     */
    @Override
    public void keyReleased() {
        if (app.keyCode == UP) movingUp = false;
        else if (app.keyCode == DOWN) movingDown = false;
    }

    /**
     * Checks for collisions between Bao and obstacles.
     * Triggers game over if Bao collides with a slipper.
     */
    public void drawCollisions(){
        for (Obstacle ob : o){
            if (ob != null && bao.isCollidingWith(ob)){
                endScreen();
            }
        }
    }
    
    /**
     * Displays the win screen when the time limit is reached.
     * Shows completion message and allows returning to menu.
     */
    public void winScreen(){
        gameOver = true;
        movingUp = false;
        movingDown = false;
        app.background(255);
        app.fill(100);
        
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        
        app.textSize(40);
        app.fill(0, 150, 0); // Green color for success
        app.text("Victory!", app.width/2, 100);
        
        app.textSize(20);
        app.fill(100);
        app.text("Bao dodges the last slipper...", app.width/2, 200);
        app.text("Mom's aim is getting worse with age.", app.width/2, 230);
        app.text("Maybe she's not as scary as he thought.", app.width/2, 260);
        
        app.textSize(20);
        app.fill(50);
        app.text("Press Enter to continue your adventure", app.width/2, 800-100);
    }
    
    /**
     * Displays the game over screen when Bao collides with a slipper.
     * Shows failure message and allows returning to menu.
     */
    public void endScreen(){
        gameOver = true;
        movingUp = false;
        movingDown = false;
        app.background(255);
        app.fill(100);
        
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.textSize(40);
        app.fill(150, 0, 0); // Red color for failure
        app.text("Direct Hit!", app.width/2, 100);
        
        app.textSize(20);
        app.fill(100);
        app.text("Mom's slipper finds its target...", app.width/2, 200);
        app.text("Bao rubs his head, wondering if he should have studied harder.", app.width/2, 230);
        app.text("At least Mom's aim is still perfect.", app.width/2, 260);
        
        app.textSize(20);
        app.fill(50);
        app.text("Press Enter to try again", app.width/2, 800-100);
    }
}
