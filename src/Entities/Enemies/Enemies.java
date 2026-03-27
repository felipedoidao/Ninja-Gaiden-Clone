package Entities.Enemies;

import java.awt.Graphics;

import Entities.Player;
import Main.Main;

public class Enemies {

    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected int hori_dir;

    protected int index, frames, maxIndex, maxFrames;

    public Enemies(int x, int y, int width, int height){

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public void locatePlayer(Player player){
        if (this.x > player.getX()){
            this.hori_dir = -1;
        }else {
            this.hori_dir = -1;
        }
    }

    public void animFrames(){
        frames++;
        if (frames >= maxFrames){
            index++;
            frames = 0;
            if (index >= maxIndex){
                index = 0;
            }
        }
    }

    public boolean hitPlayer(Player player, int x, int y){

        return (player.getMaskX() + player.getMaskWidth() > this.x &&
                player.getMaskX() < this.x + this.width && 
                player.getMaskY() + player.getMaskHeight() >= this.y &&
                player.getMaskY() <= this.y + this.height);
    }


    public void render(Graphics g){}

    public void update(){}

    public int getX(){
        return (int)this.x;
    }
    public int getY(){
        return (int)this.y;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
}
