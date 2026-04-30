package Entities.Weapons;

import java.awt.image.BufferedImage;

import Entities.Player;

import java.awt.Graphics;

import World.Camera;
import Main.Main;

public class Invincible_fire extends Weapons{

    private BufferedImage[] image;

    public Invincible_fire(int x, int y, int width, int height){
        super(x, y, width, height);

        this.maxIndex = 4;
        this.maxFrames = 10;
        this.destroy = false;

        image = new BufferedImage[4];

        for (int i=0; i < 4; i++){
            image[i] = Main.ninja.getSprite(32+16*i, 32*5+16, 16, 16);
        }
    }

    public void render (Graphics g){
        g.drawImage(image[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }

    public void update(){

        this.animFrames();

        if (!Player.invincible){
            Main.entities.remove(this);
        }
    }
}
