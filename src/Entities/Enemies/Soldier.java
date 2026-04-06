package Entities.Enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Player;
import Main.Main;
import World.Camera;
import World.Tile;
import World.World;

public class Soldier extends Enemies{

    public int last_hori_dir = 1;
    private double speed = 0.6;
    private double vSpeed;

    private int attackCd = 0;
    private int attackingTime = 0;
    private boolean attacking;

    private BufferedImage[] right, left, attack_right, attack_left;

    public Soldier(int x, int y, int width, int height){
        super(x, y, width, height);

        this.maxFrames = 20;
        this.maxIndex = 2;

        this.locatePlayer(Main.player);
        this.locatePlayer2(Main.player);

        right = new BufferedImage[2];
        left = new BufferedImage[2];
        attack_right = new BufferedImage[2];
        attack_left = new BufferedImage[2];

        for (int i = 0; i < 2; i++){
            right[i] = Main.enemies.getSprite(32*i, 64, 32, 32);
            left[i] = Main.enemies.getSprite(64 + (32*i), 64, 32, 32);
            attack_right[i] = Main.enemies.getSprite((32*4) + (32*i), 64, 32, 32);
            attack_left[i] = Main.enemies.getSprite((32*6) + (32*i), 64, 32, 32);
        }
    }

    public void render (Graphics g){

        if (!isDead){
            if (this.last_hori_dir > 0){
                if(!this.attacking){
                    g.drawImage(right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                
                }else {
                    g.drawImage(attack_right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
                
        
            }else if (this.last_hori_dir < 0){
                if(!this.attacking){
                    g.drawImage(left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                
                }else {
                    g.drawImage(attack_left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
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

        if (!isDead){
            vSpeed = speed * hori_dir;
            this.animFrames();
            this.locatePlayer2(Main.player);
            this.hit();
            this.move();
            this.attack();
        
        }else {
            this.deathAnimation();
            this.animFrames();
            this.respawn();
        }

    }

    public void attack(){
        attackCd++;
        if (attackCd >= 145){
            attacking = true;
            attackingTime++;
            switch (attackingTime) {
                case 10:
                    Bullet bullet = new Bullet(this.getX()+16, this.getY()-2, 5, 3, this);
                    Main.entities.add(bullet);
                    break;
                case 20:
                    Bullet bullet1 = new Bullet(this.getX()+16, this.getY()-2, 5, 3, this);
                    Main.entities.add(bullet1);
                    break;
                case 30:
                    Bullet bullet2 = new Bullet(this.getX()+16, this.getY()-2, 5, 3, this);
                    Main.entities.add(bullet2);
                    break;
            }
            if (attackingTime >= 30){
                attacking = false;
                attackingTime = 0;
                attackCd = 0;
            }
        }

        this.maxFrames = 20;
        if (attacking) this.maxFrames = 4;

    }

    public void hit(){

        if (this.hitPlayer(Main.player, this.getX() + 3, this.getY()) && !Main.player.hitted){
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

    public void move(){
        if(!attacking){
            for (int i=0; i < this.speed; i++){

                Tile hit = World.isFree(this.getX() + hori_dir, this.getY(), 20, 32);

                Tile right = World.isFree(this.getX() + this.width + hori_dir, this.getY()+32, 20, 1);
                Tile left = World.isFree(this.getX() - this.width + hori_dir, this.getY()+32, 20, 1);

                switch (hit) {
                    case null ->{
                        this.x += vSpeed;
                        break;
                    }
                    default ->{
                        this.hori_dir *= -1;
                        break;
                    }

                }
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
    }

    public void locatePlayer2(Player player){
        if (this.x > player.getX()){
            this.last_hori_dir = -1;
        }else if (this.x < player.getX()){
            this.last_hori_dir = 1;
        }
    }

}
