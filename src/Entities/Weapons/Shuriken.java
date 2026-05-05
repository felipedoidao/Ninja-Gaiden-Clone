package Entities.Weapons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Player;
import Main.Main;
import World.Camera;

public class Shuriken extends Weapons{

    private double speed;
    private int hori_dir;

    private BufferedImage[] shuriken;

    public Shuriken(int x, int y, int width, int height){
        super(x, y, width, height);

        this.maxFrames = 5;
        this.maxIndex = 2;

        this.destroy = false;

        this.first_move(Main.player);

        this.speed = 10;
        this.speed *= hori_dir;

        this.destroy = false;

        shuriken = new BufferedImage[2];

        for (int i = 0; i < 2; i++){
            shuriken[i] = Main.ninja.getSprite((32+16) +(16*i), 32*5, 16, 16);
        }

    }

    public void render(Graphics g){
        g.drawImage(shuriken[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }

    public void update(){
        this.animFrames();
        this.move(Main.player);
    }

    public void move(Player player){

        if(!this.inScreen()){
            this.destroy = true;
            player.usingIten = false;
        }
        
        this.x += speed;

        if (this.destroy){
            Main.entities.remove(this);
        }
        
    }

    public void first_move(Player player){
        if (player.last_hori_dir > 0) {
            this.hori_dir = 1;
        }else if (player.last_hori_dir < 0){
            this.hori_dir = -1;
        }
    }
}
