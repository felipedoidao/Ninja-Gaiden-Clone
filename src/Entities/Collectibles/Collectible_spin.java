package Entities.Collectibles;

import World.Camera;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Player;
import Main.Main;

public class Collectible_spin extends Collectibles{

    public static BufferedImage image;

    public Collectible_spin(int x, int y, int width, int height){
        super(x, y, width, height);

        image = Main.ninja.getSprite(0, 16 + (32*5), 16, 16);

    }

    public void render(Graphics g){
        g.drawImage(image, this.getX() - Camera.x, this.getY() - Camera.y, null);
    }

    public void update(){
        if(this.caught(Main.player)){
            if (Player.bag[0] instanceof Collectible_spin){
                Player.energy += 5;
            }
            Player.bag[0] = this;
            Main.entities.remove(this);
        }
        this.move();
        if(!inScreen()){
            Main.entities.remove(this);
        }
    }
}
