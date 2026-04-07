package Entities.Collectibles;

import java.awt.Graphics;

import Entities.Entities;
import Entities.Player;
import World.Tile;
import World.World;
import Entities.Enemies.Enemies;
import Main.Main;

public class Collectibles extends Entities{

    protected double gravity = 0.4;
    protected double fallSpeed = 0;

    public Collectibles(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    public void caught(Player player){
        if (this.x >= player.getX() && this.x+this.width <= player.getX()+32 && this.y >= player.getY() && this.y+this.height <= player.getY()+32){
            Player.bag[0] = this;
            Main.entities.remove(this);
        }
    }

    public void move(){
        this.fallSpeed += this.gravity;

        for (int i = 0; i < Math.abs(this.fallSpeed); i++){
            double moveStep = Math.signum(this.fallSpeed);

            Tile hit = World.isFree((int) x, (int)(this.y + moveStep), this.width, this.height);

            switch (hit){
                case null -> {
                    this.y += moveStep;
                    break;
                }
                default -> {
                    this.fallSpeed = 0;
                    break;
                }
            }
        }
    }

    public void render(Graphics g){}

    public void update(){
        this.caught(Main.player);
        this.move();

    }
}
