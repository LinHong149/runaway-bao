/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runaway;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the goal or target that the player needs to reach in the game.
 * The goal is rendered as an image and can be interacted with through mouse clicks.
 *
 * @author linhong
 */
public class Goal {
    /** The x-coordinate of the goal's position */
    public int x, y;
    /** The width and height of the goal's image */
    public int width = 50, height = 50;
    /** Reference to the Processing applet for rendering */
    private PApplet app;
    /** The image used to represent the goal */
    private PImage image;

    /**
     * Creates a new goal at the specified position.
     *
     * @param p The Processing applet instance
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     * @param path Path to the goal's image
     */
    public Goal(PApplet p, int x, int y, String path) {
        this.app = p;
        this.x = x;
        this.y = y;
        image = app.loadImage(path);
    }
    
    /**
     * Renders the goal on the screen using its image.
     */
    public void draw() {
        app.image(image, x, y, width, height);
    }
    
    /**
     * Checks if the goal was clicked by the mouse.
     * Uses circular collision detection based on the distance between
     * the mouse position and the goal's center.
     *
     * @param mouseX The x-coordinate of the mouse click
     * @param mouseY The y-coordinate of the mouse click
     * @return true if the goal was clicked, false otherwise
     */
    public boolean isClicked(int mouseX, int mouseY) {
        int centerX = x + (width/2);
        int centerY = y + (height/2);
        float d = PApplet.dist(mouseX, mouseY, centerX, centerY);
        System.out.println(d < 16);
        System.out.println(mouseX);
        System.out.println(mouseY);
        System.out.println(centerX);
        System.out.println(centerY);
        return d < width;
    }
}
