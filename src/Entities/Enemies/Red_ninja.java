package Entities.Enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Main;
import World.Camera;
import World.Tile;
import World.World;

public class Red_ninja extends Enemies{

    private BufferedImage[] right, left;
    private BufferedImage[] attack_right, attack_left;

    private double speed = 1;
    private boolean attacking = false;
    private boolean jumped = false;
    private boolean inGround = true;
    private double fallSpeed;
    private double aceleration = 0.3;

    public Red_ninja(int x, int y, int width, int height){
        super(x, y, width, height);
        this.locatePlayer(Main.player);

        this.maxFrames = 20;
        this.maxIndex = 2;

        this.width = 20;
        this.height = 32;

        right = new BufferedImage[2];
        left = new BufferedImage[2];
        attack_right = new BufferedImage[1];
        attack_left = new BufferedImage[1];

        attack_right[0] = Main.enemies.getSprite(32*4, 0, 32, 32);
        attack_left[0] = Main.enemies.getSprite(32*5, 0, 32, 32);

        for (int i = 0; i < 2; i++){
            right[i] = Main.enemies.getSprite(32*i, 0, 32, 32);
            left[i] = Main.enemies.getSprite((32*2) +(32*i), 0, 32, 32);
        }
    }

    public void render(Graphics g){

        if(!this.isDead){
            if (this.hori_dir > 0){
                if(attacking){
                    g.drawImage(attack_right[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }else {
                    g.drawImage(right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
            }else if (this.hori_dir < 0){
                if(attacking){
                    g.drawImage(attack_left[0], this.getX() - Camera.x -10, this.getY() - Camera.y, null);
                }else {
                    g.drawImage(left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
            }
        }
        

    }

    public void update(){

        if(!this.isDead && this.inScreen()){
            this.animFrames();
            this.move();
            this.hit();
            this.fallSpeed += this.aceleration;
        
        }else {
            this.attacking = false;
            this.jumped = false;
            this.respawn();
        }

    }

    public void hit(){
        if (this.hitPlayer(Main.player, this.getX(), this.getY()) && !Main.player.hitted){
            this.attacking = true;
            this.jumped = true;
            Main.player.gotHit = true;
            Main.player.lives -= 1;
            Main.player.hitted = true;
            Main.player.knockBack = true;
            Main.player.inKnockBack = true;
        }

        if (this.hurt(Main.player, this.getX(), this.getY())){
            this.isDead = true;
        }

    }

    public void move(){
        if (this.jumped && this.inGround) {
            this.fallSpeed = -3;
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
                        this.hori_dir *= -1;
                    }
            
                }else if (this.hori_dir > 0) {
                    if (right == null){
                        this.hori_dir *= -1;
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
                    this.speed = 2.5;
                    break;
                }
                default -> {
                    this.inGround = true;
                    this.fallSpeed = 0;
                    this.attacking = false;
                    this.speed = 1;
                    break;
                }
            }
        }

    }
}
