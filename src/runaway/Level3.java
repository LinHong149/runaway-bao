package runaway;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import static processing.core.PConstants.*;

/**
 * Represents the third level of the game.
 * This level features Bao trying to reach his mother while managing his confidence level.
 * The level includes a dynamic lighting system and a time limit.
 *
 * @author linhong
 */
public class Level3 extends Level {
    /** The player character */
    private Bao bao;
    /** Array of blocking obstacles (walls) */
    private Obstacle[] b = new Obstacle[6];
    /** The goal object (mother) */
    private Goal goal;
    /** Reference to the Processing applet for rendering */
    private PApplet app;
    /** Background image for the level */
    private static PImage image;
    /** Graphics layer for the confidence mask effect */
    private PGraphics maskLayer;

    /** Whether the game is over */
    private boolean gameOver = false;
    /** Time when the level started */
    private int startTime = -1;
    /** Time elapsed since level start */
    private int elapsedTime = 0;
    /** Total duration of the level (60 seconds) */
    private final int TOTAL_DURATION = 60_000;
    /** Flag for background image loading */
    private static boolean img = false;

    /** Current confidence level (affects visibility radius) */
    private int confidenceL = 250;
    /** Movement speed of Bao */
    private int speed = 5;

    /**
     * Creates a new instance of Level 3.
     * Initializes Bao, obstacles, and the goal (mother).
     *
     * @param app The Processing applet instance
     */
    public Level3(PApplet app) {
        super(app, 3);
        this.app = app;

        if (!img) {
            image = app.loadImage("images/Level3BG.png");
            img = true;
        }

        bao = new Bao(app, 100, 100, speed, "");
        goal = new Goal(app, 1100, 100, "images/mother.png");

        // Blocking obstacles (walls)
        b[0] = new Obstacle(app, 0,330, 100, 500);
        b[1] = new Obstacle(app, 200, 0, 360, 420);
        b[2] = new Obstacle(app, 630, 0, 400, 420);
        b[3] = new Obstacle(app, 300, 500, 250, 600);
        b[4] = new Obstacle(app, 850, 660, 200, 200);
        b[5] = new Obstacle(app, 750, 400, 300, 120);

        maskLayer = app.createGraphics(1600, 1200);
    }

    /**
     * Loads and updates the level content.
     * Handles player movement, confidence system, and rendering.
     */
    @Override
    public void loadLevel() {
        if (startTime == -1) startTime = app.millis();
        elapsedTime = app.millis() - startTime;

        if (elapsedTime >= TOTAL_DURATION) {
            timeUp();
            return;
        }

        float dist = PApplet.dist(bao.x, bao.y, goal.x, goal.y);
        confidenceL = (int) app.map(dist, 0, 1000, 250, 120);
        confidenceL = PApplet.constrain(confidenceL, 120, 250);

        app.image(image, 0, 0, 1200, 800);
        goal.draw();
        bao.draw();

        drawConfidenceMask();
        drawConfidenceBar();
        drawTimer();
        drawCollisions();
    }

    /**
     * Handles key press events.
     * Manages player movement and level completion.
     */
    public void keyPressed() {
        if (!gameOver && app.keyPressed) {
            switch (app.keyCode) {
                case LEFT -> bao.move(-1, 0, b);
                case RIGHT -> bao.move(1, 0, b);
                case UP -> bao.move(0, -1, b);
                case DOWN -> bao.move(0, 1, b);
            }
        }
        if (gameOver && app.keyCode == ENTER) {
            ((Sketch) app).returnToMenu();
        }
    }

    /**
     * Creates and renders the confidence mask effect.
     * This creates a dynamic lighting system around Bao.
     */
    private void drawConfidenceMask() {
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
        blackScreen.background(0, 204); // 80% dark overlay
        blackScreen.endDraw();
        blackScreen.mask(maskImg);
        app.image(blackScreen, 0, 0);
    }

    /**
     * Renders the confidence level bar in the UI.
     * Shows the current confidence level and its maximum value.
     */
    private void drawConfidenceBar() {
        app.textAlign(PApplet.LEFT, PApplet.TOP);
        app.fill(255);
        app.text("Confidence", 20, 20);

        int barMaxWidth = 200;
        int barHeight = 15;
        int barWidth = (int) PApplet.map(confidenceL, 120, 250, 0, barMaxWidth);

        app.noStroke();
        app.fill(200);
        app.rect(20, 45, barMaxWidth, barHeight);
        app.fill(0, 200, 0);
        app.rect(20, 45, barWidth, barHeight);
    }

    /**
     * Renders the time remaining in the level.
     */
    private void drawTimer() {
        app.fill(255);
        app.text("Time Left: " + Math.max(0, (TOTAL_DURATION - elapsedTime) / 1000), 1050, 50);
    }

    /**
     * Checks for collisions between Bao and the goal.
     * Triggers win condition if Bao reaches his mother.
     */
    private void drawCollisions() {
        if (bao.isCollidingWith(goal)) {
            winScreen();
        }
    }

    /**
     * Displays the win screen when Bao reaches his mother.
     * Shows completion message and allows returning to menu.
     */
    public void winScreen() {
        gameOver = true;
        app.background(255);
        app.fill(100);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.textSize(40);
        app.fill(0, 150, 0); // Green color for success
        app.text("Home Sweet Home!", app.width / 2, 100);

        app.textSize(20);
        app.fill(100);
        app.text("Bao reaches out to his mother...", app.width / 2, 200);
        app.text("Her warm embrace melts away his fears.", app.width / 2, 230);
        app.text("Sometimes running away leads you right back home.", app.width / 2, 260);
        
        app.textSize(20);
        app.fill(50);
        app.text("Press Enter to continue your adventure", app.width / 2, app.height - 100);
    }

    /**
     * Displays the game over screen when time runs out.
     * Shows failure message and allows returning to menu.
     */
    public void timeUp() {
        gameOver = true;
        app.background(255);
        app.fill(100);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.textSize(40);
        app.fill(150, 0, 0); // Red color for failure
        app.text("Lost in the Dark", app.width / 2, 100);

        app.textSize(20);
        app.fill(100);
        app.text("The night grows darker...", app.width / 2, 200);
        app.text("Bao's confidence fades with each passing moment.", app.width / 2, 230);
        app.text("Maybe home wasn't so bad after all.", app.width / 2, 260);
        
        app.textSize(20);
        app.fill(50);
        app.text("Press Enter to try again", app.width / 2, app.height - 100);
    }
}