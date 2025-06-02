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
    private PImage image;
    public Obstacle(PApplet p, int x, int y, String path){
        this.app = p;
        this.x = x;
        this.y = y;
       image = app.loadImage(path);
    }
    
    public void move (int dx, int dy){
        x += dx;
        y += dy;
    }
    
    public void draw(){
        app.fill(100);
        app.ellipse(x, y, 40, 30);
    }
    
    public boolean isCollidingWith(Obstacle other){
        int centerX = x+(image.pixelWidth/2);
        int centerY = y+(image.pixelHeight/2);
        int otherCenterX = other.x+(other.image.pixelWidth/2);
        int otherCenterY = other.y+(other.image.pixelHeight/2);
        
        float d = PApplet.dist(otherCenterX, otherCenterY, centerX, centerY);
        
        return d<72;
  
    }
    
}
