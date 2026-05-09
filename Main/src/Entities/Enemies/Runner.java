package Entities.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Player;
import Main.Main;
import World.Camera;
import World.Tile;
import World.World;

public class Runner extends Enemies{

    private BufferedImage[] right, left;
    private BufferedImage  jump_right, jump_left;

    private double fallSpeed;
    private double gravity = 0.4;
    private double speed = 5;
    private boolean jumped;
    private boolean inGround;


    public Runner(int x, int y, int width, int height){
        super(x, y, width, height);

        this.maxFrames = 5;
        this.maxIndex = 2;

        this.locatePlayer(Main.player);

        right = new BufferedImage[2];
        left = new BufferedImage[2];
        jump_right = Main.enemies.getSprite(192, 176, 48, 48);
        jump_left = Main.enemies.getSprite(192, 128, 48, 48);

        for (int i = 0; i < 2; i++){
            right[i] = Main.enemies.getSprite(96 + 48*i, 32*4, 48, 48);
            left[i] = Main.enemies.getSprite(48*i, 32*4, 48, 48);
        }
    }

    public void render(Graphics g){
        if (!isDead){
            if (this.hori_dir > 0){
                if (!inGround){
                    g.drawImage(jump_right, this.getX() - Camera.x, this.getY() - Camera.y, null);
                }else {
                    g.drawImage(right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                } 
            
            }else {
                if (!inGround){
                    g.drawImage(jump_left, this.getX() - Camera.x, this.getY() - Camera.y, null);
                }else {
                    g.drawImage(left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
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
        if(!isDead){
            this.maxFrames = 5;
            this.maxIndex = 2;
            if(Main.time){
                this.animFrames();
                this.move();
                if(!this.inScreen()) this.isDead = true;
                this.hit();
            }
            this.hurt();
        
        }else {
            this.animFrames();
            this.respawn();
            this.deathAnimation();
        }

    }

    public void move(){
        fallSpeed += gravity;
        if (this.jumped && this.inGround) {
            this.fallSpeed = -4;
        }

        for (int i=0; i < speed; i++){

            Tile hit = World.isFree(this.getX() + hori_dir, this.getY(), 48, 48);

            Tile right = World.isFree(this.getX()+ 24 + hori_dir, this.getY()+48, width, 1);
            Tile left = World.isFree(this.getX() - this.width + hori_dir, this.getY()+48, width, 1);

            switch (hit) {
                case null ->{
                    this.x += hori_dir;
                    break;
                }
                default ->{
                    if (inGround) this.hori_dir *= -1;
                    break;
                }

            }

            if (this.inGround){
                if (this.hori_dir < 0){
                    if (left == null){
                        jumped = true;
                    }
            
                }else if (this.hori_dir > 0) {
                    if (right == null){
                        jumped = true;
                    }

                }
            }
            
        }

         for (int i = 0; i < Math.abs(fallSpeed); i++){
            double moveStep = Math.signum(fallSpeed);

            Tile hit = World.isFree((int) x, (int)(y + moveStep), 48, 48);

            switch (hit){
                case null -> {
                    this.y += moveStep;
                    this.inGround = false;
                    this.jumped = false;
                    break;
                }
                default -> {
                    this.inGround = true;
                    this.fallSpeed = 0;
                    break;
                }
            }
        }

    }

    public void hit(){

        if (this.hitPlayer(Main.player, this.getX(), this.getY() + 8) && !Main.player.hitted){
            Main.player.gotHit = true;
            Player.lives -= 1;
            Main.player.hitted = true;
            Main.player.knockBack = true;
            Main.player.inKnockBack = true;
        }
    }

    public void hurt(){
        if (this.hurt(Main.player, this.getX(), this.getY() + 8)){
            this.isDead = true;
            this.index = 0;
            this.frames = 0;
            this.dead = true;
            Player.score += 5;
        }
    }
}
