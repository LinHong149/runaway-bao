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
public class Bao {
    public int x, y;
    public int width = 72, height = 72;
    private PApplet app;
    private PImage image;
    public Bao(PApplet p, int x, int y, String path){
        this.app = p;
        this.x = x;
        this.y = y;
//       image = app.loadImage(path);
    }
    
    public void move (int dx, int dy){
        x += dx;
        y += dy;
    }
    
    public void draw(){
        app.ellipse(x, y, 40, 30);
    }
    
    
    
    public boolean isCollidingWith(Obstacle other){
        int centerX = x+(width/2);
        int centerY = y+(height/2);
        int otherCenterX = other.x+(other.width/2);
        int otherCenterY = other.y+(other.height/2);
        
        float d = PApplet.dist(otherCenterX, otherCenterY, centerX, centerY);
        
        return d<72;
  
    } 
    
    
    public boolean isCollidingWith(Goal other){
        int centerX = x+(width/2);
        int centerY = y+(height/2);
        int otherCenterX = other.x+(other.width/2);
        int otherCenterY = other.y+(other.height/2);
        
        float d = PApplet.dist(otherCenterX, otherCenterY, centerX, centerY);
        
        return d<72;
  
    } 
   
   
    
//    public boolean isClicked(int mouseX, int mouseY){
//        int centerX = x+(72/2);
//        int centerY = y+(72/2);
//        float d = PApplet.dist(mouseX, mouseY, centerX, centerY);
//        System.out.println(d<16);
//        System.out.println(mouseX);
//        System.out.println(mouseY);
//        System.out.println(centerX);
//        System.out.println(centerY);
//        return d<48;
//    }
    
//    public void displayInfo(PApplet p){
//        app.fill(0);
//        app.text("Name: "+name, x, y-30);
//        app.text("Age: "+age, x, y-10);
//    }
//    
}