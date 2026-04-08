package Entities.Collectibles;

import World.Camera;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import Main.Main;

public class Collectible_fireball extends Collectibles{
    
    public static BufferedImage image;
    
    public Collectible_fireball(int x, int y, int width, int height){
        super(x, y, width, height);
        
        image = Main.ninja.getSprite(16, 16 + (32*5), 16, 16);

    }

    public void render(Graphics g){
        g.drawImage(image, this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
}
