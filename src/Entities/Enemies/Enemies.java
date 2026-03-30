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

    protected int index = 0, frames = 0, maxIndex, maxFrames;

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

    public boolean inScreen(){
        return this.getX() + 32 > Camera.x && this.getX() < Camera.x + Main.Largura;
    }

    public boolean hitPlayer(Player player, int x, int y){

        return (player.getMaskX() + player.getMaskWidth() > x &&
                player.getMaskX() < x + this.width &&
                player.getMaskY() + player.getMaskHeight() >= y &&
                player.getMaskY() <= y + this.height);
    }

    public boolean hurt(Player player, int x, int y){

        return (player.getSwordX() + player.getSwordWidth() > x &&
                player.getSwordX() < x + this.width && 
                player.getSwordY() + player.getSwordHeight() >= y &&
                player.getSwordY() <= y + this.height);
    }

    public void respawn(){
        if (this.isDead){

            if (this.xStart + 32 < Camera.x || this.xStart > Camera.x + Main.Largura){
                this.canRespawn = true;
            }
            if (this.canRespawn && this.xStart+32 > Camera.x && this.xStart < Camera.x + Main.Largura){
                this.x = this.xStart;
                this.y = this.yStart;
                this.isDead = false;
                this.canRespawn = false;
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
