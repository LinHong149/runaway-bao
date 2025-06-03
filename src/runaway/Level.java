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
//        app.fill(100);
//        boolean unlocked = false;
//        for(int i : completed){
//            if (i == level-1){
//                unlocked = true;
//            }
//        }
//        if (!unlocked) return;
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
        
        app.text(info[0], 10, 10);
    }
    
    public void draw(){
        app.fill(100);
        app.rect(80, 40, 400-160, 400-80);
    }
    
    public void keyPressed(){}
}
