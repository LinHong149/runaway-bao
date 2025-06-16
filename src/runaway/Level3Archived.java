package runaway;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.Arrays;
import static processing.core.PConstants.ENTER;

public class Level3Archived extends Level {
    private Bao bao;
    private Goal[] g = new Goal[4];
    private ArrayList<Goal> toDisplay;
    private PApplet app;
    private static PImage image;
    private boolean gameOver = false;
    private int startTime = -1;
    private int elapsedTime = 0;
    private final int TOTAL_DURATION = 60_000;
    private int goToX = 100, goToY = 100;
    public static boolean imgLoaded = false;

    public Level3Archived(PApplet app) {
        super(app, 3);
        this.app = app;
        this.bao = new Bao(app, 100, 100, 80, 80, 5, ""); // custom size Bao

        // Load background image once
        if (!imgLoaded) {
            image = app.loadImage("images/Level2BG.png");
            imgLoaded = true;
        }

        // Place goals randomly (or set your own coordinates)
        g[0] = new Goal(app, 150, 200, "images/bell.png");
        g[1] = new Goal(app, 400, 500, "images/bell.png");
        g[2] = new Goal(app, 800, 300, "images/bell.png");
        g[3] = new Goal(app, 600, 600, "images/bell.png");
        toDisplay = new ArrayList<>(Arrays.asList(g));
    }

    @Override
    public void loadLevel() {
        if (startTime == -1) {
            startTime = app.millis();
        }

        elapsedTime = app.millis() - startTime;
        if (!gameOver && elapsedTime >= TOTAL_DURATION) {
            endScreen();
            return;
        }

        if (image != null) {
            app.image(image, 0, 0, app.width, app.height);
        }

        app.fill(0);
        app.textSize(20);
        app.text("Time Left: " + Math.max(0, (TOTAL_DURATION - elapsedTime) / 1000), 1050, 50);

        mousePressed();
        bao.draw();
        for (Goal goal : toDisplay) {
            goal.draw();
        }

        drawCollisions();
        bao.moveTo(goToX, goToY);
    }

    public void keyPressed() {
        if (gameOver && app.keyCode == ENTER) {
            ((Sketch) app).returnToMenu();
        }
    }

    public void drawCollisions() {
        if (toDisplay.isEmpty()) {
            winScreen();
            return;
        }

        // Use iterator to avoid ConcurrentModificationException
        for (int i = 0; i < toDisplay.size(); i++) {
            Goal goal = toDisplay.get(i);
            if (bao.isCollidingWith(goal)) {
                toDisplay.remove(i);
                break;
            }
        }
    }

    public void mousePressed() {
        if (app.mousePressed) {
            for (Goal goal : toDisplay) {
                if (goal.isClicked(app.mouseX, app.mouseY)) {
                    goToX = goal.x;
                    goToY = goal.y;
                }
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
        app.text("Bao collected all of his things and is leaving for his friend's house!", app.width / 2, 200);
        app.text("Press Enter to exit", app.width / 2, app.height - 100);
    }

    public void endScreen() {
        gameOver = true;
        app.background(255);
        app.fill(100);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.textSize(40);
        app.text("Oh no!", app.width / 2, 100);

        app.textSize(20);
        app.text("Bao took too long to pack his things. Mom found out.", app.width / 2, 200);
        app.text("Press Enter to exit", app.width / 2, app.height - 100);
    }
}