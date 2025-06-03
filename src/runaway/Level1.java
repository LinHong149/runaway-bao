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
    private PApplet app;
    
    public Level1(PApplet app){
        super(app, 1);
        this.app = app;
        bao = new Bao(app, 100, 100, ""); 
    }
    
    @Override
    public void loadLevel(){
//        super.loadLevel();
    System.out.println("Loading level");
        app.fill(100);
        bao.draw();
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
}
