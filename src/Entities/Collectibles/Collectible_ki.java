package Entities.Collectibles;

import java.awt.Graphics;

import Entities.Player;
import Main.Main;
import World.Camera;
import java.awt.image.BufferedImage;

public class Collectible_ki extends Collectibles{

    private BufferedImage image;

    public Collectible_ki(int x, int y, int width, int height){
        super(x, y, width, height);

        image = Main.ninja.getSprite(16, 32*5, 16, 16);
    }

    public void render (Graphics g){
        g.drawImage(image, this.getX() - Camera.x, this.getY() - Camera.y, null);
    }

    public void update(){
        this.move();
        if (this.caught(Main.player)){
            Player.energy += 10;
            Main.entities.remove(this);
        }
        if(!inScreen()){
            Main.entities.remove(this);
        }
    }
}
