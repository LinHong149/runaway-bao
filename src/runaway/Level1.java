/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runaway;

/**
 *
 * @author linhong
 */

import java.io.*;
import java.util.Scanner;
import processing.core.PApplet;


import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.UP;

public class Level1 extends Level {
    private Bao bao;
    private Obstacle[] o = new Obstacle[12];
    private Goal goal;
    private PApplet app;
    
    public Level1(PApplet app){
        super(app, 1);
        this.app = app;
        bao = new Bao(app, 100, 100, ""); 
        goal = new Goal(app, 400, 400, "");
        o[0] = new Obstacle(app, 200, 200, "");
        o[1] = new Obstacle(app, 100, 200, "");
        o[2] = new Obstacle(app, 200, 100, "");
        o[3] = new Obstacle(app, 200, 300, "");
    }
    
    @Override
    public void loadLevel(){
//        super.loadLevel();
//        System.out.println("Loading level");
        app.fill(100);
        bao.draw();
        o[0].draw();
        o[1].draw();
        o[2].draw();
        o[3].draw();
        goal.draw();
        drawCollisions();
    }
    
    public void keyPressed(){
        if (app.keyPressed) {
          if (app.keyCode == LEFT) {
            bao.move(-5, 0);
          } else if (app.keyCode == RIGHT) {
            bao.move(5, 0);
          } else if (app.keyCode == UP) {
            bao.move(0, -5);
          } else if (app.keyCode == DOWN) {
            bao.move(0, 5);
          } else {
            bao.move(0, 0);
          }
      }
    }
    
    public void drawCollisions(){
        if (bao.isCollidingWith(goal)){
            System.out.println("win game");
        }
        for (Obstacle ob : o){
            if (ob != null && bao.isCollidingWith(ob)){
                System.out.println("end game");
            }
        }
    }
}
