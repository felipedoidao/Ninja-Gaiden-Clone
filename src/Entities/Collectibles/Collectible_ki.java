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
        this.caught(Main.player);
        if(!inScreen()){
            Main.entities.remove(this);
        }
    }

    public void caught(Player player){

        if (this.x >= player.getX() && this.x+this.width <= player.getX()+32 && this.y >= player.getY() && this.y+this.height <= player.getY()+32){
            Player.energy += 10;
            Main.entities.remove(this);
        }

    }
}
