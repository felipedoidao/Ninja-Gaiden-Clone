package Entities.Enemies;

import java.awt.Color;
import java.awt.Graphics;

import World.Camera;

public class Projectile extends Enemies{

    public Launcher launcher;

    public double vx;
    public double vy;
    private double g = 0.4;

    public Projectile(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    public void render(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, this.width, this.height);
    }

    public void update(){
        this.launch();

    }

    public void launch(){
        this.x += this.vx;
        this.vy += this.g;
        this.y += this.vy;
        
    }   
}
