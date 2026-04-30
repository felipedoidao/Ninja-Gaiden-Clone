package Entities.Collectibles;

import java.awt.Color;
import java.awt.Graphics;

import Entities.Player;
import Main.Main;
import World.Camera;

public class Collectible_star extends Collectibles{
    public Collectible_star(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    public void render(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(getX() - Camera.x, getY() - Camera.y, width, height);
    }

    public void update(){
        if (this.caught(Main.player)){
            Player.invincible = true;
            Main.entities.remove(this);
        }
        this.move();
        if (!this.inScreen()){
            Main.entities.remove(this);
        }
    }
}
