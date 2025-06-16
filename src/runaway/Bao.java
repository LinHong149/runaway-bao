package runaway;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the main character (Bao) in the game.
 * This class handles the character's movement, animation, collision detection,
 * and rendering using sprite-based graphics.
 */
public class Bao {
    /** The x-coordinate of Bao's position */
    public int x, y;
    /** The width and height of Bao's sprite */
    public int width = 50, height = 50;
    /** The movement speed of Bao */
    private int speed;

    /** Reference to the Processing applet for rendering */
    private PApplet app;

    // Sprite animation
    /** The sprite sheet containing all animation frames */
    private PImage spriteSheet;
    /** 2D array storing all sprite frames [direction][frame] */
    private PImage[][] sprites;
    /** Width of each sprite tile */
    private int tileSizex = 110;
    /** Height of each sprite tile */
    private int tileSizey = 120;

    /**
     * Enum representing the possible directions Bao can face
     */
    private enum Direction {
        FRONT, BACK, LEFT, RIGHT
    }

    /** Current facing direction of Bao */
    private Direction direction = Direction.FRONT;
    /** Whether Bao is currently walking */
    private boolean isWalking = false;
    /** Current animation frame */
    private int frame = 0;
    /** Counter for animation timing */
    private int frameCounter = 0;
    /** Speed of animation (lower = faster) */
    private int frameSpeed = 8;

    /**
     * Creates a new Bao character with default dimensions.
     *
     * @param p The Processing applet instance
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     * @param speed Movement speed
     * @param path Path to the sprite sheet (not used in current implementation)
     */
    public Bao(PApplet p, int x, int y, int speed, String path){
        this.app = p;
        this.x = x;
        this.y = y;
        this.speed = speed;

        // Load sprite sheet (change this to your actual path)
        spriteSheet = app.loadImage("images/spriteSheet.png");

        // Initialize sprite storage
        sprites = new PImage[4][3]; // 4 directions × 3 frames each

        loadSprites(); 
   }
    
    /**
     * Creates a new Bao character with custom dimensions.
     *
     * @param p The Processing applet instance
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     * @param w Custom width
     * @param h Custom height
     * @param speed Movement speed
     * @param path Path to the sprite sheet (not used in current implementation)
     */
    public Bao(PApplet p, int x, int y, int w, int h, int speed, String path){
        this.app = p;
        this.x = x;
        this.y = y;
        this.speed = speed;
        width = w;
        height = h;

        // Load sprite sheet (change this to your actual path)
        spriteSheet = app.loadImage("images/spriteSheet.png");

        // Initialize sprite storage
        sprites = new PImage[4][3]; // 4 directions × 3 frames each

        loadSprites();
    }

    /**
     * Loads and initializes all sprite frames from the sprite sheet.
     * Creates separate animations for each direction and handles mirroring for left-facing sprites.
     */
    private void loadSprites() {
        int startX = 40;
        int startY = 30;

        // FRONT
        sprites[Direction.FRONT.ordinal()][0] = spriteSheet.get(startX + 0 * tileSizex, startY + 0 * tileSizey, tileSizex, tileSizey); // (1,1)
        sprites[Direction.FRONT.ordinal()][1] = spriteSheet.get(startX + 0 * tileSizex, startY + 1 * tileSizey, tileSizex, tileSizey); // (1,2)
        sprites[Direction.FRONT.ordinal()][2] = spriteSheet.get(startX + 1 * tileSizex, startY + 0 * tileSizey, tileSizex, tileSizey); // (2,1)

        // BACK
        sprites[Direction.BACK.ordinal()][0] = spriteSheet.get(startX + 0 * tileSizex, startY + 2 * tileSizey, tileSizex, tileSizey); // (1,3)
        sprites[Direction.BACK.ordinal()][1] = spriteSheet.get(startX + 2 * tileSizex, startY + 2 * tileSizey, tileSizex, tileSizey); // (3,3)
        sprites[Direction.BACK.ordinal()][2] = spriteSheet.get(startX + 4 * tileSizex, startY + 2 * tileSizey, tileSizex, tileSizey); // (5,3)

        // RIGHT
        sprites[Direction.RIGHT.ordinal()][0] = spriteSheet.get(startX + 3 * tileSizex, startY + 0 * tileSizey, tileSizex, tileSizey); // (4,1)
        sprites[Direction.RIGHT.ordinal()][1] = spriteSheet.get(startX + 4 * tileSizex, startY + 0 * tileSizey, tileSizex, tileSizey); // (5,1)
        sprites[Direction.RIGHT.ordinal()][2] = spriteSheet.get(startX + 4 * tileSizex, startY + 1 * tileSizey, tileSizex, tileSizey); // (5,2)

        //
        for (int i = 0; i < 3; i++) {
            sprites[Direction.LEFT.ordinal()][i] = flipImage(sprites[Direction.RIGHT.ordinal()][i]);
        }
    }

