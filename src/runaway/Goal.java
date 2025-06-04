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
public class Goal {
    public int x, y;
    public int width = 72, height = 72;
    private PApplet app;
    private PImage image;
    public Goal(PApplet p, int x, int y, String path){
        this.app = p;
        this.x = x;
        this.y = y;
//       image = app.loadImage(path);
    }
//    
//    public void move (int dx, int dy){
//        x += dx;
//        y += dy;
//    }
    
    public void draw(){
        app.fill(0, 200, 0);
        app.ellipse(x, y, width, height);
    }
    
      
    public boolean isClicked(int mouseX, int mouseY){
        int centerX = x+(width/2);
        int centerY = y+(height/2);
        float d = PApplet.dist(mouseX, mouseY, centerX, centerY);
        System.out.println(d<16);
        System.out.println(mouseX);
        System.out.println(mouseY);
        System.out.println(centerX);
        System.out.println(centerY);
        return d<width;
        
    }
}
