package Entities.Collectibles;

import World.Camera;
import java.awt.Graphics;
import java.awt.Color;

public class Collectible_shuriken extends Collectibles{
    public Collectible_shuriken(int x, int y, int width, int height){
        super(x, y, width, height);

    }

    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, this.width, this.height);
    }

}
