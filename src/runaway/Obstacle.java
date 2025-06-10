/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runaway;

import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author linhong
 */
public class Obstacle {
    public int x, y;
    public int width = 72, height = 72;
    private PApplet app;
    public int speed = 0;
    private PImage image;
    public static int nObstacles = 0;
    public Obstacle(PApplet p, int x, int y, String path){
        this.app = p;
        this.x = x;
        this.y = y;
        nObstacles ++;
//       image = app.loadImage(path);
    }
//    
    public void fly (int speed){
        x -= speed; 
        this.draw();
    }
    
    public void draw(){
//        app.fill(200);
        app.ellipse(x, y, width, height);
    }
    
}
