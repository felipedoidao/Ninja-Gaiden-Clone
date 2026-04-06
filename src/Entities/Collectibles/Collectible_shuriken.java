package Entities.Collectibles;

import World.Camera;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import Main.Main;

public class Collectible_shuriken extends Collectibles{

    public static BufferedImage[] shuriken;

    public Collectible_shuriken(int x, int y, int width, int height){
        super(x, y, width, height);

        shuriken = new BufferedImage[1];
        shuriken[0] = Main.ninja.getSprite(32, 32*5, 16, 16);

    }

    public void render(Graphics g){
        g.drawImage(Collectible_shuriken.shuriken[0], this.getX() - Camera.x, this.getY() - Camera.y, 16, 16, null);
    }

}
