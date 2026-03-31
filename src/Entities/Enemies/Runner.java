package Entities.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Main;
import World.Camera;
import World.Tile;
import World.World;

public class Runner extends Enemies{

    private BufferedImage[] right, left;

    private double fallSpeed;
    private double gravity = 0.4;
    private double speed = 6;
    private boolean jumped;
    private boolean inGround;


    public Runner(int x, int y, int width, int height){
        super(x, y, width, height);

        this.maxFrames = 5;
        this.maxIndex = 2;

        this.locatePlayer(Main.player);

        right = new BufferedImage[2];
        left = new BufferedImage[2];

        for (int i = 0; i < 2; i++){
            right[i] = Main.enemies.getSprite(64 + 32*i, 32*4, 32, 32);
            left[i] = Main.enemies.getSprite(32*i, 32*4, 32, 32);
        }
    }

    public void render(Graphics g){
        if (!isDead){
            if (this.hori_dir > 0){
                g.drawImage(right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
            
            }else {
                g.drawImage(left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
            }
            
        }
    }

    public void update(){
        if(!isDead){
            this.animFrames();
            this.move();
            if(!this.inScreen()){
                this.isDead = true;
            }
            this.hit();
        
        }else {
            this.respawn();
        }

    }

    public void move(){
        fallSpeed += gravity;
        if (this.jumped && this.inGround) {
            this.fallSpeed = -4;
        }

        for (int i=0; i < speed; i++){

            Tile hit = World.isFree(this.getX() + hori_dir, this.getY(), 20, 32);

            Tile right = World.isFree(this.getX() + this.width + hori_dir, this.getY()+32, 20, 1);
            Tile left = World.isFree(this.getX() - this.width + hori_dir, this.getY()+32, 20, 1);

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
                        jumped = true;;
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

            Tile hit = World.isFree((int) x, (int)(y + moveStep), 20, 32);

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

        if (this.hitPlayer(Main.player, this.getX() + 3, this.getY()) && !Main.player.hitted){
            Main.player.gotHit = true;
            Main.player.lives -= 1;
            Main.player.hitted = true;
            Main.player.knockBack = true;
            Main.player.inKnockBack = true;
        }

        if (this.hurt(Main.player, this.getX() + 3, this.getY())){
            this.isDead = true;
        }
    }
}
