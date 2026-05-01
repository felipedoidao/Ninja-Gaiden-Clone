package Entities.Collectibles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Main;
import World.Camera;

public class Collectible_hourglass extends Collectibles{

    BufferedImage image;

    public Collectible_hourglass(int x, int y, int width, int height){
        super(x, y, width, height);

        image = Main.ninja.getSprite(80, 160, 16, 16);

    }

    public void render(Graphics g){ 
        g.drawImage(image, getX() - Camera.x, getY() - Camera.y, null);
    }

    public void update(){
        this.move();

        if (this.caught(Main.player)){
            Main.time = false;
            Main.entities.remove(this);
        }

        if (!this.inScreen()){
            Main.entities.remove(this);
        }
        
    }
}
