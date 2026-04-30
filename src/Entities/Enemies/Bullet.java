package Entities.Enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Player;
import Main.Main;
import World.Camera;

public class Bullet extends Enemies{

    private BufferedImage[] bullet;
    private double speed = 10;
    private boolean canHit;
    public int deadTimer;

    public Bullet(int x, int y, int width, int height, Soldier soldier){
        super(x, y, width, height);

        this.deadTimer = 0;

        this.dead = true;

        this.maxFrames = 4;
        this.maxIndex = 2;

        this.isDead = false;
        this.canHit = true;

        bullet = new BufferedImage[1];

        bullet[0] = Main.enemies.getSprite(32*8, 32*2, 8, 32);

        this.shoot(soldier);
    }

    public void render (Graphics g){
        if (!this.isDead){
            g.drawImage(bullet[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
        }else if (this.dead){
            g.drawImage(this.explosion[index], this.getX() - Camera.x, this.getY()+5 - Camera.y, 16, 16, null);
            g.drawImage(this.p1[0], (int)this.p1x - Camera.x, (int)this.p1y+5 - Camera.y, 16, 16, null);
            g.drawImage(this.p2[0], (int)this.p2x - Camera.x, (int)this.p2y+5 - Camera.y, 16, 16, null);
            g.drawImage(this.p3[0], (int)this.p3x - Camera.x, (int)this.p3y+5 - Camera.y, 16, 16, null);
            g.drawImage(this.p4[0], (int)this.p4x - Camera.x, (int)this.p4y+5 - Camera.y, 16, 16, null);
        }

    }

    public void update(){
        this.deathAnimation();

        if (!isDead) {

            if (Main.time){
                this.x+=this.speed;
                if (this.hitPlayer(Main.player, this.getX() + 2, this.getY() + 15) && !Main.player.hitted && this.canHit){
                    Main.player.gotHit = true;
                    Player.lives -= 1;
                    Main.player.hitted = true;
                    Main.player.knockBack = true;
                    Main.player.inKnockBack = true;
                    Main.entities.remove(this);
                }
            }
            if (this.hurt(Main.player, this.getX() + 2, this.getY() + 15)){
                this.canHit = false;
                this.index = 0;
                this.frames = 0;
                this.dead = true;
                Player.score += 2;
                this.isDead = true;
            }
            

            if (!this.inScreen()){
                Main.entities.remove(this);
            }

        }else{
            this.animFrames();
            this.deadTimer++;
            if (this.deadTimer >= 10) {
                this.deadTimer = 0;
                Main.entities.remove(this);
            }
        }
    }

    public void shoot(Soldier soldier){
        if (soldier.last_hori_dir > 0){
            this.speed = 4;
        }else {
            this.speed = -4;
        }
    }
}
