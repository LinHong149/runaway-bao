package runaway;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents an obstacle in the game that can be either rectangular or image-based.
 * Obstacles can be static or moving, and they can collide with the player character.
 */
public class Obstacle {
    /** The x-coordinate of the obstacle's position */
    public int x, y;
    /** The width and height of the obstacle */
    public int width = 30, height = 30;
    /** Reference to the Processing applet for rendering */
    private PApplet app;
    /** Movement speed of the obstacle (if moving) */
    public int speed = 0;
    /** Static image used for image-based obstacles */
    private static PImage image;
    /** Counter for the total number of obstacles created */
    public static int nObstacles = 0;
    /** Flag indicating if this is a rectangular obstacle */
    private boolean isRect = false;

    /**
     * Resets the static image field to null.
     * This should be called when returning to the menu to ensure
     * proper image loading in the next level.
     */
    public static void resetImage() {
        image = null;
    }

    /**
     * Creates a new image-based obstacle with default dimensions.
     *
     * @param app The Processing applet instance
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     * @param imagePath Path to the obstacle's image
     */
    public Obstacle(PApplet app, int x, int y, String imagePath) {
        this.app = app;
        this.x = x;
        this.y = y;

        if (image == null) {
            image = app.loadImage(imagePath);
            if (image != null) {
                image.resize(width, height); // Resize once
            } else {
                System.err.println("Failed to load obstacle image: " + imagePath);
            }
        }
    }

    
    /**
     * Creates a new image-based obstacle with custom dimensions.
     *
     * @param app The Processing applet instance
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     * @param w Custom width
     * @param h Custom height
     * @param imagePath Path to the obstacle's image
     */
    public Obstacle(PApplet app, int x, int y, int w, int h, String imagePath) {
        this.app = app;
        this.x = x;
        this.y = y;

        // Load image only once
        if (image == null) {
            image = app.loadImage(imagePath);
        }
        width=w;
        height=h;
    }
           

    /**
     * Creates a new rectangular obstacle.
     *
     * @param p The Processing applet instance
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     * @param width Width of the rectangle
     * @param height Height of the rectangle
     */
    public Obstacle(PApplet p, int x, int y, int width, int height) {
        this.app = p;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isRect = true;
        nObstacles++;
    }

    /**
     * Moves the obstacle horizontally and sets its vertical position.
     *
     * @param speed Horizontal movement speed
     * @param y New vertical position
     */
    public void fly(int speed, int y) {
        this.y = y;
        x -= speed;
        this.draw();
    }

    /**
     * Moves the obstacle horizontally at the specified speed.
     *
     * @param speed Horizontal movement speed
     */
    public void fly(int speed) {
        x -= speed;
        this.draw();
    }

    /**
     * Renders the obstacle on the screen.
     * If it's a rectangular obstacle, draws a rectangle.
     * Otherwise, draws the obstacle's image.
     */
    public void draw() {
        if (isRect) {
            app.rect(x, y, width, height);
        } else {
            app.image(image,x, y, width,height);
        }
    }
}