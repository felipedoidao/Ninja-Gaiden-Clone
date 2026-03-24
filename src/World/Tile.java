package World;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Main;

public class Tile {
    
    public static BufferedImage TILE_FLOOR = Main.level.getSprite(0, 0, 32, 32);
    public static BufferedImage GRIP_WALL_RIGHT = Main.level.getSprite(32, 0, 32, 32);
    public static BufferedImage GRIP_WALL_LEFT = Main.level.getSprite(64, 0, 32, 32);
    
    private BufferedImage sprite;
    private int x, y;

    public Tile(int x, int y, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.sprite = sprite;

    }

    public void render(Graphics g){
        g.drawImage(sprite, x - Camera.x, y - Camera.y, 32, 32, null);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
}
