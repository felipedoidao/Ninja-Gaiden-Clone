package Entities.Collectibles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Player;
import Main.Main;
import World.Camera;

public class Collectible_cure extends Collectibles{

    private BufferedImage image;

    public Collectible_cure (int x , int y, int width, int height){
        super(x, y, width, height);

        image = Main.ninja.getSprite(96, 176, 16, 16);
    }

    public void render(Graphics g){
        g.drawImage(image, getX() - Camera.x, getY() - Camera.y, null);
    }

    public void update(){
        this.move();
        if (this.caught(Main.player)){
            Player.lives += 6;
            if (Player.lives > 16) Player.lives = 16;
            Main.entities.remove(this);
        }

        if (!this.inScreen()){
            Main.entities.remove(this);
        }
    }
}
