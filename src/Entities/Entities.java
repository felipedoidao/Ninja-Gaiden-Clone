package Entities;

import java.awt.Graphics;

import World.Camera;
import Main.Main;

public class Entities {

    protected double x;
    protected double y;

    protected int width;
    protected int height;

    protected boolean dead = false;

    protected int index = 0, frames = 0, maxIndex, maxFrames;

    public Entities(double x, double y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(Graphics g){}

    public void update(){}

    public void animFrames(){
        frames++;
        if (frames >= maxFrames){
            index++;
            frames = 0;
            if (index >= maxIndex){
                if (!dead){
                    index = 0;
                }else{
                    index = maxIndex-1;
                }
            }
        }
    }

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

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    
}
