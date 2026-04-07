package Entities;

import java.awt.Graphics;

import World.Camera;
import Main.Main;

public class Entities {
    protected double x;
    protected double y;

    protected int width;
    protected int height;

    public Entities(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(Graphics g){}

    public void update(){}

    public boolean inScreen(){
        return this.getX() + 32 > Camera.x && this.getX() < Camera.x + Main.WIDTH && this.getY() > Camera.y && this.getY() < Camera.y + Main.HEIGHT;
    }

    public int getX(){
        return (int)this.x;
    }
    public int getY(){
        return (int)this.y;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    
}
