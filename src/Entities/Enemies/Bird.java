package Entities.Enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Player;
import Main.Main;
import World.Camera;

public class Bird extends Enemies{

    private BufferedImage[] right, left;

    private double hori_speed = 0;
    private double ver_speed = 0;
    private double aceleration = 0.08;

    public Bird (int x, int y, int width, int height){
        super(x, y, width, height);

        this.maxFrames = 5;
        this.maxIndex = 4;

        right = new BufferedImage[4];
        left = new BufferedImage[4];

        for (int i = 0; i < 4; i++){
            right[i] = Main.enemies.getSprite((32*4)+(32*i), 32, 32, 32);
            left[i] = Main.enemies.getSprite(32*i, 32, 32, 32);
        }

        this.locatePlayer(Main.player);


    }

    public void render (Graphics g){

        if(!this.isDead){
            if (this.hori_dir > 0){
                g.drawImage(right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        
            }else if (this.hori_dir < 0){
                g.drawImage(left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

            }
        }else if (this.dead){
            g.drawImage(this.explosion[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
            g.drawImage(this.p1[0], (int)this.p1x - Camera.x, (int)this.p1y - Camera.y, null);
            g.drawImage(this.p2[0], (int)this.p2x - Camera.x, (int)this.p2y - Camera.y, null);
            g.drawImage(this.p3[0], (int)this.p3x - Camera.x, (int)this.p3y - Camera.y, null);
            g.drawImage(this.p4[0], (int)this.p4x - Camera.x, (int)this.p4y - Camera.y, null);
        }

    }

    public void update(){

        if(!this.isDead){
            this.maxFrames = 5;
            this.maxIndex = 4;
            this.hit();
            this.move(Main.player);
            this.animFrames();
        
        }else {
            this.animFrames();
            ver_speed = 0;
            hori_speed = 0;
            this.respawn();
            this.deathAnimation();
        }

    }

    public void hit(){
        if (this.hitPlayer(Main.player, this.getX(), this.getY() + 8) && !Main.player.hitted){
            Main.player.gotHit = true;
            Main.player.lives -= 1;
            Main.player.hitted = true;
            Main.player.knockBack = true;
            Main.player.inKnockBack = true;
        }

        if (this.hurt(Main.player, this.getX(), this.getY() + 8)){
            this.isDead = true;
            this.index = 0;
            this.frames = 0;
            this.dead = true;
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
