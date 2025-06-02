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

public class Level {
    public int level = 0;
    public static ArrayList<Integer> completed = new ArrayList<>();
    
    public Level(int currLevel){
        level = currLevel;
    }
    
    public static void completed(int level){
        completed.add(level);
    }
    
    public void loadLevel(){
        boolean unlocked = false;
        for(int i : completed){
            if (i == level-1){
                unlocked = true;
            }
        }
        if (!unlocked) return;
    }
    
    public void loadPrelude(){
        try {
            Scanner file = new Scanner(new File("levels.txt"));
            String line = "";
            for (int i = 0; i <= level; i++){
                if (file.hasNext()){
                    line = file.nextLine();
                }
            }
            String[] info = line.split("\\|");
            
//            System.out.println(info[0]);
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
