package World;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Main;

public class Tile {
    
    public static BufferedImage TILE_FLOOR = Main.world.getSprite(0, 0, 32, 32);
    
    private BufferedImage sprite;
    private int x, y;

    public Tile(int x, int y, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.sprite = sprite;

    }

    public void render(Graphics g){
        g.drawImage(sprite, x, y, null);
    }
}
