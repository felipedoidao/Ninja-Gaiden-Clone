package Entities.Collectibles;

import World.Camera;
import java.awt.Graphics;
import java.awt.Color;

public class Collectible_fireball extends Collectibles{
    public Collectible_fireball(int x, int y, int width, int height){
        super(x, y, width, height);

    }

    public void render(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, this.width, this.height);
    }
}
