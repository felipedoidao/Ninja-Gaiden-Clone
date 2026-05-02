package Entities.Collectibles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import Entities.Player;
import Main.Main;
import World.Camera;

public class Collectible_star extends Collectibles{
    BufferedImage image;

    public Collectible_star(int x, int y, int width, int height){
        super(x, y, width, height);

        image = Main.ninja.getSprite(96, 160, 16, 16);

    }

    public void render(Graphics g){
        g.drawImage(image, getX() - Camera.x, getY() - Camera.y, null);
    }

    public void update(){
        if (this.caught(Main.player)){
            Player.invincible = true;
            Player.invincibleTimer = 0;
            Main.entities.remove(this);
        }
        this.move();
        if (!this.inScreen()){
            Main.entities.remove(this);
        }
    }
}