    /**
     * Creates a horizontally flipped version of the given image.
     *
     * @param img The image to flip
     * @return A new PImage that is horizontally flipped
     */
    private PImage flipImage(PImage img) {
        PImage flipped = app.createImage(img.width, img.height, app.ARGB);
        img.loadPixels();
        flipped.loadPixels();
        for (int y = 0; y < img.height; y++) {
            for (int x = 0; x < img.width; x++) {
                int srcIndex = x + y * img.width;
                int dstIndex = (img.width - 1 - x) + y * img.width;
                flipped.pixels[dstIndex] = img.pixels[srcIndex];
            }
        }
        flipped.updatePixels();
        return flipped;
    }

    /**
     * Moves Bao while checking for collisions with obstacles.
     *
     * @param dx Horizontal movement direction (-1, 0, or 1)
     * @param dy Vertical movement direction (-1, 0, or 1)
     * @param walls Array of obstacles to check for collisions
     */
    public void move(int dx, int dy, Obstacle[] walls) {
    isWalking = dx != 0 || dy != 0;

    if (dy < 0) direction = Direction.BACK;
    else if (dy > 0) direction = Direction.FRONT;
    else if (dx < 0) direction = Direction.LEFT;
    else if (dx > 0) direction = Direction.RIGHT;

    int newX = x + dx * speed;
    int newY = y + dy * speed;

    // Temporarily store old position
    int oldX = x;
    int oldY = y;

    // Update position to test collision
    x = newX;
    y = newY;

    boolean blocked = false;
    for (Obstacle wall : walls) {
        if (wall != null && isRectCollidingWith(wall)) {
            blocked = true;
            break;
        }
    }

    if (blocked) {
        // revert to old position
        x = oldX;
        y = oldY;
        isWalking = false;
    }
}
    
    /**
     * Moves Bao without collision detection.
     *
     * @param dx Horizontal movement direction
     * @param dy Vertical movement direction
     */
    public void move(int dx, int dy) {
        x += dx*speed;
        y += dy*speed;
    }

    /**
     * Smoothly moves Bao towards a target position.
     *
     * @param x Target x-coordinate
     * @param y Target y-coordinate
     */
    public void moveTo(int x, int y) {
        if (this.x > x) {
            this.x -= 2;
        } else if (this.x < x) {
            this.x += 2;
        }
        if (this.y > y) {
            this.y -= 2;
        } else if (this.y < y) {
            this.y += 2;
        }
    }

    /**
     * Renders Bao on the screen with the current animation frame.
     */
    public void draw() {
        animate();
        PImage current = sprites[direction.ordinal()][frame];
        app.image(current, x, y, width, height);
    }

    /**
     * Updates the animation frame based on Bao's movement state.
     */
    private void animate() {
        if (isWalking) {
            frameCounter++;
            if (frameCounter >= frameSpeed) {
                // Only cycle between walking frames: frame 1 and 2
                frame = frame == 1 ? 2 : 1;
                frameCounter = 0;
            }
        } else {
            frame = 0; // idle frame only
            frameCounter = 0;
        }
    }

    /**
     * Checks if Bao is colliding with an obstacle using circular collision detection.
     *
     * @param other The obstacle to check collision with
     * @return true if Bao is colliding with the obstacle
     */
    public boolean isCollidingWith(Obstacle other){
        int centerX = x + (width / 2);
        int centerY = y + (height / 2);
        int otherCenterX = other.x + (other.width / 2);
        int otherCenterY = other.y + (other.height / 2);

        float d = PApplet.dist(otherCenterX, otherCenterY, centerX, centerY);
        return d < other.width;
    }

    /**
     * Checks if Bao is colliding with a goal using circular collision detection.
     *
     * @param other The goal to check collision with
     * @return true if Bao is colliding with the goal
     */
    public boolean isCollidingWith(Goal other){
        int centerX = x + (width / 2);
        int centerY = y + (height / 2);
        int otherCenterX = other.x + (other.width / 2);
        int otherCenterY = other.y + (other.height / 2);

        float d = PApplet.dist(otherCenterX, otherCenterY, centerX, centerY);
        return d < other.width;
    }
    /**
     * Sets whether Bao is currently walking.
     *
     * @param walking true if Bao should be walking, false otherwise
     */
    public void setWalking(boolean walking) {
        isWalking = walking;
    }
    /**
     * Checks if Bao is colliding with an obstacle using rectangular collision detection.
     *
     * @param other The obstacle to check collision with
     * @return true if Bao's rectangle intersects with the obstacle's rectangle
     */
    public boolean isRectCollidingWith(Obstacle other) {
    int baoLeft = x;
    int baoRight = x + width;
    int baoTop = y;
    int baoBottom = y + height;

    int rectLeft = other.x;
    int rectRight = other.x + other.width;
    int rectTop = other.y;
    int rectBottom = other.y + other.height;

    return !(baoRight < rectLeft || 
             baoLeft > rectRight || 
             baoBottom < rectTop || 
             baoTop > rectBottom);
}
}