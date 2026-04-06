package Entities.Weapons;

import java.awt.Color;
import java.awt.Graphics;

import Entities.Player;
import Main.Main;

public class Shuriken extends Weapons{

    private double speed;
    private int hori_dir = 0;
    private int vert_dir = 0;

    public Shuriken(int x, int y, int width, int height){
        super(x, y, width, height);

        this.speed = 4;

        this.first_move(Main.player);

    }

    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }

    public void update(){
        this.move(Main.player);

    }

    public void move(Player player){
        double distance = Math.sqrt(((this.x - player.getX())*(this.x - player.getX())) + ((this.y - player.getY())*(this.y - player.getY())));

        this.speed *= hori_dir;

        if (distance >= 100){
            this.hori_dir*=-1;
        }
        
    }

    public void first_move(Player player){
        if (player.hori_dir > 0) {
            this.hori_dir = 1;
        }else {
            this.hori_dir = -1;
        }
    }
}
