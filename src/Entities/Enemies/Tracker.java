package Entities.Enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Player;
import Main.Main;
import World.Camera;
import World.Tile;
import World.World;

public class Tracker extends Enemies{

    private double aceleration = 0.08;
    private double gravity = 0.4;
    private double fallSpeed = 0;
    private double speed = 0;

    private boolean jumped = false;
    private boolean littleJump = false;
    private int littleJumpCD = 0;

    private BufferedImage[] right, left;

    public Tracker (int x, int y, int width, int height){
        super(x, y, width, height);

        this.maxIndex = 2;
        this.maxFrames = 8;

        right = new BufferedImage[2];
        left = new BufferedImage[2];

        for (int i=0; i < 2; i++){
            right[i] = Main.enemies.getSprite(40*i, 176, 40, 24);
            left[i] = Main.enemies.getSprite(80 + (40*i), 176, 40, 24);
        }

    }

    public void render(Graphics g){
        if (!this.isDead) {
            if (this.hori_dir > 0){
                g.drawImage(right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

            }else {
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

        if(!isDead){
            this.maxFrames = 15;
            this.animFrames();
            this.locatePlayer(Main.player);
            this.move();
            this.hit();
            if (this.y > Camera.y+Main.HEIGHT) {
                this.isDead = true;
            }
        
        }else {
            this.animFrames();
            this.deathAnimation();
            this.respawn();
            this.speed = 0;
        }
        
    }

    public void move(){

        if(this.hori_dir > 0){
            this.speed += this.aceleration;
        }else {
            this.speed -= this.aceleration;
        }
        
        this.fallSpeed += this.gravity;

        if (this.fallSpeed >= 7) this.fallSpeed= 6.5;

        if (this.speed >= 4) this.speed = 3;
        if (this.speed <= -4) this.speed = -3;

        if (this.jumped){
            fallSpeed = -5;
        }
        if (this.littleJump){
            fallSpeed = -2;
        }
        this.littleJumpCD ++;
        if (this.littleJumpCD >= 15){
            this.littleJump = true;
            this.littleJumpCD = 0;
        }

        for (int i = 0; i < Math.abs(this.speed); i++){

            double moveStep = Math.signum(this.speed);

            Tile hit = World.isFree(this.getX() + (int)moveStep, this.getY(), this.width, this.height);

            Tile right = World.isFree(this.getX() + this.width + (int)moveStep, this.getY()+40, this.width, 5);
            Tile left = World.isFree(this.getX() - this.width + (int)moveStep, this.getY()+40, this.width, 5);

            if (hit == null){
                this.x += moveStep;
            }

            if (this.hori_dir > 0){
                if (right == null){
                    jumped = true;
                }
            }else {
                if (left == null){
                    jumped = true;
                }
            }
        }

        for (int i = 0; i < Math.abs(this.fallSpeed); i++){

            double moveStep = Math.signum(this.fallSpeed);

            Tile hit = World.isFree(this.getX(), this.getY() + (int)moveStep, this.width, this.height);
            Tile jump = World.isFree(this.getX(), this.getY()+24 + (int)moveStep, this.width, 10);

            if(jump == null){
                this.jumped = false;
            }

            if (hit == null){
                this.y += moveStep;
                this.littleJump = false;

            }else {
                this.fallSpeed = 0;
            }
        }

    }

    public void hit(){

        if (this.hitPlayer(Main.player, this.getX(), this.getY()) && !Main.player.hitted){
            Main.player.gotHit = true;
            Player.lives -= 1;
            Main.player.hitted = true;
            Main.player.knockBack = true;
            Main.player.inKnockBack = true;
        }

        if (this.hurt(Main.player, this.getX() + 3, this.getY())){
            this.isDead = true;
            this.index = 0;
            this.frames = 0;
            this.dead = true;
            Player.score += 5;
        }
    }
}
