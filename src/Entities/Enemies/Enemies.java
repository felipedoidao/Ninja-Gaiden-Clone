package Entities.Enemies;

import java.awt.Graphics;

import Entities.Player;
import Main.Main;
import World.Camera;

public class Enemies {

    protected double x;
    protected double y;

    protected int width;
    protected int height;
    protected int hori_dir;

    public int xStart;
    public int yStart;

    protected boolean isDead = true;
    protected boolean canRespawn = true;

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
        }else if (this.x < player.getX()){
            this.hori_dir = 1;
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

        return (player.getMaskX() + player.getMaskWidth() > x &&
                player.getMaskX() < x + this.width && 
                player.getMaskY() + player.getMaskHeight() >= y &&
                player.getMaskY() <= y + this.height);
    }

    public void hurt(Player player, int x, int y){

        boolean hurt = (player.getSwordX() + player.getSwordWidth() > this.x &&
                player.getSwordX() < this.x + this.width && 
                player.getSwordY() + player.getSwordHeight() >= this.y &&
                player.getSwordY() <= this.y + this.height);

        if (hurt){
            this.isDead = true;
        }
    }

    public void respawn(){
        if (this.isDead){

            if (this.xStart > Camera.x + Main.Largura || this.xStart < Camera.x - 32){
                this.canRespawn = true;
            }
            if (this.canRespawn && this.xStart+32 >= Camera.x-1 || this.canRespawn && this.xStart <= Camera.x+1+Main.Largura){
                this.x = this.xStart;
                this.y = this.yStart;
                isDead = false;
                canRespawn = false;
                this.locatePlayer(Main.player);
            }
        }
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
