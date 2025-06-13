/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runaway;

/**
 *
 * @author linhong
 */

import java.util.ArrayList;
import java.util.Arrays;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import static processing.core.PConstants.*;

public class Level4 extends Level {
    private Bao bao;
    private Obstacle[] o = new Obstacle[4];
    private ArrayList<Obstacle> toDisplay;
    private Goal goal;

    private PApplet app;
    private PImage image;
    private PGraphics maskLayer;

    private boolean gameOver = false;
    private int startTime = -1;
    private int elapsedTime = 0;
    private final int TOTAL_DURATION = 10_000;

    private int confidenceL = 200;
    private int speed = 5;

    public Level4(PApplet app) {
        super(app, 4);
        this.app = app;

        bao = new Bao(app, 100, 100, speed, "");
        goal = new Goal(app, 1000, 600, "");

        o[0] = new Obstacle(app, 400, 200, "");
        o[1] = new Obstacle(app, 600, 300, "");
        o[2] = new Obstacle(app, 700, 500, "");
        o[3] = new Obstacle(app, 800, 400, "");

        toDisplay = new ArrayList<>(Arrays.asList(o));
        maskLayer = app.createGraphics(1600, 1200);
    }

    @Override
    public void loadLevel() {
        if (startTime == -1) {
            startTime = app.millis();
        }

        elapsedTime = app.millis() - startTime;

        if (elapsedTime >= TOTAL_DURATION) {
            timeUp();
            return;
        }

        // Calculate confidence based on distance from goal
        float dist = PApplet.dist(bao.x, bao.y, goal.x, goal.y);
        confidenceL = (int) app.map(dist, 0, 1000, 250, 120);
        confidenceL = PApplet.constrain(confidenceL, 120, 250);
        
        // Draw game objects
        app.fill(100);
        goal.draw();
        bao.draw();
        for (Obstacle obs : toDisplay) {
            obs.draw();
        }

        // Draw the confidence mask
        maskLayer.beginDraw();
        maskLayer.background(0);
        maskLayer.noStroke();

        float steps = 100;
        for (int i = 100; i > 0; i--) {
            float alpha = app.map(i, 0, steps, 255, 0);
            float r = app.map(i, 0, steps, 0, confidenceL);
            maskLayer.fill(alpha);
            maskLayer.ellipse(bao.x, bao.y, r * 2, r * 2);
        }

        maskLayer.endDraw();
        PImage maskImg = maskLayer.get();
        maskImg.filter(app.INVERT);

        PGraphics blackScreen = app.createGraphics(1600, 1200);
        blackScreen.beginDraw();
        blackScreen.background(0, 204); // 80% transparent black
        blackScreen.endDraw();

        blackScreen.mask(maskImg);
        app.image(blackScreen, 0, 0);
        
        // Confidence Bar
        app.textAlign(PApplet.LEFT, PApplet.TOP);
        app.fill(255);
        app.text("Confidence", 20, 20);

        int barMaxWidth = 200;
        int barHeight = 15;
        int barWidth = (int) PApplet.map(confidenceL, 120, 250, 0, barMaxWidth);

        app.noStroke();
        app.fill(200); // background bar
        app.rect(20, 45, barMaxWidth, barHeight);

        app.fill(0, 200, 0); // green fill
        app.rect(20, 45, barWidth, barHeight);
        
        app.fill(255);
        app.text("Time Left: " + Math.max(0, (TOTAL_DURATION - elapsedTime) / 1000), 1050, 50);
        
        drawCollisions();
    }

    public void keyPressed() {
        if (app.keyPressed && !gameOver) {
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
        }

        if (gameOver && app.keyCode == ENTER) {
            ((Sketch) app).returnToMenu();
        }
    }

    public void drawCollisions() {
        if (bao.isCollidingWith(goal)) {
            winScreen();
            return;
        }

        for (Obstacle obs : toDisplay) {
            if (bao.isCollidingWith(obs)) {
                endScreen();
                return;
            }
        }
    }

    public void winScreen() {
        gameOver = true;
        app.background(255);
        app.fill(100);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.textSize(40);
        app.text("You Win!", app.width / 2, 100);

        app.textSize(20);
        app.text("Bao followed his instincts and made it to safety.", app.width / 2, 200);
        app.text("Press Enter to exit", app.width / 2, app.height - 100);
    }

    public void endScreen() {
        gameOver = true;
        System.out.println("End screen 4");
        app.background(255);
        app.fill(100);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.textSize(40);
        app.text("Oh no!", app.width / 2, 100);

        app.textSize(20);
        app.text("Bao wandered too long and got caught!", app.width / 2, 200);
        app.text("Press Enter to exit", app.width / 2, app.height - 100);
    }
    
    

    public void timeUp() {
        gameOver = true;
        System.out.println("End screen 4");
        app.background(255);
        app.fill(100);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.textSize(40);
        app.text("Oh no!", app.width / 2, 100);

        app.textSize(20);
        app.text("Bao wandered too long and lost his confidence.", app.width / 2, 200);
        app.text("Press Enter to exit", app.width / 2, app.height - 100);
    }
}