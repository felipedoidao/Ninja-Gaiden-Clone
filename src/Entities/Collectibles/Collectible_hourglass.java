package Entities.Collectibles;

import java.awt.Color;
import java.awt.Graphics;

import Main.Main;
import World.Camera;

public class Collectible_hourglass extends Collectibles{
    public Collectible_hourglass(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillRect(getX() - Camera.x, getY() - Camera.y, width, height);
    }

    public void update(){
        this.move();

        if (this.caught(Main.player)){
            Main.time = false;
            Main.entities.remove(this);
        }

        if (!this.inScreen()){
            Main.entities.remove(this);
        }
        
    }
}
