package Entities.Enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Player;
import Main.Main;
import World.Camera;
import World.Tile;
import World.World;

public class Launcher extends Enemies{

    public double vy;
    public double vx;

    private double speed = 0.5;
    private double vSpeed;
    private int dirTimer = 0;

    private boolean isAttacking = false;
    private boolean attacking = false;
    private int attackingTime = 0;
    private int attackCD = 0;
    private int last_hori_dir = 1;

    private BufferedImage[] right, left, attack_right, attack_left;

    public Launcher(int x , int y, int width, int height){
        super(x, y, width, height);

        this.maxFrames = 10;
        this.maxIndex = 2;

        right = new BufferedImage[2];
        left = new BufferedImage[2];
        attack_right = new BufferedImage[1];
        attack_left = new BufferedImage[1];

        attack_right[0] = Main.enemies.getSprite((32*7), 32*3, 32, 32);
        attack_left[0] = Main.enemies.getSprite((32*5), 32*3, 32, 32);

        for (int i = 0; i < 2; i++){
            right[i] = Main.enemies.getSprite((32*2) + 32*i, 32*3, 32, 32);
            left[i] = Main.enemies.getSprite(32*i, 32*3, 32, 32);

        }

        this.locatePlayer(Main.player);
        
    }

    public void render(Graphics g){

         if (!isDead){
            if (this.last_hori_dir > 0){
                if(!this.attacking){
                    g.drawImage(right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                
                }else {
                    g.drawImage(attack_right[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
                
        
            }else if (this.last_hori_dir < 0){
                if(!this.attacking){
                    g.drawImage(left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                
                }else {
                    g.drawImage(attack_left[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
                
            }
        }
    }

    public void update(){
        if (!isDead && inScreen()){
            vSpeed = speed * hori_dir;
            this.animFrames();
            this.move();
            attack(Main.player);
            this.locatePlayer2(Main.player);
            this.hit();
        
        }else {
            this.respawn();
        }
        
    }

    public void attack(Player player){
        
        double dx = player.getX() - this.getX();
        double dy = player.getY() - this.getY();
            
        int maxHeight = 80;
        int maxD = 180;
        double g = 0.4;

        if(dx > -maxD && dx < maxD){
            attackCD++;
            if(attackCD >= 90){
                attacking = true;
                isAttacking = true;
                attackCD = 0;
            }

            if (attacking){
                attackingTime++;
                if(attackingTime >= 20){
                    attacking = false;
                    attackingTime = 0;
                }
            }

            if(isAttacking){

                this.vy = -(Math.sqrt(2*g*maxHeight));

                double t = (Math.abs(this.vy) + Math.sqrt((this.vy*this.vy) + 2 * g * dy))/g;

                this.vx = dx/t;

                Projectile projectile = new Projectile(this.getX() + (this.width/2), this.getY() + (this.height/2), 8, 8);
                projectile.vx = this.vx;
                projectile.vy = this.vy;
                Main.entities.add(projectile);

                this.isAttacking = false;
            }
        }
        
    }

    public void move(){
        dirTimer++;
        for (int i=0; i < this.speed; i++){

            Tile hit = World.isFree(this.getX() + hori_dir, this.getY(), 20, 32);

            Tile right = World.isFree(this.getX()+ 9 + this.width + hori_dir, this.getY()+32, 20, 1);
            Tile left = World.isFree(this.getX()+ 9 - this.width + hori_dir, this.getY()+32, 20, 1);

            if (dirTimer >= 120){
                this.locatePlayer(Main.player);
                dirTimer = 0;
            }

            switch (hit) {
                case null ->{
                    if (!attacking) this.x += vSpeed;
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

    public void hit(){

        if (this.hitPlayer(Main.player, this.getX(), this.getY()) && !Main.player.hitted){
            Main.player.gotHit = true;
            Main.player.lives -= 1;
            Main.player.hitted = true;
            Main.player.knockBack = true;
            Main.player.inKnockBack = true;
        }

        if (this.hurt(Main.player, this.getX() , this.getY())){
            this.isDead = true;
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
