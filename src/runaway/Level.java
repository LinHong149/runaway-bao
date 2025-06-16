package runaway;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Represents a level in the game.
 * This class handles level loading, prelude display, and score tracking.
 * It also manages the list of completed levels.
 *
 * @author linhong
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import processing.core.PApplet;

public class Level {
    /** The current level number */
    public int level = 0;
    /** List of completed level numbers */
    public static ArrayList<Integer> completed = new ArrayList<>();
    /** Reference to the Processing applet for rendering */
    private PApplet app;
    
    /**
     * Creates a new level instance.
     *
     * @param app The Processing applet instance
     * @param currLevel The level number to create
     */
    public Level(PApplet app, int currLevel) {
        level = currLevel;
        this.app = app;
    }
    
    /**
     * Marks a level as completed by adding it to the completed levels list.
     *
     * @param level The level number to mark as completed
     */
    public static void completed(int level) {
        completed.add(level);
    }
    
    /**
     * Loads the level's content.
     * This method should be overridden by specific level implementations.
     */
    public void loadLevel() {
    }
    
    /**
     * Loads and displays the level's prelude information from levels.txt.
     * The prelude includes the level name, mission description, and objectives.
     */
    public void loadPrelude() {
        String[] info = new String[3];
        try {
            Scanner file = new Scanner(new File("levels.txt"));
            String line = "";
            for (int i = 0; i <= level; i++) {
                if (file.hasNext()) {
                    line = file.nextLine();
                }
            }
            info = line.split("\\|");
            
        } catch (IOException e) {
            System.out.println(e);
        }
        
        app.textSize(40);
        app.text(info[0], 160, 100);
        
        app.textSize(32);
        app.text("Mission", 160, 180);
        app.textSize(20);
        app.text(info[1], 160, 200, 400, 500);
        
        app.textSize(32);
        app.text("Objective", 650, 180);
        app.textSize(20);
        app.text(info[2], 650, 200, 400, 500);
        
        app.text("Press Enter to start", 800-280, 800-100);
    }
    
    /**
     * Renders the level's background.
     */
    public void draw() {
        app.fill(100);
        app.rect(80, 40, 400-160, 400-80);
    }
    
    /**
     * Handles key press events.
     * This method should be overridden by specific level implementations.
     */
    public void keyPressed() {}
    
    /**
     * Handles key release events.
     * This method should be overridden by specific level implementations.
     */
    public void keyReleased() {}
    
    /**
     * Writes the completion time for a level to the score file.
     *
     * @param level The level number
     * @param elapsedTime The time taken to complete the level in milliseconds
     */
    public void writeScore(int level, int elapsedTime) {
        try {
            Path path = Paths.get("score.txt");
            List<String> lines = Files.readAllLines(path);

            if (level > 0 && level <= lines.size()) {
                lines.set(level - 1, "Level " + level + ": " + elapsedTime/1000 + "s"); 
                Files.write(path, lines);
            } else {
                System.out.println("Invalid line number.");
            }
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
