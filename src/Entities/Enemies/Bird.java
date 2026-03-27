package Entities.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import Main.Main;
import World.Camera;
import Entities.Player;

public class Bird extends Enemies{

    private BufferedImage[] right, left;

    private double hori_speed = 0;
    private double ver_speed = 0;
    private double aceleration = 0.08;

    public Bird (int x, int y, int width, int height){
        super(x, y, width, height);

        this.maxFrames = 5;
        this.maxIndex = 4;

        this.width = 28;
        this.height = 10;


        right = new BufferedImage[4];
        left = new BufferedImage[4];

        for (int i = 0; i < 4; i++){
            right[i] = Main.enemies.getSprite(128+(32*i), 32, 32, 32);
            left[i] = Main.enemies.getSprite(32*i, 32, 32, 32);
        }

        this.locatePlayer(Main.player);


    }

    public void render (Graphics g){
        
        if (this.hori_dir > 0){
            g.drawImage(right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        
        }else if (this.hori_dir < 0){
            g.drawImage(left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

        }

    }

    public void update(){
        this.animFrames();

        this.move(Main.player);

        this.hit();

    }

    public void hit(){
        if (this.hitPlayer(Main.player, this.getX(), this.getY() - 8) && !Main.player.hitted){
            Main.player.gotHit = true;
            Main.player.lives -= 1;
            Main.player.hitted = true;
            Main.player.knockBack = true;
            Main.player.inKnockBack = true;
        }
    }

    public void move(Player player){
        this.x += hori_speed;
        this.y += ver_speed;

        if (this.x >= player.getX()){
            this.hori_dir = -1;
        }else if (this.x <= player.getX()){
            this.hori_dir = 1;
        }

        if (hori_dir > 0){
            this.hori_speed += this.aceleration;
        }else if (hori_dir < 0){
            this.hori_speed -= this.aceleration;
        }


        if (this.y >= player.getY()){
            this.ver_speed -= this.aceleration + 0.01;
        }else if(this.y <= player.getY()){
            this.ver_speed += this.aceleration + 0.01;
        }

        if (this.hori_speed >= 3){
            this.hori_speed = 3;
        }

        if (this.ver_speed >= 3){
            this.ver_speed = 3;
        }

        if (this.hori_speed <= -3){
            this.hori_speed = -3;
        }

        if (this.ver_speed <= -3){
            this.ver_speed = -3;
        }

    }

}
