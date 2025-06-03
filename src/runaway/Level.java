package runaway;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author linhong
 */


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import processing.core.PApplet;

public class Level {
    public int level = 0;
    public static ArrayList<Integer> completed = new ArrayList<>();
    private PApplet app;
    
    public Level(PApplet app, int currLevel){
        level = currLevel;
        this.app = app;
    }
    
    public static void completed(int level){
        completed.add(level);
    }
    
    public void loadLevel(){
    }
    
    public void loadPrelude(){
        String[] info = new String[3];
        try {
            Scanner file = new Scanner(new File("levels.txt"));
            String line = "";
            for (int i = 0; i <= level; i++){
                if (file.hasNext()){
                    line = file.nextLine();
                }
            }
           info = line.split("\\|");
            
        } catch (IOException e){
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
    
    public void draw(){
        app.fill(100);
        app.rect(80, 40, 400-160, 400-80);
    }
    
    public void keyPressed(){}
}
